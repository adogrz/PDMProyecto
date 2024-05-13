package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MenuRecetaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_receta);
        //metodo para cambiar a RecetasInsertarActivity
        findViewById(R.id.btn_insertarReceta).setOnClickListener(v -> {
            startActivity(new Intent(MenuRecetaActivity.this, RecetasInsertarActivity.class));
        });
        //metodo para cambiar a RecetasConsultarActivity
        findViewById(R.id.btn_consultarReceta).setOnClickListener(v -> {
            startActivity(new Intent(MenuRecetaActivity.this, RecetasConsultarActivity.class));
        });
        //metodo para cambiar a RecetasActualizarActivity
        findViewById(R.id.btn_actualizarReceta).setOnClickListener(v -> {
            startActivity(new Intent(MenuRecetaActivity.this, RecetasActualizarActivity.class));
        });
        //metodo para cambiar a RecetasEliminarActivity
        findViewById(R.id.btn_eliminarReceta).setOnClickListener(v -> {
            startActivity(new Intent(MenuRecetaActivity.this, RecetasEliminarActivity.class));
        });
    }
}