package sv.ues.fia.eisi.pdmproyectoetapa1.ui.compra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;

public class MenuCompraActivity extends AppCompatActivity {
    MaterialButton btnInsertarCompra, btnConsultarCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_compra);
        btnInsertarCompra = findViewById(R.id.btn_insertar_comp);
        btnConsultarCompra = findViewById(R.id.btn_consultar_comp);

        btnInsertarCompra.setOnClickListener(v -> navigateToActivity(InsertarCompraActivity.class));
        btnConsultarCompra.setOnClickListener(v -> navigateToActivity(ConsultarCompraActivity.class));
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