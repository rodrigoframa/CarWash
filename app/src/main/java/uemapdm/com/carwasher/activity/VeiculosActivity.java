package uemapdm.com.carwasher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uemapdm.com.carwasher.R;
import uemapdm.com.carwasher.entidades.Veiculo;
import uemapdm.com.carwasher.extra.VeiculoListItemAdapter;

public class VeiculosActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseAuth.AuthStateListener listener;

    RecyclerView meuRecyclerView;
    TextView txtvEmail;
    Button btnLogout;
    FloatingActionButton btnAddVeiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiculos);
        setTitle("Meus Veículos");

        auth = FirebaseAuth.getInstance();
        txtvEmail = (TextView) findViewById(R.id.txtvEmailId);
        btnLogout = (Button) findViewById(R.id.btnLogoutId);
        btnLogout.setOnClickListener(this);
        btnAddVeiculo = (FloatingActionButton) findViewById(R.id.btnAddVeiculoId);
        btnAddVeiculo.setOnClickListener(this);

        meuRecyclerView = (RecyclerView) findViewById(R.id.listVeiculoId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        meuRecyclerView.setLayoutManager(linearLayoutManager);
        meuRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser u = firebaseAuth.getCurrentUser();

                if(u == null){
                    Intent i = new Intent(VeiculosActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
        user = auth.getCurrentUser();

        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("veiculo");

        if(user != null){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid()).child("veiculo");
        ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //txtvEmail.setText( dataSnapshot.getValue().toString() + " " + user.getEmail() + " " + user.getDisplayName());
                    ArrayList<Veiculo> lista = new ArrayList<Veiculo>();

                    for(DataSnapshot filho : dataSnapshot.getChildren()){
                        Veiculo v = filho.getValue(Veiculo.class);
                        lista.add(v);
                    }

                    VeiculoListItemAdapter adapter = new VeiculoListItemAdapter(lista, VeiculosActivity.this);
                    meuRecyclerView.setAdapter(adapter);



                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            //txtvEmail.setText(user.getEmail() + " " + user.getDisplayName());

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btnLogoutId){
            auth.signOut();
        }

        if(view.getId() == R.id.btnAddVeiculoId){
            Intent i = new Intent(this, CadastroVeiculoActivity.class);
            startActivity(i);
        }

    }
}
