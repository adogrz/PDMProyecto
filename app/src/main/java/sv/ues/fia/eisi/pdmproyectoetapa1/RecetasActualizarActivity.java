package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.RecetaMedicaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleReceta;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.RecetaMedica;

public class RecetasActualizarActivity extends AppCompatActivity {

    ControlBaseDatos helper;
    EditText numeroReceta, fechaReceta,idMedico, nombreMedico, apellidoMedico,
            especialidadMedico, jvpmMedico,dosisReceta, idRecetaMedica, idMedicamento,
            periodicidadReceta, fechaInicioTratamiento, fechaFinTratamiento;
    Button btn_actualizarReceta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_actualizar);

        idRecetaMedica = (EditText) findViewById(R.id.editIdEditarReceta);
        numeroReceta = (EditText) findViewById(R.id.editNumeroReceta);
        fechaReceta = (EditText) findViewById(R.id.editFechaReceta);
        idMedico = (EditText) findViewById(R.id.editModIdMedico);
        nombreMedico = (EditText) findViewById(R.id.editNombreMedico);
        apellidoMedico = (EditText) findViewById(R.id.editApellidoMedico);
        especialidadMedico = (EditText) findViewById(R.id.editEspecialidadMedico);
        jvpmMedico = (EditText) findViewById(R.id.editJVPM);
        idMedicamento = (EditText) findViewById(R.id.editActualizarMedicamentoReceta);
        dosisReceta = (EditText) findViewById(R.id.editDosisReceta);
        periodicidadReceta = (EditText) findViewById(R.id.editPeriodicidadReceta);
        fechaInicioTratamiento = (EditText) findViewById(R.id.editFechaInicioTratamiento);
        fechaFinTratamiento = (EditText) findViewById(R.id.editFechaFinTratamiento);
        helper = ControlBaseDatos.obtenerInstancia(RecetasActualizarActivity.this);
        btn_actualizarReceta = (Button) findViewById(R.id.btn_actualizarReceta);
    }

    //Metodo para actualizar una receta
    public void actualizarReceta(View view){

        //Se obtienen los datos de los campos de texto y verifica que no exista ningun error

        try{
            ControlBaseDatos helper = ControlBaseDatos.obtenerInstancia(RecetasActualizarActivity.this);
            //obteniendo las instancias de los daos
            RecetaMedicaDAO recetaMedicaDAO = helper.getRecetaMedicaDAO();
            MedicoDAO medicoDAO = helper.getMedicoDAO();
            DetalleRecetaDAO detalleRecetaDAO = helper.getDetalleRecetaDAO();
            //obteniendo los datos de los campos de texto
            String idReceta = idRecetaMedica.getText().toString();
            String numero = numeroReceta.getText().toString();
            String fecha = fechaReceta.getText().toString();
            String idMedicoNew = idMedico.getText().toString();
            String nombre = nombreMedico.getText().toString();
            String apellido = apellidoMedico.getText().toString();
            String especialidad = especialidadMedico.getText().toString();
            String jvpm = jvpmMedico.getText().toString();
            String idMedicamentoNew = idMedicamento.getText().toString();
            String dosis = dosisReceta.getText().toString();
            String periodicidad = periodicidadReceta.getText().toString();
            String fechaInicio = fechaInicioTratamiento.getText().toString();
            String fechaFin = fechaFinTratamiento.getText().toString();

            //Creando las instancias de los objetos
            RecetaMedica recetaMedica = new RecetaMedica();
            Medico medico = new Medico();
            DetalleReceta detalleReceta = new DetalleReceta();

            //asignando valores al medico
            medico.setIdMedico(idMedicoNew);
            medico.setNombreMedico(nombre);
            medico.setApellidoMedico(apellido);
            medico.setEspecialidad(especialidad);
            medico.setJvpm(jvpm);

            //asignando valores a la receta
            recetaMedica.setIdReceta(idReceta);
            recetaMedica.setNumeroReceta(numero);
            recetaMedica.setFechaReceta(fecha);
            recetaMedica.setIdMedico(idMedicoNew);

            //asignando valores al detalle receta
            detalleReceta.setIdDetalleReceta(idReceta);
            detalleReceta.setDosis(dosis);
            detalleReceta.setPeriodicidad(periodicidad);
            detalleReceta.setFechaInicioTratamiento(fechaInicio);
            detalleReceta.setFechaFinTratamiento(fechaFin);
            detalleReceta.setIdRecetaMedica(idReceta);
            detalleReceta.setIdMedicamento(idMedicamentoNew);

            if(idReceta.isEmpty() || numero.isEmpty() || fecha.isEmpty() || idMedicoNew.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || especialidad.isEmpty() || jvpm.isEmpty() || idMedicamentoNew.isEmpty() || dosis.isEmpty() || periodicidad.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()){
                Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            //Insertando en la base de datos
            medicoDAO.modificar(medico);
            recetaMedicaDAO.modificar(recetaMedica);
            detalleRecetaDAO.modificar(detalleReceta);
        }catch (DAOException e){
            e.printStackTrace();
        }
    }
}
