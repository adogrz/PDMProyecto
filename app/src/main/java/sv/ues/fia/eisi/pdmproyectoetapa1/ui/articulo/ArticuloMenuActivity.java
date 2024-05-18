package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;

public class ArticuloMenuActivity extends AppCompatActivity {
    Button btnConsultarArticulo, btnInsertarArticulo, btnModificarArticulo, btnEliminarArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_menu);
        btnConsultarArticulo = findViewById(R.id.btn_consultar_articulo);
        btnInsertarArticulo = findViewById(R.id.btn_insertar_articulo);
        btnModificarArticulo = findViewById(R.id.btn_modificar_articulo);
        btnEliminarArticulo = findViewById(R.id.btn_eliminar_articulo);

        btnConsultarArticulo.setOnClickListener(v -> navigateToActivity(ConsultarArticuloActivity.class));
        btnInsertarArticulo.setOnClickListener(v -> navigateToActivity(InsertarArticuloActivity.class));
        btnModificarArticulo.setOnClickListener(v -> navigateToActivity(ModificarArticuloActivity.class));
        btnEliminarArticulo.setOnClickListener(v -> navigateToActivity(EliminarArticuloActivity.class));
    }

    /**
     * Metodo para navegar a una actividad
     * @param targetActivity Actividad a la que se desea navegar
     */
    private void navigateToActivity(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }
}