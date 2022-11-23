package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_ordenes;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mx.com.encargalo.Adapters.mio_adaprvOrdenesOrden;
import mx.com.encargalo.Model.mio_mdlOrdenesOrden;
import mx.com.encargalo.Model.mio_mdlOrdenesRepartidor;
import mx.com.encargalo.R;
import mx.com.encargalo.Utils.Util;
import mx.com.encargalo.Utils.mio_cls_Consulta_detalle_pedido;

public class mio_frgdetallepedido extends Fragment {
    Button dpbtndetalle,dpbtnchat;
    ImageView mio_imgvw1,mio_imgvw2, mio_imgvw3, mio_imgvw4, mio_imgvw5, mio_imgvw6, mio_imgvw7;
    TextView mio_txtnumpedido,mio_txtLdetalllecliente,  mio_dpedtdireccionentrega, mio_txtdetallesolicitado, mio_txtdetalleaceptadorechazado, mio_txtdetalleenprepa, mio_txtdetallepreparado, mio_txtdetalleencamino, mio_txtdetalleentregado, mio_txtdetallereclamado, mio_txtRepartidor, mio_txtestadorepartidor, mio_txtestadoaceptadorechazado;
    RequestQueue requestQueue;
    Spinner is_sptipopersona;
    View confirmacion;
    List<String> nomRepartidores = new ArrayList<String>();
    List<Integer> idRepartidores = new ArrayList<Integer>();
    Dialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mio_frgdetallepedido, container, false);
        dpbtndetalle= vista.findViewById(R.id.mio_dpbtndetalle);
        dpbtnchat=vista.findViewById(R.id.mio_dpbtnchat);
        is_sptipopersona = vista.findViewById(R.id.is_sptipopersona);
        mio_txtLdetalllecliente = vista.findViewById(R.id.mio_txtLdetalllecliente);
        mio_txtnumpedido = vista.findViewById(R.id.mio_txtnumpedido);
        mio_dpedtdireccionentrega = vista.findViewById(R.id.mio_dpedtdireccionentrega);
        mio_txtestadorepartidor = vista.findViewById(R.id.mio_txtestadorepartidor);
        mio_txtdetallesolicitado = vista.findViewById(R.id.mio_txtdetallesolicitado);
        mio_txtdetalleaceptadorechazado = vista.findViewById(R.id.mio_txtdetalleaceptadorechazado);
        mio_txtdetalleenprepa = vista.findViewById(R.id.mio_txtdetalleenprepa);
        mio_txtdetallepreparado = vista.findViewById(R.id.mio_txtdetallepreparado);
        mio_txtdetalleencamino = vista.findViewById(R.id.mio_txtdetalleencamino);
        mio_txtdetalleentregado = vista.findViewById(R.id.mio_txtdetalleentregado);
        mio_txtdetallereclamado = vista.findViewById(R.id.mio_txtdetallereclamado);
        mio_txtRepartidor = vista.findViewById(R.id.mio_txtRepartidor);
        mio_txtestadoaceptadorechazado = vista.findViewById(R.id.mio_txtestadoaceptadorechazado);
        mio_imgvw1 = vista.findViewById(R.id.mio_imgvw1);
        mio_imgvw2 = vista.findViewById(R.id.mio_imgvw2);
        mio_imgvw3 = vista.findViewById(R.id.mio_imgvw3);
        mio_imgvw4 = vista.findViewById(R.id.mio_imgvw4);
        mio_imgvw5 = vista.findViewById(R.id.mio_imgvw5);
        mio_imgvw6 = vista.findViewById(R.id.mio_imgvw6);
        mio_imgvw7 = vista.findViewById(R.id.mio_imgvw7);



        mio_txtdetallesolicitado.setText(formatearFecha(mio_cls_Consulta_detalle_pedido.odFechaSolicitado)+ "a las " + mio_cls_Consulta_detalle_pedido.odHoraSolicitado);





        requestQueue = Volley.newRequestQueue(getContext());

        listaHistorial();

        mio_txtnumpedido.setText(String.valueOf(mio_cls_Consulta_detalle_pedido.idOrden));
        mio_txtLdetalllecliente.setText(mio_cls_Consulta_detalle_pedido.perNombreCompleto);
        mio_imgvw1.setImageResource(R.drawable.verificacion_verde);

        if(mio_cls_Consulta_detalle_pedido.idRepartidor == 0 ){
            dpbtnchat.setVisibility(vista.GONE);
            mio_txtRepartidor.setVisibility(vista.GONE);
            llenarSpinnerRepartidor();


        }else{
            mio_txtestadorepartidor.setText("Repartidor selecccionado:");
            is_sptipopersona.setVisibility(vista.GONE);
            mio_txtRepartidor.setText(mio_cls_Consulta_detalle_pedido.repNombres);
        }

        dpbtndetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                is_sptipopersona.setAdapter(null);
                Navigation.findNavController(view).navigate(R.id.nav_misordenesdetalleproducto);


            }
        });

        dpbtnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Navigation.findNavController(view).navigate(R.id.nav_misordenesconversacion);
            }
        });

        llamarWsUbicación(String.valueOf(mio_cls_Consulta_detalle_pedido.idOrden));

        is_sptipopersona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i!=0){

                    confirmacion = view;

                    actualizarRepartidor(idRepartidores.get(i), mio_cls_Consulta_detalle_pedido.idOrden);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return vista;
    }

    private void listaHistorial() {

        String URL = Util.RUTA+"c_lista_estados_orden_mis_ordenes.php?sp_idOrden="+ mio_cls_Consulta_detalle_pedido.idOrden;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json = null;
                json = response.optJSONArray("sp_c_lista_estados_orden");
                JSONObject jsonObject = null;
                try {
                    for (int i = 0; i < json.length(); i++) {

                        jsonObject = json.getJSONObject(i);
                        String esorEstado = jsonObject.optString("esorEstado");
                        String esorFecha = jsonObject.optString("esorFecha");
                        String esorHora = jsonObject.optString("esorHora");

                        switch (esorEstado) {
                            case "ACEPTADO": {
                                mio_txtdetalleaceptadorechazado.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_txtestadoaceptadorechazado.setText("Aceptado");
                                mio_imgvw2.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "RECHAZADO": {
                                mio_txtdetalleaceptadorechazado.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_txtestadoaceptadorechazado.setText("Rechazado");
                                mio_imgvw2.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "EN PREPARACION": {
                                mio_txtdetalleenprepa.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_imgvw3.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "PREPARADO": {
                                mio_txtdetallepreparado.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_imgvw4.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "EN CAMINO": {
                                mio_txtdetalleencamino.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_imgvw5.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "ENTREGADO": {
                                mio_txtdetalleentregado.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_imgvw6.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                            case "RECLAMADO": {
                                mio_txtdetallereclamado.setText(formatearFecha(esorFecha) + " a las " + esorHora);
                                mio_imgvw7.setImageResource(R.drawable.verificacion_verde);
                                break;
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void actualizarRepartidor(int idRepartidor,int idOrden) {

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.mio_lytactualizarrepartidor);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Button arcancelar =  dialog.findViewById(R.id.mio_arbtncancelar);
        arcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Button araceptar = dialog.findViewById(R.id.mio_arbtnaceptar);
        araceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarOrdenRepartidor(idRepartidor, idOrden);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void actualizarOrdenRepartidor(int idRepartidor, int idOrden) {
        String URL = Util.RUTA+"m_repartidor_por_orden_mis_ordenes.php?sp_idOrden="+String.valueOf(idOrden)+"&sp_idRepartidor="+String.valueOf(idRepartidor);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json = null;
                json = response.optJSONArray("sp_m_repartidor_por_orden_mis_ordenes");
                JSONObject jsonObject = null;
                try {
                    jsonObject = json.getJSONObject(0);
                    int idRep = jsonObject.optInt("idRepartidor");
                    String perNomRep = jsonObject.optString("perNombreCompleto");

                    mio_cls_Consulta_detalle_pedido.idRepartidor=idRep;
                    mio_txtRepartidor.setText(perNomRep);
                    mio_txtestadorepartidor.setText("Repartidor selecccionado:");

                    dpbtnchat.setVisibility(getView().VISIBLE);
                    mio_txtRepartidor.setVisibility(getView().VISIBLE);
                    is_sptipopersona.setVisibility(getView().GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error:"+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void llenarSpinnerRepartidor() {



        String URL = Util.RUTA+"c_lista_repartidor_orden_mis_ordenes.php?sp_idTienda=3";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(nomRepartidores.size() !=0){
                    nomRepartidores.clear();
                }

                mio_mdlOrdenesRepartidor repartidor = null;
                JSONArray json = null;
                json = response.optJSONArray("sp_c_lista_repartidor_orden");
                nomRepartidores.add("SELECCIONE");
                idRepartidores.add(0);

                try {
                    for (int i = 0; i < json.length(); i++) {

                        repartidor = new mio_mdlOrdenesRepartidor();
                        JSONObject jsonObject = null;

                        jsonObject = json.getJSONObject(i);
                        repartidor.setIdRepartidor(jsonObject.optInt("idRepartidor"));
                        repartidor.setRepNombreCompleto(jsonObject.optString("perNombreCompleto"));
                        nomRepartidores.add(repartidor.getRepNombreCompleto());
                        idRepartidores.add(repartidor.getIdRepartidor());

                    }

                    is_sptipopersona.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,nomRepartidores));

                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof ParseError) {
                    Toast.makeText(getContext(), "No existe registro", Toast.LENGTH_SHORT).show();
                }
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private String formatearFecha(String fecha) {
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = parseador.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formateador.format(date);
    }

    private void llamarWsUbicación(String urlIdOrden) {


        String URL = Util.RUTA +"c_coordenadas_de_orden_entregada.php?idOrden="+urlIdOrden;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String latitud = response.getString("Latitud");
                            String longitud = response.getString("Longitud");

                            mio_dpedtdireccionentrega.setText(coordenadasDireccion(latitud, longitud));
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    //Función para transformar coordenadas a dirección
    private String coordenadasDireccion(String latitud, String longitud) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        String NombreDireccion = "";
        try{
            List<Address> listAddress = geocoder.getFromLocation(Double.parseDouble(latitud), Double.parseDouble(longitud), 1);
            NombreDireccion =  listAddress.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NombreDireccion;
    }


}