package sv.ues.fia.eisi.pdmproyectoetapa1.ui.recetamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.RecetaMedicaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.RecetaMedica;

public class RecetasEliminarActivity extends AppCompatActivity {

    Spinner spinnerIdReceta;
    Button botonEliminarReceta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_eliminar);
        spinnerIdReceta = (Spinner) findViewById(R.id.spinnerDelReceta);
        botonEliminarReceta = (Button) findViewById(R.id.btn_eliminarReceta);
        llenarSpinner();

        botonEliminarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarReceta();
            }
        });

    }
    private void llenarSpinner() {
        List<RecetaMedica> recetas=null;
        ControlBaseDatos helper = ControlBaseDatos.obtenerInstancia(RecetasEliminarActivity.this);
        try {
            recetas = helper.getRecetaMedicaDAO().obtenerTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<RecetaMedica> recetaAdapter = new ArrayAdapter<>(RecetasEliminarActivity.this,
                android.R.layout.simple_spinner_item, recetas);
        spinnerIdReceta.setAdapter(recetaAdapter);
    }

    private void eliminarReceta() {
        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(RecetasEliminarActivity.this);
        RecetaMedicaDAO recetaMedicaDAO = controlBaseDatos.getRecetaMedicaDAO();
        MedicoDAO medicoDAO = controlBaseDatos.getMedicoDAO();
        String idReceta = spinnerIdReceta.getSelectedItem().toString();

        try {
            RecetaMedica recetaMedica = recetaMedicaDAO.obtener(idReceta);
            String idMedico=recetaMedica.getIdMedico();
            Medico medico=controlBaseDatos.getMedicoDAO().obtener(idMedico);
            medicoDAO.eliminar(medico);
            Toast.makeText(RecetasEliminarActivity.this, "Receta eliminada",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(RecetasEliminarActivity.this, "Error al eliminar la receta",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}