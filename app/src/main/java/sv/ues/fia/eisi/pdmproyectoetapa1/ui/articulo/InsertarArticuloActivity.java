package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LocalDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.LocalArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.TipoArticulo;

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

        botGuardarArticulo.setOnClickListener(v -> insertarArticulo());
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

        if (proveedores != null && tiposArticulo != null && locales != null) {
            ArrayAdapter<Proveedor> proveedorAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, proveedores);
            ArrayAdapter<TipoArticulo> tipoArticuloAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tiposArticulo);
            ArrayAdapter<Local> localAdapter = new ArrayAdapter<>(InsertarArticuloActivity.this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, locales);

            // Setup
            spinnerProveedor.setAdapter(proveedorAdapter);
            spinnerTipoArticulo.setAdapter(tipoArticuloAdapter);
            spinnerLocal.setAdapter(localAdapter);
        } else {
            Toast.makeText(this, "No existe ningun proveedor, tipo articulo o locales",
                    Toast.LENGTH_SHORT).show();
        }
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
                Double.parseDouble(precioArticulo), 100, descripcionArticulo,
                proveedor.getIdProveedor(), tipoArticulo.getId());

        // Crear el objeto LocalArticulo
        LocalArticulo localArticulo = new LocalArticulo(local.getIdLocal(), idArticulo);

        // Insertar el objeto Articulo
        try {
            cBD.getArticuloDAO().insertar(articulo);
            cBD.getLocalArticuloDAO().insertar(localArticulo);
            Toast.makeText(InsertarArticuloActivity.this, "Artículo insertado correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (DAOException e) {
            Toast.makeText(InsertarArticuloActivity.this, "Error al insertar el artículo",
                    Toast.LENGTH_SHORT).show();
        }
    }
}