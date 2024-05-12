package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.adapter.CustomSpinnerAdapter;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LocalDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;

public class InsertarArticuloActivity extends AppCompatActivity {
    EditText editIdArticulo, editNombreArticulo, editDescripcionArticulo, editPrecioArticulo;
    Spinner spinnerProveedor, spinnerTipoArticulo, spinnerLocal;
    FloatingActionButton botGuardarArticulo;
    ControlBaseDatos cBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_articulo);
        editIdArticulo = findViewById(R.id.editTextIdArticulo);
        editNombreArticulo = findViewById(R.id.editTextNombreArticulo);
        editDescripcionArticulo = findViewById(R.id.editTextDescripcionArticulo);
        editPrecioArticulo = findViewById(R.id.editTextPrecioUnitarioArticulo);
        spinnerProveedor = findViewById(R.id.spinnerProveedorArticulo);
        spinnerTipoArticulo = findViewById(R.id.spinnerTipoArticuloArticulo);
        spinnerLocal = findViewById(R.id.spinnerLocalArticulo);
        botGuardarArticulo = findViewById(R.id.fabGuardarActiculo);

        cBD = ControlBaseDatos.obtenerInstancia(InsertarArticuloActivity.this);

        llenarSpinners();

        botGuardarArticulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarArticulo();
            }
        });
    }

    private void llenarSpinners() {
        List<Proveedor> proveedores = null;
        List<TipoArticulo> tiposArticulo = null;
        List<Local> locales = null;
        try {
            proveedores = cBD.getProveedorDAO().obtenerTodos();
            tiposArticulo = cBD.getTipoArticuloDAO().obtenerTodos();
            locales = cBD.getLocalDAO().obtenerTodos();
        } catch (DAOException e) {
            Toast.makeText(InsertarArticuloActivity.this, "Error al acceder a la base de datos",
                    Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<Proveedor> proveedorAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                android.R.layout.simple_spinner_item, proveedores);
        ArrayAdapter<TipoArticulo> tipoArticuloAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                android.R.layout.simple_spinner_item, tiposArticulo);
        ArrayAdapter<Local> localAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                android.R.layout.simple_spinner_item, locales);

        // Setup
        spinnerProveedor.setAdapter(proveedorAdapter);
        spinnerTipoArticulo.setAdapter(tipoArticuloAdapter);
        spinnerLocal.setAdapter(localAdapter);
    }

    private void insertarArticulo() {
        // Obtener los datos de los campos
        String idArticulo = editIdArticulo.getText().toString();
        String nombreArticulo = editNombreArticulo.getText().toString();
        String descripcionArticulo = editDescripcionArticulo.getText().toString();
        String precioArticulo = editPrecioArticulo.getText().toString();
        Proveedor proveedor = (Proveedor) spinnerProveedor.getSelectedItem();
        TipoArticulo tipoArticulo = (TipoArticulo) spinnerTipoArticulo.getSelectedItem();
        Local local = (Local) spinnerLocal.getSelectedItem();

        // Validar que los campos no estén vacíos
        if (idArticulo.isEmpty() || nombreArticulo.isEmpty() || descripcionArticulo.isEmpty() || precioArticulo.isEmpty()) {
            Toast.makeText(InsertarArticuloActivity.this, "Por favor, rellene todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto Articulo
        Articulo articulo = new Articulo(idArticulo, nombreArticulo,
                Double.parseDouble(precioArticulo), 0, descripcionArticulo,
                proveedor.getIdProveedor(), tipoArticulo.getId());

        // Insertar el objeto Articulo
        try {
            cBD.getArticuloDAO().insertar(articulo);
            Toast.makeText(InsertarArticuloActivity.this, "Artículo insertado correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (DAOException e) {
            Toast.makeText(InsertarArticuloActivity.this, "Error al insertar el artículo",
                    Toast.LENGTH_SHORT).show();
        }
    }
}