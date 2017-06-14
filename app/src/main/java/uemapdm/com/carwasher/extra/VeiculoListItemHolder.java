package uemapdm.com.carwasher.extra;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import uemapdm.com.carwasher.R;

/**
 * Created by rodri on 06/06/2017.
 */

public class VeiculoListItemHolder extends RecyclerView.ViewHolder {

    private ImageButton btnExcluirVeic, btnEditarVeic;
    private TextView txtvPlacaVeic, txtvCorVeic, txtvMarcaVeic, txtvModeloVeic;

    public VeiculoListItemHolder(final View itemView) {
        super(itemView);
        txtvPlacaVeic = (TextView) itemView.findViewById(R.id.txtvPlacaVeicId);
        txtvModeloVeic = (TextView) itemView.findViewById(R.id.txtvModeloVeicId);
        txtvCorVeic = (TextView) itemView.findViewById(R.id.txtvCorVeicId);
        txtvMarcaVeic = (TextView) itemView.findViewById(R.id.txtvMarcaVeicId);
        btnEditarVeic = (ImageButton) itemView.findViewById(R.id.btnEditarVeicId);
        btnExcluirVeic = (ImageButton) itemView.findViewById(R.id.btnExcluirVeicId);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(itemView.getContext(), "Clicado", Toast.LENGTH_SHORT).show();
            }
        });

    }




    public ImageButton getBtnExcluirVeic () { return btnExcluirVeic; }
    public ImageButton getBtnEditarVeic () { return  btnEditarVeic; }
    public TextView getTxtvPlacaVeic () { return  txtvPlacaVeic; }
    public TextView getTxtvCorVeic() { return txtvCorVeic; }
    public TextView getTxtvMarcaVeic() { return  txtvMarcaVeic; }
    public TextView getTxtvModeloVeic() { return  txtvModeloVeic; }
}


