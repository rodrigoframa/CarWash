package uemapdm.com.carwasher.extra;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uemapdm.com.carwasher.R;
import uemapdm.com.carwasher.activity.CadastroVeiculoActivity;
import uemapdm.com.carwasher.activity.VeiculosActivity;
import uemapdm.com.carwasher.entidades.Veiculo;

public class VeiculoListItemAdapter extends RecyclerView.Adapter<VeiculoListItemHolder> {

    VeiculosActivity activity;
    ArrayList<Veiculo> itens;
    FirebaseAuth auth;

    public VeiculoListItemAdapter( ArrayList<Veiculo> lista, VeiculosActivity activity) {
        this.itens = lista;
        this.activity = activity;
    }

    @Override
    public VeiculoListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.veiculos_list_item, //resource
                parent, ///viewgroup
                false //atach to root
        );

        return  new VeiculoListItemHolder(v);
    }

    @Override
    public void onBindViewHolder(VeiculoListItemHolder holder, final int position) {

        final Veiculo item = itens.get(position);

        holder.getTxtvPlacaVeic().setText(item.getPlaca());
        holder.getTxtvCorVeic().setText(item.getCor());
        holder.getTxtvMarcaVeic().setText(item.getMarca());
        holder.getTxtvModeloVeic().setText(item.getModelo());

        holder.getBtnExcluirVeic().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth = FirebaseAuth.getInstance();
                DatabaseReference ref = FirebaseDatabase.getInstance().
                        getReference("usuarios").child(auth.getCurrentUser().getUid()).child("veiculo").child(item.getPlaca());
                //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("veiculo").child(item.getPlaca());

                ref.setValue(null);

                itens.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itens.size());
            }
        });

        holder.getBtnEditarVeic().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(activity, CadastroVeiculoActivity.class);
                i.putExtra("veiculo", item);

                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(itens == null){
            return 0;
        }
        return itens.size();
    }

}
