package sv.ues.fia.eisi.pdmproyectoetapa1.ui.compra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Compra;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleCompra;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Proveedor;

public class InsertarCompraActivity extends AppCompatActivity {
    EditText etIdCompra, etCantidadCompra;
    Spinner spinnerArticulo, spinnerProveedor;
    MaterialButton btnInsertarCompra;
    ControlBaseDatos helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_compra);
        etIdCompra = findViewById(R.id.et_id_compra);
        etCantidadCompra = findViewById(R.id.et_cantidad);
        spinnerArticulo = findViewById(R.id.spinner_articulo);
        spinnerProveedor = findViewById(R.id.spinner_proveedor);
        btnInsertarCompra = findViewById(R.id.btn_insertar_compra);

        helper = ControlBaseDatos.obtenerInstancia(this);

        llenarSpinners();

        btnInsertarCompra.setOnClickListener(v -> insertarNuevaCompra());
    }

    private void llenarSpinners() {
        List<Articulo> articulos = null;
        List<Proveedor> proveedores = null;

        try {
            articulos = helper.getArticuloDAO().obtenerTodos();
            proveedores = helper.getProveedorDAO().obtenerTodos();
            if (articulos.size() == 0) {
                Toast.makeText(this, "No hay artículos registrados. Registre articulos antes.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (DAOException e) {
            Toast.makeText(this, "Error al acceder a la base de datos", Toast.LENGTH_SHORT).show();
        }

        if (articulos != null && proveedores != null) {
            ArrayAdapter<Articulo> adapterArticulo = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, articulos);
            ArrayAdapter<Proveedor> adapterProveedor = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, proveedores);

            spinnerArticulo.setAdapter(adapterArticulo);
            spinnerProveedor.setAdapter(adapterProveedor);
        }
    }

    private void insertarNuevaCompra() {
        // Obtener los datos de los campos
        String idCompra = etIdCompra.getText().toString();
        Articulo articulo = (Articulo) spinnerArticulo.getSelectedItem();
        Proveedor proveedor = (Proveedor) spinnerProveedor.getSelectedItem();
        String cantidadCompra = etCantidadCompra.getText().toString();

        // Validar que los campos no estén vacíos
        if (idCompra.isEmpty() || cantidadCompra.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String idArticulo = articulo.getIdArticulo();
        String idProveedor = proveedor.getIdProveedor();

        try {
            Compra nuevaCompra = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Insertar la compra
                nuevaCompra = new Compra(idCompra, LocalDate.now().toString(), idProveedor);
                helper.getCompraDAO().insertar(nuevaCompra);
            }

            // Calcular subtotal
            int cantidadComprada = Integer.parseInt(cantidadCompra);
            double subtotal = articulo.getPrecioUnitario() * cantidadComprada;

            // Insertar detalle de compra
            helper.getDetalleCompraDAO().insertar(new DetalleCompra(cantidadComprada, subtotal,
                    idCompra, idArticulo));

            // Actualizar existencia de articulo
            articulo.setStock(articulo.getStock() + cantidadComprada);
            helper.getArticuloDAO().modificar(articulo);

            // Actualizar monto total de la compra
            if (nuevaCompra != null) {
                nuevaCompra.setMontoTotal(subtotal);
                helper.getCompraDAO().modificar(nuevaCompra);
            }

            Toast.makeText(this, "Compra insertada correctamente", Toast.LENGTH_SHORT).show();
            finish();
        } catch (DAOException e) {
            Toast.makeText(this, "Error al insertar la compra: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}