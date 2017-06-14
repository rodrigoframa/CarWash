package uemapdm.com.carwasher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import uemapdm.com.carwasher.R;

public class ResgateSenhaActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnResetSenha;
    EditText edtxtEmailReset;
    TextView txtvCadastro;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgate_senha);

        edtxtEmailReset = (EditText) findViewById(R.id.edtxtEmailResetId);
        txtvCadastro = (TextView) findViewById(R.id.txtvCadastroId);
        btnResetSenha = (Button) findViewById(R.id.btnResetSenhaId);
        btnResetSenha.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        String email = edtxtEmailReset.getText().toString();

        if(email == null || email.trim().equals("")){
            Toast.makeText(this, "Informe o email",Toast.LENGTH_SHORT).show();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(ResgateSenhaActivity.this, "As instruções para alteração de senha foram enviadas ao seu email",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ResgateSenhaActivity.this, "Erro ao enviar e-mail" + task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void abrirCadastroUsuario(View view){

        startActivity(new Intent(ResgateSenhaActivity.this , CadastroActivity.class));
        finish();

    }



}
