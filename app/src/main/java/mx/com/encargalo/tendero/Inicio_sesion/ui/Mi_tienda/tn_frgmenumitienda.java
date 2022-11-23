package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_tienda;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mx.com.encargalo.R;



public class tn_frgmenumitienda extends Fragment {
    String iddocu;
    TextView txtdocu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       Intent intent = this.getActivity().getIntent();

       final View view = inflater.inflate(R.layout.fragment_tn_frgmenumitienda, container, false);
       TextView txtdocu = view.findViewById(R.id.textView2);
      this.iddocu = intent.getStringExtra("idDocumentoPersona");
      txtdocu.setText("" + iddocu);
        return view;


        //    Inflate the layout for this fragment
    //   return inflater.inflate(R.layout.fragment_tn_frgmenumitienda, container, false);

  }
}