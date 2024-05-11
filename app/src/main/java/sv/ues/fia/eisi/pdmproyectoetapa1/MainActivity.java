package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;

public class MainActivity extends AppCompatActivity {
    TextView textView;

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

        /*


        textView = findViewById(R.id.textViewMenu);

        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(MainActivity.this);
        ProveedorDAO proveedorDAO = controlBaseDatos.getProveedorDAO();

        try {
            List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
            Proveedor primerProveedor = proveedores.get(1);
            Proveedor proveedorEncontrado = proveedorDAO.obtener(primerProveedor.getIdProveedor());
            textView.setText(proveedorEncontrado.getNombre());
            Toast.makeText(MainActivity.this, "Proveedores obtenidos correctamente.", Toast.LENGTH_SHORT).show();
        } catch (DAOException e) {
            Toast.makeText(MainActivity.this, "Error al obtener los proveedores.", Toast.LENGTH_SHORT).show();
        }
        */

        Button botonVentas = findViewById(R.id.btn_Ventas);
        botonVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuVentasActivity.class);
                startActivity(intent);
            }
        });
    }
}