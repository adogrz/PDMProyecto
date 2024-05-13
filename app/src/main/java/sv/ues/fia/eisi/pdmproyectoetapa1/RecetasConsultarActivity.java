package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleReceta;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.RecetaMedica;

public class RecetasConsultarActivity extends AppCompatActivity {
    ControlBaseDatos helper;
    Spinner spinnerRecetas;
    EditText idRecetaMedica, numeroReceta, fechaReceta, idMedico, nombreMedico, apellidoMedico,
            especialidadMedico, jvpmMedico,idDetalleReceta,consultaMedicamentoReceta,dosisReceta,
            periodicidadReceta, fechaInicioTratamiento, fechaFinTratamiento;
    Button btnConsultarReceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_consultar);
        helper = ControlBaseDatos.obtenerInstancia(RecetasConsultarActivity.this);
        spinnerRecetas = (Spinner) findViewById(R.id.spinnerConsultarRecetas);
        idRecetaMedica = (EditText) findViewById(R.id.editIdRecetaMedica);
        numeroReceta = (EditText) findViewById(R.id.editNumeroReceta);
        fechaReceta = (EditText) findViewById(R.id.editFechaReceta);
        idMedico = (EditText) findViewById(R.id.editIdMedico);
        nombreMedico = (EditText) findViewById(R.id.editNombreMedico);
        apellidoMedico = (EditText) findViewById(R.id.editApellidoMedico);
        especialidadMedico = (EditText) findViewById(R.id.editEspecialidadMedico);
        jvpmMedico = (EditText) findViewById(R.id.editJVPM);
        idDetalleReceta = (EditText) findViewById(R.id.editIdDetalleReceta);
        consultaMedicamentoReceta = (EditText) findViewById(R.id.readMedicamentoReceta);
        dosisReceta = (EditText) findViewById(R.id.editDosisReceta);
        periodicidadReceta = (EditText) findViewById(R.id.editPeriodicidadReceta);
        fechaInicioTratamiento = (EditText) findViewById(R.id.editFechaInicioTratamiento);
        fechaFinTratamiento = (EditText) findViewById(R.id.editFechaFinTratamiento);
        btnConsultarReceta = (Button) findViewById(R.id.btnConsultarRecetas);

        btnConsultarReceta.setOnClickListener(v -> mostrarDatosReceta());

        llenarSpinner();
    }
    private void llenarSpinner() {
        List<RecetaMedica> recetas = null;
        try{
            recetas = helper.getRecetaMedicaDAO().obtenerTodos();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        ArrayAdapter<RecetaMedica> recetaAdapter = new ArrayAdapter<>(RecetasConsultarActivity.this,
                android.R.layout.simple_spinner_item, recetas);
        spinnerRecetas.setAdapter(recetaAdapter);
    }

    private void mostrarDatosReceta() {
        helper = ControlBaseDatos.obtenerInstancia(RecetasConsultarActivity.this);
        RecetaMedica receta = null;
        try {
            receta = helper.getRecetaMedicaDAO().obtener(spinnerRecetas.getSelectedItem().toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        if(receta != null){
            MedicoDAO medicoDAO = helper.getMedicoDAO();
            DetalleRecetaDAO detalleRecetaDAO = helper.getDetalleRecetaDAO();
            Medico medico = null;
            DetalleReceta detalleReceta = null;
            try {
                medico = medicoDAO.obtener(receta.getIdMedico());
                detalleReceta = detalleRecetaDAO.obtenerDetallePorIdReceta(receta.getIdReceta());
            } catch (DAOException e) {
                throw new RuntimeException(e);
            }

            if(medico!=null && detalleReceta!=null){
                idRecetaMedica.setText(receta.getIdReceta());
                numeroReceta.setText(receta.getNumeroReceta());
                fechaReceta.setText(receta.getFechaReceta());
                idMedico.setText(medico.getIdMedico());
                nombreMedico.setText(medico.getNombreMedico());
                apellidoMedico.setText(medico.getApellidoMedico());
                especialidadMedico.setText(medico.getEspecialidad());
                jvpmMedico.setText(medico.getJvpm());
                idDetalleReceta.setText(detalleReceta.getIdDetalleReceta());
                consultaMedicamentoReceta.setText(detalleReceta.getIdMedicamento());
                dosisReceta.setText(detalleReceta.getDosis());
                periodicidadReceta.setText(detalleReceta.getPeriodicidad());
                fechaInicioTratamiento.setText(detalleReceta.getFechaInicioTratamiento());
                fechaFinTratamiento.setText(detalleReceta.getFechaFinTratamiento());

            }else{
                idRecetaMedica.setText("");
                numeroReceta.setText("");
                fechaReceta.setText("");
                idMedico.setText("");
                nombreMedico.setText("");
                apellidoMedico.setText("");
                especialidadMedico.setText("");
                jvpmMedico.setText("");
                idDetalleReceta.setText("");
                consultaMedicamentoReceta.setText("");
                dosisReceta.setText("");
                periodicidadReceta.setText("");
                fechaInicioTratamiento.setText("");
                fechaFinTratamiento.setText("");
            }
        }
    }
}