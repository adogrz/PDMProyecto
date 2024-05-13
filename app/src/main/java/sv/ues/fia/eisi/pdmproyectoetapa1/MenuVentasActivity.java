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
        Button botonConsultarVenta = findViewById(R.id.btn_consultarVenta);
        Button botonEliminarVenta = findViewById(R.id.btn_eliminarVenta);

        //Boton para ir a la vista de agregar una venta
        botonAgregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, AgregarVentaActivity.class);
                startActivity(intent);
            }
        });

        //Boton para ir a la vista de actualizar una venta
        botonActualizarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, AtualizarVentaActivity.class);
                startActivity(intent);
            }
        });

        //Boton para ir a la vista de consultar una venta
        botonConsultarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, ConsultarVentaActivity.class);
                startActivity(intent);
            }
        });

        //Boton para ir a la vista de eliminar una venta
        botonEliminarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuVentasActivity.this, EliminarVentaActivity.class);
                startActivity(intent);
            }
        });
    }
}