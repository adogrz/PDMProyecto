package sv.ues.fia.eisi.pdmproyectoetapa1.ui.recetamedica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.controls.Control;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;

import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleReceta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.RecetaMedica;

public class RecetasInsertarActivity extends AppCompatActivity {

    ControlBaseDatos helper;
    //EditText para los campos de activity_recetas_insertar.xml
    EditText editNumeroReceta, editIdRecetaMedica, editIdMedico;
    EditText editFechaReceta, editIdDetalleReceta;
    EditText editNombreMedico;
    EditText editApellidoMedico;
    EditText editEspecialidadMedico;
    EditText editJVPM;
    Spinner spinnerMedicamentos;
    EditText editDosisReceta;
    EditText editPeriodicidadReceta;
    EditText editFechaInicioTratamiento;
    EditText editFechaFinTratamiento;
    Button btnInsertarReceta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_insertar);
        helper = ControlBaseDatos.obtenerInstancia(RecetasInsertarActivity.this);
        editIdRecetaMedica = (EditText) findViewById(R.id.editIdRecetaMedica);
        editNumeroReceta = (EditText) findViewById(R.id.editNumeroReceta);
        editFechaReceta = (EditText) findViewById(R.id.editFechaReceta);
        editIdMedico = (EditText) findViewById(R.id.editIdMedico);
        editNombreMedico = (EditText) findViewById(R.id.editNombreMedico);
        editApellidoMedico = (EditText) findViewById(R.id.editApellidoMedico);
        editEspecialidadMedico = (EditText) findViewById(R.id.editEspecialidadMedico);
        editJVPM = (EditText) findViewById(R.id.editJVPM);
        spinnerMedicamentos = (Spinner) findViewById(R.id.spinnerMedicamentos);
        editIdDetalleReceta = (EditText) findViewById(R.id.editIdDetalleReceta);
        editDosisReceta = (EditText) findViewById(R.id.editDosisReceta);
        editPeriodicidadReceta = (EditText) findViewById(R.id.editPeriodicidadReceta);
        editFechaInicioTratamiento = (EditText) findViewById(R.id.editFechaInicioTratamiento);
        editFechaFinTratamiento = (EditText) findViewById(R.id.editFechaFinTratamiento);
        btnInsertarReceta = (Button) findViewById(R.id.btn_insertarReceta);

        llenarSpinner();

        btnInsertarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarReceta();
            }
        });
    }
    private void llenarSpinner() {
        List<Medicamento> medicamentos=null;
        try {
            medicamentos = helper.getMedicamentosDAO().obtenerTodos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayAdapter<Medicamento> medicamentoAdapter = new ArrayAdapter<>(RecetasInsertarActivity.this,
                android.R.layout.simple_spinner_item, medicamentos);
        spinnerMedicamentos.setAdapter(medicamentoAdapter);
    }

    private void insertarReceta(){
        String idRecetaMedica = editIdRecetaMedica.getText().toString();
        String numeroReceta = editNumeroReceta.getText().toString();
        String fechaReceta = editFechaReceta.getText().toString();
        String idMedico = editIdMedico.getText().toString();
        String nombreMedico = editNombreMedico.getText().toString();
        String apellidoMedico = editApellidoMedico.getText().toString();
        String especialidadMedico = editEspecialidadMedico.getText().toString();
        String jvpm = editJVPM.getText().toString();
        Medicamento medicamento = (Medicamento) spinnerMedicamentos.getSelectedItem();
        String idMedicamento = medicamento.getIdMedicamento();
        String idDetalleReceta = editIdDetalleReceta.getText().toString();
        String dosisReceta = editDosisReceta.getText().toString();
        String periodicidadReceta = editPeriodicidadReceta.getText().toString();
        String fechaInicioTratamiento = editFechaInicioTratamiento.getText().toString();
        String fechaFinTratamiento = editFechaFinTratamiento.getText().toString();

        //validar que los campos no esten vacios
        if(idRecetaMedica.isEmpty() || numeroReceta.isEmpty() || fechaReceta.isEmpty() ||
                nombreMedico.isEmpty() || apellidoMedico.isEmpty() || especialidadMedico.isEmpty()
                || jvpm.isEmpty() || dosisReceta.isEmpty() || periodicidadReceta.isEmpty() ||
                fechaInicioTratamiento.isEmpty() || fechaFinTratamiento.isEmpty() ){
            //mostrar mensaje de error
            Toast.makeText(RecetasInsertarActivity.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //crear objeto Medico
        Medico medico = new Medico(idMedico, nombreMedico, apellidoMedico, especialidadMedico, jvpm);
        //insertar medico en la base de datos
        try {
            helper.getMedicoDAO().insertar(medico);
            Toast.makeText(RecetasInsertarActivity.this, "Medico insertado correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //crear objeto RecetaMedica
        RecetaMedica recetaMedica = new RecetaMedica(idRecetaMedica, numeroReceta, fechaReceta, idMedico);
        //insertar recetaMedica en la base de datos
        try {
            helper.getRecetaMedicaDAO().insertar(recetaMedica);
            Toast.makeText(RecetasInsertarActivity.this, "Receta medica insertada correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Crear objeto DetalleReceta
        DetalleReceta detalleReceta = new DetalleReceta(idDetalleReceta, dosisReceta,
                periodicidadReceta, fechaInicioTratamiento, fechaFinTratamiento, idRecetaMedica, idMedicamento);
        //insertar detalleReceta en la base de datos
        try {
            helper.getDetalleRecetaDAO().insertar(detalleReceta);
            Toast.makeText(RecetasInsertarActivity.this, "Detalle receta insertado correctamente",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}