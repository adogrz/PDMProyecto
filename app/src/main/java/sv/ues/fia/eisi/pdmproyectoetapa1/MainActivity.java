package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.BaseDatosFarmacia;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;

public class MainActivity extends AppCompatActivity {
    Button iracompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        iracompra = (Button) findViewById(R.id.iracompra);
        iracompra.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent iracompra = new Intent(MainActivity.this, MenuCompra.class);
                startActivity(iracompra);
            }


        });
        ControlBaseDatos DB = ControlBaseDatos.obtenerInstancia(MainActivity.this);
        ProveedorDAO PRDAO = DB.getProveedorDAO();
        try {
            List<Proveedor> proveedors = PRDAO.obtenerTodos();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }


    }
}