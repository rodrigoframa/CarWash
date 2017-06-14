package uemapdm.com.carwasher.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import uemapdm.com.carwasher.R;
import uemapdm.com.carwasher.entidades.Usuario;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    Button btnRegistro;
    EditText edtxtEmail;
    EditText edtxtSenha;
    EditText edtxtRepSenha;
    EditText edtxtNome;
    EditText edtxtCpf;
    EditText edtxtTelefone;
    EditText edtxtEndereco;
    EditText edtxtCep;
    EditText edtxtBairro;
    TextView txtvLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        auth = FirebaseAuth.getInstance();

        btnRegistro = (Button) findViewById(R.id.btnRegistroId);
        btnRegistro.setOnClickListener(this);

        edtxtEmail = (EditText) findViewById(R.id.edtxtEmailId);
        edtxtNome = (EditText) findViewById(R.id.edtxtNomeId);
        edtxtSenha = (EditText) findViewById(R.id.edtxtSenhaId);
        edtxtRepSenha = (EditText) findViewById(R.id.edtxtRepSenhaId);
        edtxtCpf = (EditText) findViewById(R.id.edtxtCpfId);
        edtxtTelefone = (EditText) findViewById(R.id.edtxtTelefoneId);
        edtxtEndereco = (EditText) findViewById(R.id.edtxtEnderecoId);
        edtxtCep = (EditText) findViewById(R.id.edtxtCepId);
        edtxtBairro = (EditText) findViewById(R.id.edtxtBairroId);
        txtvLogin = (TextView) findViewById(R.id.txtvLoginId);
        txtvLogin.setOnClickListener(this);

        //Definir Mascaras
        SimpleMaskFormatter sMaskCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        SimpleMaskFormatter sMaskTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        SimpleMaskFormatter sMaskCep = new SimpleMaskFormatter("NNNNN-NNN");


        MaskTextWatcher maskCpf = new MaskTextWatcher(edtxtCpf, sMaskCpf);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(edtxtTelefone, sMaskTelefone);
        MaskTextWatcher maskCep = new MaskTextWatcher(edtxtCep, sMaskCep);

        edtxtCpf.addTextChangedListener( maskCpf );
        edtxtTelefone.addTextChangedListener( maskTelefone );
        edtxtCep.addTextChangedListener( maskCep );



    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegistroId) {
            registrar();
        }

        //Está criando uma conta
        if (view.getId() == R.id.txtvLoginId) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
            return;
        }
    }

        private void registrar() {

        final String nome = edtxtNome.getText().toString();
        final String email = edtxtEmail.getText().toString();
        final String senha = edtxtSenha.getText().toString();
        String confirmacao = edtxtRepSenha.getText().toString();

        if (nome == null || nome.trim().equals("") || email == null || email.trim().equals("") || senha == null || senha.trim().equals("")) {
            Toast.makeText(this, "Informe nome, email e senha", Toast.LENGTH_SHORT).show();
            edtxtNome.setError("");
            edtxtEmail.setError("");
            edtxtSenha.setError("");
            edtxtRepSenha.setError("");
            return;
        }

        if (!senha.equals(confirmacao)) {
            Toast.makeText(this, "Senha e confirmação diferentes", Toast.LENGTH_SHORT).show();
            edtxtSenha.setError("");
            edtxtRepSenha.setError("");
            return;

        }

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Carregando , aguarde por favor!");
            progressDialog.setCancelable(false);
            progressDialog.show();

        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (!task.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Erro ao criar usuário " + task.getException(), Toast.LENGTH_LONG).show();

                    progressDialog.dismiss();
                } else {

                    FirebaseUser u = auth.getCurrentUser();

                    UserProfileChangeRequest.Builder profileBuilder =
                            new UserProfileChangeRequest.Builder();
                    profileBuilder.setDisplayName(nome);

                    u.updateProfile(profileBuilder.build());

                    Toast.makeText(CadastroActivity.this, "Usuário criado com sucesso. Verifique seu email pelo link de validação",
                            Toast.LENGTH_LONG).show();
                    DatabaseReference refUsuario = FirebaseDatabase.getInstance().getReference("usuarios");

                    Usuario usuario = new Usuario(nome, email, senha);
                    refUsuario.child(task.getResult().getUser().getUid()).setValue(usuario);

                    startActivity(new Intent(CadastroActivity.this, VeiculosActivity.class));
                    finish();
                }
            }

        });
        }
}

