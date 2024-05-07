package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AgregarCompra extends AppCompatActivity {
Button irart;
Button irmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_compra);

        irart=(Button)findViewById(R.id.irart);
        irart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent irart =new Intent( AgregarCompra.this, InsertCompraArticulo.class);
                startActivity(irart);
            }


        });
        irmed=(Button)findViewById(R.id.irmed);
        irmed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent irmed =new Intent( AgregarCompra.this, InsertCompraMedicamento.class);
                startActivity(irmed);
            }


        });
    }
}
