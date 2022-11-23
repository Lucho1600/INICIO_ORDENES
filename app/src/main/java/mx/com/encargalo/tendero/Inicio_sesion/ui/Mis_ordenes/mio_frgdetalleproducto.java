package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_ordenes;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.encargalo.Adapters.mio_adaplvProductosOrdenAdapter;
import mx.com.encargalo.Model.mio_mdlProductosOrden;
import mx.com.encargalo.R;
import mx.com.encargalo.Utils.Util;
import mx.com.encargalo.Utils.mio_cls_Consulta_detalle_pedido;

public class mio_frgdetalleproducto extends Fragment {
    Button mio_dpbtncancelarorden,mio_dpbtnenviarorden;
    Dialog dialog;
    View confirmacion, cancelacion;
    ListView mio_lstvwProductos;
    TextView tv_cantProductos, mio_txtmostrartotal;
    CheckBox ch_selec_all;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;
    ArrayList<mio_mdlProductosOrden> mio_mdlProductosOrdenList;
    mio_adaplvProductosOrdenAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mio_frgdetalleproducto, container, false);

        //Enlace de elementos del layout por id
        mio_dpbtncancelarorden=vista.findViewById(R.id.mio_dpbtncancelarorden);
        mio_lstvwProductos = vista.findViewById(R.id.mio_lstvProductos);
        mio_dpbtnenviarorden=vista.findViewById(R.id.mio_dpbtnenviarorden);
        tv_cantProductos = vista.findViewById(R.id.mio_dptxtcontadorproductos);
        ch_selec_all = vista.findViewById(R.id.chxbTodosProductos);
        mio_txtmostrartotal = vista.findViewById(R.id.mio_txtmostrartotal);

        requestQueue = Volley.newRequestQueue(getContext());

        if(mio_cls_Consulta_detalle_pedido.odEstadoPedido != "SOLICITADO"){
            mio_dpbtncancelarorden.setVisibility(vista.GONE);
            mio_dpbtnenviarorden.setVisibility(vista.GONE);
        }

        // Inicializamos el array de lista productos
        mio_mdlProductosOrdenList = new ArrayList<>();
        mio_dpbtncancelarorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelacion=v;
                cancelarOrden();
            }
        });
        mio_dpbtnenviarorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mio_cls_Consulta_detalle_pedido.idRepartidor == 0){
                    Toast.makeText(getContext(), "Debe seleccionar un repartidor disponible", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (ch_selec_all.isChecked()){
                        confirmacion=v;
                        enviarorden();
                    }else {
                        Toast.makeText(getContext(), "Debe seleccionar todos los items", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });




        mio_lstvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });

        //Procedimiento para cargar la lista de productos al momento de crear el fragment
        cargarWebService();

        ch_selec_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ch_selec_all.isChecked()){
                    for (int i = 0; i < mio_mdlProductosOrdenList.size(); i++)
                        adapter.checkCheckBox(i, true);
                }else {
                    adapter.removeSelection();
                }

            }
        });



        return vista;
    }

    //Prodecimiento para obtener la lista de productos segun el id de la orden
    private void cargarWebService() {

        //Asignación de url de API
        String url = Util.RUTA+"c_detalle_orden_x_id_mis_ordenes.php?id_orden="+ mio_cls_Consulta_detalle_pedido.idOrden;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            //Procedimiento que se ejecuta si hay respuesta en lista de productos
            public void onResponse(JSONObject response) {
                mio_mdlProductosOrden productos = null;
                JSONArray json = response.optJSONArray("consulta");
                Double total = 0.0;

                try {


                    for (int i = 0; i < json.length(); i++) {
                        productos = new mio_mdlProductosOrden();
                        JSONObject jsonObject = null;
                        jsonObject = json.getJSONObject(i);
                        productos.setMio_locidProducto(jsonObject.optInt("idListadoProductoTienda"));
                        productos.setMio_locdescProducto(jsonObject.optString("proDescripcion"));
                        productos.setMio_locprecioProducto(jsonObject.optDouble("doPrecioVenta"));
                        productos.setMio_locimagenProducto(jsonObject.optString("lptImagen1"));
                        productos.setMio_locunidadMedidaProducto(jsonObject.optString("proUnidadMedida"));
                        productos.setMio_loccantProducto(jsonObject.optInt("doCantidad"));
                        mio_mdlProductosOrdenList.add(productos);

                        int cantidad = jsonObject.optInt("doCantidad");
                        Double precioventa = jsonObject.optDouble("doPrecioVenta");
                        total = total + (cantidad * precioventa);

                    }
                    adapter = new mio_adaplvProductosOrdenAdapter(getContext(), mio_mdlProductosOrdenList, new mio_adaplvProductosOrdenAdapter.onClick() {
                        @Override
                        public void onClick(int id) {
                            adapter.checkCheckBox(id, !adapter.getBooleanSelectedItem(id));
                            if (adapter.getCountItems() == mio_mdlProductosOrdenList.size()) {
                                ch_selec_all.setChecked(true);
                            } else {
                                ch_selec_all.setChecked(false);
                            }
                        }
                    });

                    mio_txtmostrartotal.setText("S/ " +String.valueOf(total));
                    mio_lstvwProductos.setAdapter(adapter);
                    mio_lstvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
                        }
                    });
                    tv_cantProductos.setText("(" + mio_mdlProductosOrdenList.size() + " )");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "No hay conexion" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            //Procedimiento que se ejecuta si hay error en respuesta en lista de productos
            public void onErrorResponse(VolleyError error) {
                //Enviamos un mensaje con el error de respuesta
                Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void enviarorden() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.mio_lytenviarorden);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button ecancelar = dialog.findViewById(R.id.mio_eobtncancelar);
        ecancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button eenviar = dialog.findViewById(R.id.mio_eobtnaceptar);
        eenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebServiceEnviarOrden();

                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void cargarWebServiceEnviarOrden()
    {
        String url = Util.RUTA + "m_orden_estado_mis_ordenes.php?sp_idOrden="+mio_cls_Consulta_detalle_pedido.idOrden+"&sp_odEstado=ACEPTADO";
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json = null;
                json = response.optJSONArray("sp_m_repartidor_por_orden_mis_ordenes");
                JSONObject jsonObject = null;
                Navigation.findNavController(confirmacion).navigate(R.id.nav_misordenesconfirmacion);

                try {
                    jsonObject = json.getJSONObject(0);
                    mio_cls_Consulta_detalle_pedido.odEstadoAceptado = jsonObject.optString("esorEstado");
                    mio_cls_Consulta_detalle_pedido.odFechaAceptado = jsonObject.optString("esorFecha");
                    mio_cls_Consulta_detalle_pedido.odHoraAceptado = jsonObject.optString("esorHora");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al actualizar" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void cancelarOrden() {
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.mio_lytcancelarorden);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        final Button cancelar = dialog.findViewById(R.id.mio_cobtncancelar);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Button aceptar = dialog.findViewById(R.id.mio_cobtnaceptar);
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebServiceRechazarOrden();
                //Navigation.findNavController(cancelacion).navigate(R.id.nav_misordenesdetallepedido);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void cargarWebServiceRechazarOrden()
    {
        String url = Util.RUTA + "m_orden_estado_mis_ordenes.php?sp_idOrden="+mio_cls_Consulta_detalle_pedido.idOrden+"&sp_odEstado=RECHAZADO";
        url = url.replace(" ", "%20");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json = null;
                json = response.optJSONArray("sp_m_repartidor_por_orden_mis_ordenes");
                JSONObject jsonObject = null;
                Toast.makeText(getContext(), "La orden ha sido rechazada", Toast.LENGTH_SHORT).show();

                try {
                    jsonObject = json.getJSONObject(0);
                    mio_cls_Consulta_detalle_pedido.odEstadoRechazado = jsonObject.optString("esorEstado");
                    mio_cls_Consulta_detalle_pedido.odFechaRechazado = jsonObject.optString("esorFecha");
                    mio_cls_Consulta_detalle_pedido.odHoraRechazado = jsonObject.optString("esorHora");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al actualizar" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }




}