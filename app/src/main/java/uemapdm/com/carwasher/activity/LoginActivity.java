package uemapdm.com.carwasher.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import uemapdm.com.carwasher.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    Button btnLogar;
    TextView txtvRecuperar;
    Button btnCadastro;
    EditText edtxtLoginUsuario;
    EditText edtxtLoginSenha;
    TextInputLayout txtinpLoginSenha;
    TextInputLayout txtinpLoginUsuario;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        txtinpLoginUsuario = (TextInputLayout) findViewById(R.id.txtinpLoginUsuarioId);
        txtinpLoginSenha = (TextInputLayout) findViewById(R.id.txtinpLoginSenhaId);

        edtxtLoginUsuario = (EditText) findViewById(R.id.edtxtLoginUsuarioId);
        edtxtLoginSenha = (EditText) findViewById(R.id.edtxtLoginSenhaId);

        btnCadastro = (Button) findViewById(R.id.btnCadastroId);
        btnCadastro.setOnClickListener(this);
        txtvRecuperar = (TextView) findViewById(R.id.txtvRecuperarId);
        txtvRecuperar.setOnClickListener(this);
        btnLogar = (Button) findViewById(R.id.btnLogarId);
        btnLogar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {





        if(view.getId() == R.id.btnLogarId){
            login();
        }

        //Está criando uma conta
        if(view.getId() == R.id.btnCadastroId) {
            Intent i = new Intent(this, CadastroActivity.class);
            startActivity(i);
            return;
        }

        //Recuperar Senha
        if(view.getId() == R.id.txtvRecuperarId) {
            Intent i = new Intent(this, ResgateSenhaActivity.class);
            startActivity(i);
            return;
        }


    }

    private void login() {

        String email = edtxtLoginUsuario.getText().toString();
        String senha = edtxtLoginSenha.getText().toString();

        if(email == null || email.trim().equals("") || senha == null || senha.trim().equals("")){
            Toast.makeText(this, "Informe email e senha",Toast.LENGTH_SHORT).show();
            edtxtLoginUsuario.setError("");
            edtxtLoginSenha.setError("");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando , aguarde por favor!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this, VeiculosActivity.class);
                    startActivity(i);
                    finish();
                    return;
                } else {
                    Toast.makeText(LoginActivity.this, "Falha ao realizar o login " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

}
