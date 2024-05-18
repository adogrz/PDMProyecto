package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;

public class EliminarArticuloActivity extends AppCompatActivity {
    EditText editIdArticulo;
    Button buttonEliminarArticulo;
    ControlBaseDatos cBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_articulo);
        editIdArticulo = findViewById(R.id.etIdArticuloEliminar);
        buttonEliminarArticulo = findViewById(R.id.btnEliminarArticulo);

        cBD = ControlBaseDatos.obtenerInstancia(this);

        buttonEliminarArticulo.setOnClickListener(v -> eliminarArticulo());
    }

    public void eliminarArticulo() {
        String idArticulo = editIdArticulo.getText().toString();
        if (idArticulo.isEmpty()) {
            editIdArticulo.setError("Campo obligatorio");
        } else {
            Articulo articuloEliminar;
            try {
                articuloEliminar = cBD.getArticuloDAO().obtener(idArticulo);
                cBD.getArticuloDAO().eliminar(articuloEliminar);
                Toast.makeText(EliminarArticuloActivity.this, "Articulo eliminado exitosamente",
                        Toast.LENGTH_SHORT).show();
            } catch (DAOException e) {
                Toast.makeText(EliminarArticuloActivity.this, "Error: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}