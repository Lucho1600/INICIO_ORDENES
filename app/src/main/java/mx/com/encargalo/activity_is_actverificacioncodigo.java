package mx.com.encargalo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import mx.com.encargalo.tendero.Inicio_sesion.MainActivity;
import mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_ordenes.mio_frgmisordenesprincipal;

public class activity_is_actverificacioncodigo extends AppCompatActivity {
    Button btnvalidar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_actverificacioncodigo);
btnvalidar=(Button)findViewById(R.id.is_vcbtncontinuar);
//VALIDAR Y ENVIAR A MIS ORDENES PRINCIPAL
btnvalidar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent verif = new Intent(activity_is_actverificacioncodigo.this, MainActivity.class);
        startActivity(verif);
    }
});

    }
}

