package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.os.Bundle;
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
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;

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

        textView = findViewById(R.id.textViewPrueba);

        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(MainActivity.this);

        TipoArticuloDAO tipoArticuloDAO = controlBaseDatos.getTipoArticuloDAO();

        List<TipoArticulo> tipoArticulos;

        try {
            tipoArticulos = tipoArticuloDAO.obtenerTodos();
            String idPrimero = tipoArticulos.get(7).getId();

            TipoArticulo tipoArticulo = tipoArticuloDAO.obtener(idPrimero);
            textView.setText(tipoArticulo.getNombre());
        } catch (DAOException | IndexOutOfBoundsException e) {
            Toast.makeText(this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}