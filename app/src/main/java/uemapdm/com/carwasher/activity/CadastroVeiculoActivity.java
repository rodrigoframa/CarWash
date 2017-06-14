package uemapdm.com.carwasher.activity;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uemapdm.com.carwasher.R;
import uemapdm.com.carwasher.entidades.Veiculo;

public class CadastroVeiculoActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSalvarVeiculo;
    EditText edtxtPlaca;
    EditText edtxtModelo;
    EditText edtxtMarca;
    EditText edtxtCor;

    FirebaseAuth auth;

    Veiculo veiculo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);
        setTitle("Veículo");

        auth = FirebaseAuth.getInstance();

        //if (auth.getCurrentUser() != null) { }

        btnSalvarVeiculo = (Button) findViewById(R.id.btnSalvarVeiculoId);
        btnSalvarVeiculo.setOnClickListener(this);

        edtxtPlaca = (EditText) findViewById(R.id.edtxtPlacaId);
        edtxtModelo = (EditText) findViewById(R.id.edtxtModeloId);
        edtxtMarca = (EditText) findViewById(R.id.edtxtMarcaId);
        edtxtCor = (EditText) findViewById(R.id.edtxtCorId);

        SimpleMaskFormatter sMaskPlaca = new SimpleMaskFormatter("LLL-NNNN");

        MaskTextWatcher maskPlaca = new MaskTextWatcher(edtxtPlaca, sMaskPlaca);

        edtxtPlaca.addTextChangedListener( maskPlaca );
        limparCampos();
    }

    @Override
    protected void onStart() {
        super.onStart();

        veiculo = (Veiculo) getIntent().getSerializableExtra("veiculo");

        if(veiculo != null){
            edtxtPlaca.setText(veiculo.getPlaca());
            edtxtModelo.setText(veiculo.getModelo());
            edtxtMarca.setText(veiculo.getMarca());
            edtxtCor.setText(veiculo.getCor());
        }
    }

    @Override
    public void onClick(View view) {

        String placa = edtxtPlaca.getText().toString();
        String marca = edtxtMarca.getText().toString();
        String modelo = edtxtModelo.getText().toString();
        String cor = edtxtCor.getText().toString();
        String placaSemFormatacao = placa.replace("-","").toUpperCase();

        DatabaseReference refVeiculo = FirebaseDatabase.getInstance().getReference("usuarios").child(auth.getCurrentUser().getUid()).child("veiculo");


        if(placa == null || placa.trim().equals("") || marca == null || marca.trim().equals("") || modelo == null || modelo.trim().equals("") || cor == null || cor.trim().equals("")){
            Toast.makeText(this, "Informe os campos vazios",Toast.LENGTH_SHORT).show();
            return;
        }

        if(veiculo == null){

            veiculo = new Veiculo(placaSemFormatacao, marca, modelo, cor);

            //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("veiculo").child(placa);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios").child(auth.getCurrentUser().getUid()).child("veiculo").child(placaSemFormatacao);

            ref.setValue(veiculo, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if(databaseError != null){
                        //deu certo
                    } else {
                        //deu erro
                    }

                }
            });

            finish();

        } else {
            veiculo.setMarca(marca);
            veiculo.setModelo(modelo);
            veiculo.setCor(cor);

           DatabaseReference ref = FirebaseDatabase.getInstance().
                    getReference("usuarios").child(auth.getCurrentUser().getUid()).child("veiculo").child(placaSemFormatacao);

            ref.setValue(veiculo);

            finish();


        }

    }

    private void limparCampos(){
        edtxtPlaca.setText("");
        edtxtModelo.setText("");
        edtxtMarca.setText("");
        edtxtCor.setText("");
    }
}
