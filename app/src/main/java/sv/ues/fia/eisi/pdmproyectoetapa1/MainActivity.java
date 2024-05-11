package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.os.Bundle;
import android.content.Intent;
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
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;

public class MainActivity extends AppCompatActivity {
   Button btnmedicamentos;

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

      btnmedicamentos=(Button)findViewById(R.id.btn_Medicamentos);
        btnmedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnmedicamentos = new Intent(MainActivity.this, MenuMedicamentos.class);
                startActivity(btnmedicamentos);
            }
        });

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(MainActivity.this);
        ProveedorDAO proveedorDAO=db.getProveedorDAO();

        try {
            List<Proveedor> proveedores=proveedorDAO.obtenerTodos();

        }catch (DAOException e){
            throw new RuntimeException(e);
        }





    }
}