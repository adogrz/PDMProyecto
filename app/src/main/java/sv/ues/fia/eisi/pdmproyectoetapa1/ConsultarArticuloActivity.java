package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;

public class ConsultarArticuloActivity extends AppCompatActivity {
    EditText editIdArticulo;
    TextView tvNombreArticulo, tvDescripcion, tvPrecioUnitario, tvStock, tvTipoArticulo,
            tvLocalArticulo, tvProveedorArticulo;
    Button btnMostrarDatosArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_articulo);
        editIdArticulo = findViewById(R.id.etNombreArticulo);
        tvNombreArticulo = findViewById(R.id.tvNombreArticulo);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvPrecioUnitario = findViewById(R.id.tvPrecioUnitario);
        tvStock = findViewById(R.id.tvStock);
        tvTipoArticulo = findViewById(R.id.tvTipoArticulo);
        tvLocalArticulo = findViewById(R.id.tvLocalArticulo);
        tvProveedorArticulo = findViewById(R.id.tvProveedorArticulo);
        btnMostrarDatosArticulo = findViewById(R.id.btnMostrarDatos);

        btnMostrarDatosArticulo.setOnClickListener(v -> mostrarDatosArticulo());
    }

    private void mostrarDatosArticulo() {
        ControlBaseDatos cBD = ControlBaseDatos.obtenerInstancia(ConsultarArticuloActivity.this);
        Articulo articulo = null;
        String nombreTipoArticulo = null;
        String nombreProveedor = null;
        String nombreLocal = null;

        try {
            articulo = cBD.getArticuloDAO().obtener(editIdArticulo.getText().toString());
            nombreTipoArticulo = cBD.getTipoArticuloDAO().obtener(articulo.getIdTipoArticulo()).getNombre();
            nombreProveedor = cBD.getProveedorDAO().obtener(articulo.getIdProveedor()).getNombre();
            String idLocal = cBD.getLocalArticuloDAO().obtenerPorIdArticulo(articulo.getIdArticulo()).getIdLocal();
            nombreLocal = cBD.getLocalDAO().obtener(idLocal).getNombreLocal();
        } catch (DAOException e) {
            Toast.makeText(ConsultarArticuloActivity.this, "No se encontró el artículo", Toast.LENGTH_SHORT).show();
        }

        if (articulo != null) {
            tvNombreArticulo.setText(articulo.getNombre());
            tvDescripcion.setText(articulo.getDescripcion());
            tvPrecioUnitario.setText(String.valueOf(articulo.getPrecioUnitario()));
            tvStock.setText(String.valueOf(articulo.getStock()));
            tvTipoArticulo.setText(nombreTipoArticulo);
            tvLocalArticulo.setText(nombreLocal);
            tvProveedorArticulo.setText(nombreProveedor);
        } else {
            tvNombreArticulo.setText("");
            tvDescripcion.setText("");
            tvPrecioUnitario.setText("");
            tvStock.setText("");
            tvTipoArticulo.setText("");
            tvLocalArticulo.setText("");
            tvProveedorArticulo.setText("");
        }
    }
}