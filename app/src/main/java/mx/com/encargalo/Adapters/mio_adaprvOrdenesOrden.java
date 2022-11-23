package mx.com.encargalo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import mx.com.encargalo.Model.mio_mdlOrdenesOrden;
import mx.com.encargalo.R;
import mx.com.encargalo.Utils.mio_cls_Consulta_detalle_pedido;

public class mio_adaprvOrdenesOrden extends RecyclerView.Adapter<mio_adaprvOrdenesOrden.ViewHolder> {

    LayoutInflater inflater;
    ArrayList<mio_mdlOrdenesOrden> listaOrdenes;
    Context context;



    public mio_adaprvOrdenesOrden(Context context, ArrayList<mio_mdlOrdenesOrden> listaOrdenes){

        this.inflater = LayoutInflater.from(context);
        this.listaOrdenes = listaOrdenes;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View vista = inflater.inflate(R.layout.mio_menuitemlistaordenes, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ViewHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull mio_adaprvOrdenesOrden.ViewHolder holder, int position) {


        int idOrden = listaOrdenes.get(position).getIdOrden();
        String odFechaPedido = listaOrdenes.get(position).getOdFechaPedido();
        String odHoraPedido = listaOrdenes.get(position).getOdHoraPedido();
        String perNombreCompleto = listaOrdenes.get(position).getPerNombreCompleto();
        String odEstado = listaOrdenes.get(position).getOdEstado();
        int idRepartidor = listaOrdenes.get(position).getIdRepartidor();
        String repNombres = listaOrdenes.get(position).getRepNombres();
        holder.idOrden.setText(String.valueOf(idOrden));
        holder.odFechaPedido.setText(odFechaPedido);
        holder.perNombreCompleto.setText(perNombreCompleto);
        holder.odEstado.setText(odEstado);
        holder.txt_Detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mio_cls_Consulta_detalle_pedido.perNombreCompleto = perNombreCompleto;
                mio_cls_Consulta_detalle_pedido.idOrden = idOrden;
                mio_cls_Consulta_detalle_pedido.odFechaSolicitado = odFechaPedido;
                mio_cls_Consulta_detalle_pedido.odHoraSolicitado = odHoraPedido;
                mio_cls_Consulta_detalle_pedido.idRepartidor = idRepartidor;
                mio_cls_Consulta_detalle_pedido.repNombres = repNombres;
                mio_cls_Consulta_detalle_pedido.odEstadoPedido = odEstado;
                Navigation.findNavController(view).navigate(R.id.nav_misordenesdetallepedido);


            }
        });

        holder.txt_Detalle_Chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idRepartidor == 0){
                    Toast.makeText(view.getContext(), "Asigne un repartidor en detalle", Toast.LENGTH_LONG).show();
                }
                else{
                    Navigation.findNavController(view).navigate(R.id.nav_misordenesconversacion);

                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return listaOrdenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idOrden, odFechaPedido, perNombreCompleto, odEstado, txt_Detalle, txt_Detalle_Chat;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            idOrden = itemView.findViewById(R.id.mio_mop_txtIdOrden);
            odFechaPedido = itemView.findViewById(R.id.mio_mop_txtOrdCreada);
            perNombreCompleto = itemView.findViewById(R.id.mio_mop_txtNombres);
            odEstado = itemView.findViewById(R.id.mio_mop_txtOrdEstado);
            txt_Detalle = itemView.findViewById(R.id.mio_moptxtdetalle);
            txt_Detalle_Chat = itemView.findViewById(R.id.mio_moptxtdetallechat);

        }
    }







}
