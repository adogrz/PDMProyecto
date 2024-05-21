package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.LocalArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.TipoArticulo;

public class ModificarArticuloActivity extends AppCompatActivity {
    EditText editIdArticulo, editNombreArticulo, editDescripcionArticulo, editPrecioArticulo;
    Spinner spinnerProveedor, spinnerTipoArticulo, spinnerLocal;
    Button botConsultarArticulo;
    FloatingActionButton botGuardarArticulo;
    ControlBaseDatos cBD;
    List<Proveedor> proveedores = null;
    List<TipoArticulo> tiposArticulo = null;
    List<Local> locales = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_articulo);
        editIdArticulo = findViewById(R.id.editTextIdArticulo);
        editNombreArticulo = findViewById(R.id.editTextNombreArticulo);
        editDescripcionArticulo = findViewById(R.id.editTextDescripcionArticulo);
        editPrecioArticulo = findViewById(R.id.editTextPrecioUnitarioArticulo);
        spinnerProveedor = findViewById(R.id.spinnerProveedorArticulo);
        spinnerTipoArticulo = findViewById(R.id.spinnerTipoArticuloArticulo);
        spinnerLocal = findViewById(R.id.spinnerLocalArticulo);
        botConsultarArticulo = findViewById(R.id.btnConsultarArticulo);
        botGuardarArticulo = findViewById(R.id.fabGuardarActiculo);

        cBD = ControlBaseDatos.obtenerInstancia(ModificarArticuloActivity.this);

        llenarSpinners();

        botConsultarArticulo.setOnClickListener(v -> consultarArticulo());


        botGuardarArticulo.setOnClickListener(v -> modificarArticulo());
    }

    private void consultarArticulo() {
        // Obtener el id del artículo
        String idArticulo = editIdArticulo.getText().toString();

        // Validar el id del artículo
        if (idArticulo.isEmpty()) {
            Toast.makeText(ModificarArticuloActivity.this, "Ingrese el id del artículo",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Consultar el artículo
        Articulo articulo = null;

        try {
            articulo = cBD.getArticuloDAO().obtener(idArticulo);
        } catch (DAOException e) {
            Toast.makeText(ModificarArticuloActivity.this, "Error: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }

        // Setear los datos del artículo en los campos de texto
        if (articulo != null) {
            editNombreArticulo.setText(articulo.getNombre());
            editDescripcionArticulo.setText(articulo.getDescripcion());
            editPrecioArticulo.setText(String.valueOf(articulo.getPrecioUnitario()));
        } else {
            Toast.makeText(ModificarArticuloActivity.this, "No se encontró el artículo",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void llenarSpinners() {
        try {
            proveedores = cBD.getProveedorDAO().obtenerTodos();
            tiposArticulo = cBD.getTipoArticuloDAO().obtenerTodos();
            locales = cBD.getLocalDAO().obtenerTodos();
        } catch (DAOException e) {
            Toast.makeText(ModificarArticuloActivity.this, "Error al acceder a la base de datos",
                    Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<Proveedor> proveedorAdapter = new ArrayAdapter<>(ModificarArticuloActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, proveedores);
        ArrayAdapter<TipoArticulo> tipoArticuloAdapter = new ArrayAdapter<>(ModificarArticuloActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, tiposArticulo);
        ArrayAdapter<Local> localAdapter = new ArrayAdapter<>(ModificarArticuloActivity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, locales);

        // Setup
        spinnerProveedor.setAdapter(proveedorAdapter);
        spinnerTipoArticulo.setAdapter(tipoArticuloAdapter);
        spinnerLocal.setAdapter(localAdapter);
    }

    private void modificarArticulo() {
        // Obtener los datos de los campos de texto y spinners
        String idArticulo = editIdArticulo.getText().toString();
        String nombreArticulo = editNombreArticulo.getText().toString();
        String descripcionArticulo = editDescripcionArticulo.getText().toString();
        String precioArticulo = editPrecioArticulo.getText().toString();
        Proveedor proveedor = (Proveedor) spinnerProveedor.getSelectedItem();
        TipoArticulo tipoArticulo = (TipoArticulo) spinnerTipoArticulo.getSelectedItem();
        Local local = (Local) spinnerLocal.getSelectedItem();

        // Validar los datos
        if (idArticulo.isEmpty() || nombreArticulo.isEmpty()  || precioArticulo.isEmpty()) {
            Toast.makeText(ModificarArticuloActivity.this, "Por favor, rellene todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto Articulo
        Articulo articulo = new Articulo(idArticulo, nombreArticulo,
                Double.parseDouble(precioArticulo), descripcionArticulo, proveedor.getIdProveedor(),
                tipoArticulo.getId());

        // Crear el objeto LocalArticulo
        LocalArticulo localArticulo = new LocalArticulo(local.getIdLocal(), idArticulo);

        // Modificar el artículo
        try {
            cBD.getArticuloDAO().modificar(articulo);
            cBD.getLocalArticuloDAO().modificar(localArticulo);
            Toast.makeText(ModificarArticuloActivity.this, "Artículo modificado correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (DAOException e) {
            Toast.makeText(ModificarArticuloActivity.this, "Error al modificar el artículo",
                    Toast.LENGTH_SHORT).show();
        }
    }
}