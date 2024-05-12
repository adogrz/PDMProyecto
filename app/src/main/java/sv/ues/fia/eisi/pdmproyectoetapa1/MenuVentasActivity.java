package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuVentasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ventas);

        //Botones del menu de ventas
        Button botonAgregarVenta = findViewById(R.id.btn_agregarVenta);
        Button botonActualizarVenta = findViewById(R.id.btn_actualizarVenta);

        //Boton para agregar una venta
        botonAgregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, AgregarVentaActivity.class);
                startActivity(intent);
            }
        });

        //Boton para actualizar una venta
        botonActualizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, AtualizarVentaActivity.class);
                startActivity(intent);
            }
        });
    }
}