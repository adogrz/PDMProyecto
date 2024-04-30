package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuCompra extends AppCompatActivity {
 Button iragregarcompra;
 Button iraconsultarcompra;
 Button iractualizarcompra;
 Button iraeliminarcompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_compra);

        iragregarcompra=(Button)findViewById(R.id.iragregarcompra);
        iragregarcompra.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent iracompra =new Intent( MenuCompra.this, AgregarCompra.class);
                startActivity(iracompra);
            }


        });
        iraconsultarcompra=(Button)findViewById(R.id.iraconsultarcompra);
        iraconsultarcompra.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent iraconsultarcompra =new Intent( MenuCompra.this, ConsultarCompra.class);
                startActivity(iraconsultarcompra);
            }


        });
        iractualizarcompra=(Button)findViewById(R.id.iractualizarcompra);
        iractualizarcompra.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent iractualizarcompra =new Intent( MenuCompra.this, ActualizarCompra.class);
                startActivity(iractualizarcompra);
            }


        });
        iraeliminarcompra=(Button)findViewById(R.id.iraeliminarcompra);
        iraeliminarcompra.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent iraeliminarcompra =new Intent( MenuCompra.this, EliminarCompra.class);
                startActivity(iraeliminarcompra);
            }


        });
    }
}