package sv.ues.fia.eisi.pdmproyectoetapa1.ui.compra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Compra;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleCompra;

public class ConsultarCompraActivity extends AppCompatActivity {
    EditText etIdCompra;
    TextView tvFechaCompra, tvNombreArticulo, tvNombreProveedor, tvCantidadCompra, tvPrecioUnitario,
            tvTotalCompra;
    Button btnConsularCompra;
    ControlBaseDatos helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_compra);
        etIdCompra = findViewById(R.id.et_id_compra);
        tvFechaCompra = findViewById(R.id.tv_fecha_compra);
        tvNombreArticulo = findViewById(R.id.tv_nombre_articulo);
        tvNombreProveedor = findViewById(R.id.tv_nombre_proveedor);
        tvCantidadCompra = findViewById(R.id.tv_cantidad_compra);
        tvPrecioUnitario = findViewById(R.id.tv_precio_unitario);
        tvTotalCompra = findViewById(R.id.tv_monto_total);
        btnConsularCompra = findViewById(R.id.btn_consultar_compra);

        helper = ControlBaseDatos.obtenerInstancia(this);

        btnConsularCompra.setOnClickListener(v -> consultarCompra());
    }

    private void consultarCompra() {
        // Obtener id de la compra
        String idCompra = etIdCompra.getText().toString();

        if (idCompra.isEmpty()) {
            Toast.makeText(this, "Ingrese el id de la compra", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Compra compra = helper.getCompraDAO().obtener(idCompra);
            DetalleCompra detalleCompra = helper.getDetalleCompraDAO().obtenerPorIdCompra(idCompra);
            Articulo articulo = helper.getArticuloDAO().obtener(detalleCompra.getIdArticulo());
            String nombreProveedor = helper.getProveedorDAO().obtener(compra.getIdProveedor()).getNombre();

            // Mostrar datos de la compra
            tvFechaCompra.setText(compra.getFechaCompra());
            tvNombreArticulo.setText(articulo.getNombre());
            tvNombreProveedor.setText(nombreProveedor);
            tvCantidadCompra.setText(String.valueOf(detalleCompra.getCantidadArticulo()));
            tvPrecioUnitario.setText(String.valueOf(articulo.getPrecioUnitario()));
            String montoTotal = "$ " + compra.getMontoTotal();
            tvTotalCompra.setText(montoTotal);
        } catch (DAOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            tvFechaCompra.setText("");
            tvNombreArticulo.setText("");
            tvNombreProveedor.setText("");
            tvCantidadCompra.setText("");
            tvPrecioUnitario.setText("");
            tvTotalCompra.setText("");
        }
    }
}