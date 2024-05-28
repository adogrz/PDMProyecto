package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonObject;
import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.GetDataTask;

public class ConsultarArticuloActivity extends AppCompatActivity {
    private static final String TAG = ConsultarArticuloActivity.class.getSimpleName();
    private EditText editIdArticulo;
    private TextView tvNombreArticulo, tvDescripcion, tvPrecioUnitario, tvStock, tvTipoArticulo,
            tvLocalArticulo, tvProveedorArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_articulo);

        editIdArticulo = findViewById(R.id.et_id_articulo);
        tvNombreArticulo = findViewById(R.id.tvNombreArticulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPrecioUnitario = findViewById(R.id.tvPrecioUnitario);
        tvStock = findViewById(R.id.tvStock);
        tvTipoArticulo = findViewById(R.id.tvTipoArticulo);
        tvLocalArticulo = findViewById(R.id.tvLocalArticulo);
        tvProveedorArticulo = findViewById(R.id.tvProveedorArticulo);
        Button btnMostrarDatosArticulo = findViewById(R.id.btnMostrarDatos);

        btnMostrarDatosArticulo.setOnClickListener(v -> mostrarDatosArticulo());
    }

    private void limpiarCampos() {
        tvNombreArticulo.setText("");
        tvDescripcion.setText("");
        tvPrecioUnitario.setText("");
        tvStock.setText("");
        tvTipoArticulo.setText("");
        tvLocalArticulo.setText("");
        tvProveedorArticulo.setText("");
    }

    private void mostrarDatosArticulo() {
        String urlArticulo = "https://pdmproyectouno.000webhostapp.com/articulo_obtener_por_id.php?id=" +
                editIdArticulo.getText().toString();
        new GetDataTask(result -> {
            if (result == null || !result.get("success").getAsBoolean()) {
                manejarError(result);
                return;
            }

            JsonObject articulo = result.getAsJsonObject("articulo");
            actualizarVistaArticulo(articulo);
            obtenerDatosAsociados(articulo.get("id_proveedor").getAsString(), articulo.get("id_tipo_articulo").getAsString(), articulo.get("id_articulo").getAsString());
        }).execute(urlArticulo);
    }

    private void manejarError(JsonObject result) {
        String message = result != null ? result.get("message").getAsString() : "Unknown error";
        Log.e(TAG, "Error: " + message);
        Toast.makeText(ConsultarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void actualizarVistaArticulo(JsonObject articulo) {
        tvNombreArticulo.setText(articulo.get("nombre_articulo").getAsString());
        tvPrecioUnitario.setText(articulo.get("precio_unitario_articulo").getAsString());
        tvStock.setText(articulo.get("stock_articulo").getAsString());
        tvDescripcion.setText(articulo.get("descripcion_articulo").getAsString());
    }

    private void obtenerDatosAsociados(String idProveedor, String idTipoArticulo, String idArticulo) {
        obtenerProveedor(idProveedor);
        obtenerTipoArticulo(idTipoArticulo);
        obtenerLocalArticulo(idArticulo);
    }

    private void obtenerProveedor(String idProveedor) {
        String urlProveedor = "https://pdmproyectouno.000webhostapp.com/proveedor_obtener_por_id.php?id=" + idProveedor;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject proveedor = result.getAsJsonObject("proveedor");
                tvProveedorArticulo.setText(proveedor.get("nombre_proveedor").getAsString());
            } else {
                Log.e(TAG, "Error fetching proveedor data.");
            }
        }).execute(urlProveedor);
    }

    private void obtenerTipoArticulo(String idTipoArticulo) {
        String urlTipoArticulo = "https://pdmproyectouno.000webhostapp.com/tipo_articulo_obtener_por_id.php?id=" + idTipoArticulo;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject tipoArticulo = result.getAsJsonObject("tipo_articulo");
                tvTipoArticulo.setText(tipoArticulo.get("nombre_tipo_articulo").getAsString());
            } else {
                Log.e(TAG, "Error fetching tipo_articulo data.");
            }
        }).execute(urlTipoArticulo);
    }

    private void obtenerLocalArticulo(String idArticulo) {
        String urlArticuloLocal = "https://pdmproyectouno.000webhostapp.com/articulo_sucursal_obtener_por_id_articulo.php?id_articulo=" + idArticulo;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject articuloLocal = result.getAsJsonObject("articulo_sucursal");
                String idLocal = articuloLocal.get("id_sucursal").getAsString();
                obtenerLocal(idLocal);
            } else {
                Log.e(TAG, "Error fetching articulo_sucursal data.");
            }
        }).execute(urlArticuloLocal);
    }

    private void obtenerLocal(String idLocal) {
        String urlLocal = "https://pdmproyectouno.000webhostapp.com/sucursal_obtener_por_id.php?id=" + idLocal;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject local = result.getAsJsonObject("sucursal");
                tvLocalArticulo.setText(local.get("nombre_sucursal").getAsString());
            } else {
                Log.e(TAG, "Error fetching local data.");
            }
        }).execute(urlLocal);
    }
}
