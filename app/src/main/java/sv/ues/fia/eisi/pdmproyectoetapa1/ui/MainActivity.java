package sv.ues.fia.eisi.pdmproyectoetapa1.ui;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;

import sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo.ArticuloMenuActivity;
import sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento.MenuMedicamentos;
import sv.ues.fia.eisi.pdmproyectoetapa1.ui.recetamedica.MenuRecetaActivity;
import sv.ues.fia.eisi.pdmproyectoetapa1.ui.venta.MenuVentasActivity;

public class MainActivity extends AppCompatActivity {
    Button btonMenuVenta, btonMenuArticulo, btonMenuMedicamento, btonMenuRecetaMedica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btonMenuVenta = findViewById(R.id.btn_Ventas);
        btonMenuArticulo = findViewById(R.id.btn_menu_articulos);
        btonMenuMedicamento = findViewById(R.id.btn_menu_medicamentos);
        btonMenuRecetaMedica = findViewById(R.id.btn_recetaMedica);

        // Asignar listeners a los botones
        btonMenuVenta.setOnClickListener(v -> navigateToActivity(MenuVentasActivity.class));
        btonMenuArticulo.setOnClickListener(v -> navigateToActivity(ArticuloMenuActivity.class));
        btonMenuMedicamento.setOnClickListener(v -> navigateToActivity(MenuMedicamentos.class));
        btonMenuRecetaMedica.setOnClickListener(v -> navigateToActivity(MenuRecetaActivity.class));
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