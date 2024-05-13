package sv.ues.fia.eisi.pdmproyectoetapa1;


import android.annotation.SuppressLint;

import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;

import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;

public class MedicamentosActualizarActivity extends AppCompatActivity {

    EditText fechaExpedicionS, fechaExperacionS;
    Spinner medicamentoid,articuloMedicamento,formaFarmaceutica,viaAdministracion,laboratorio;
    Button actualizarMedicamento,limpiarMedicamento;

    Switch recetaMedicaS;

    ControlBaseDatos helper;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_actualizar);

        //Busqueda de los elementos de la interfaz
        medicamentoid = findViewById(R.id.idMedAct);
        fechaExpedicionS = findViewById(R.id.editFechaExpedicionAct);
        fechaExperacionS = findViewById(R.id.editFechaExpiracionAct);
        recetaMedicaS = findViewById(R.id.editRecetaMedicaAct);
        articuloMedicamento = findViewById(R.id.editArticuloAct);
        formaFarmaceutica = findViewById(R.id.editFormaFarmaceuticaAct);
        viaAdministracion = findViewById(R.id.editViaAdministracionAct);
        laboratorio = findViewById(R.id.editLaboratorioAct);
        actualizarMedicamento = findViewById(R.id.actualizarM);
        limpiarMedicamento = findViewById(R.id.limpiarM);
        //Spinners
        SpinnerMedicamento();
        spinnerArticuloM();
        spinnerViadministracion();
        spinnerFormaFarmaceutica();
        spinnerLaboratorio();

        //Acción del botón actualizar
        actualizarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarMedicamento();
            }
        });
        //Acción del botón limpiar
        limpiarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCampos(v);
            }
        });

    }

    //Metodo para llenar el spinner de medicamento
    public void SpinnerMedicamento(){
        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
       MedicamentoDAO medicamentoDAO =db.getMedicamentosDAO();
       try {
              List<Medicamento> medicamentosList = medicamentoDAO.obtenerTodos();
              ArrayAdapter<Medicamento> adapterMedicamento = new ArrayAdapter<>(this,
                      androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, medicamentosList);

           medicamentoid.setAdapter(adapterMedicamento);

       }catch (DAOException e){
           Toast.makeText(MedicamentosActualizarActivity.this, "No se pudo obtener los medicamentos", Toast.LENGTH_SHORT).show();
       }
    }
    //Metodo para llenar el spinner de medicamento articulo
        public void spinnerArticuloM() {
            ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
            ArticuloDAO articuloDAO = db.getArticuloDAO();

            try {
                String idTipoArticulo = db.getTipoArticuloDAO().obtenerTodos().get(0).getId();
                // Obtener todos los artículos
                List<Articulo> articuloList = articuloDAO.obtenerTodos();

                // Filtrar solo los artículos de tipo "medicamento"
                List<Articulo> articulosMedicamento = new ArrayList<>();
                for (Articulo articulo : articuloList) {
                    if (articulo.getIdTipoArticulo().equals(idTipoArticulo)) {
                        articulosMedicamento.add(articulo);
                    }
                }
                // Crear el adaptador con la lista filtrada
                ArrayAdapter<Articulo> adapterArticulo = new ArrayAdapter<>(this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, articulosMedicamento);

                // Asignar el adaptador al spinner
                articuloMedicamento.setAdapter(adapterArticulo);

            } catch (DAOException e) {
                throw new RuntimeException(e);
            }
        }

    //Metodo para llenar el spinner de via de administracion
    public void spinnerViadministracion() {

        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
        ViaAdministracionDAO viaAdministracionDAO = db.getViaAdministracionDAO();

        try {
            List<ViaAdministracion> viaAdministracionList = viaAdministracionDAO.obtenerTodos();
            ArrayAdapter<ViaAdministracion> adapterViaAdministracion = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, viaAdministracionList);

            viaAdministracion.setAdapter(adapterViaAdministracion);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }
    //Metodo para llenar el spinner de forma farmaceutica
    public void spinnerFormaFarmaceutica() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
        FormaFarmaceuticaDAO formaFarmaceuticaDAO = db.getFormaFarmaceuticaDAO();

        try {
            List<FormaFarmaceutica> formaFarmaceuticaList = formaFarmaceuticaDAO.obtenerTodos();
            ArrayAdapter<FormaFarmaceutica> adapterFormaFarmaceutica = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, formaFarmaceuticaList);

            formaFarmaceutica.setAdapter(adapterFormaFarmaceutica);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo para llenar el spinner de laboratorio
    public void spinnerLaboratorio() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
        LaboratorioDAO laboratorioDAO = db.getLaboratorioDAO();

        try {
            List<Laboratorio> laboratorioList = laboratorioDAO.obtenerTodos();
            ArrayAdapter<Laboratorio> adapterLaboratorio = new ArrayAdapter<>(this,
                    androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, laboratorioList);

            laboratorio.setAdapter(adapterLaboratorio);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarMedicamento(){
        //Obteniendo la instancia de la base de datos
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosActualizarActivity.this);
        //Obteniendo la instancia del DAO
        MedicamentoDAO medicamentoDAO = db.getMedicamentosDAO();

        //Obteniendo la opcion seleccionada de los spinners
        Medicamento medicamentoSeleccionado = (Medicamento) medicamentoid.getSelectedItem();
        Articulo articuloSeleccionado = (Articulo) articuloMedicamento.getSelectedItem();
        FormaFarmaceutica formaFarmaceuticaSeleccionada = (FormaFarmaceutica) formaFarmaceutica.getSelectedItem();
        ViaAdministracion viaAdministracionSeleccionada = (ViaAdministracion) viaAdministracion.getSelectedItem();
        Laboratorio laboratorioSeleccionado = (Laboratorio) laboratorio.getSelectedItem();

        //Obteniendo los valores de los campos de texto
        String fechaExpedicion = fechaExpedicionS.getText().toString();
        String fechaExpiracion = fechaExperacionS.getText().toString();
        boolean recetaRequerida = recetaMedicaS.isChecked(); // Obteniendo el estado del Switch

        //Creando un nuevo objeto de tipo Medicamento
        Medicamento medicamento=new Medicamento();

        //Asignando los valores al objeto
        medicamento.setIdMedicamento(medicamentoSeleccionado.getIdMedicamento());
        medicamento.setFechaExpedicion(fechaExpedicion);
        medicamento.setFechaExpiracion(fechaExpiracion);
        medicamento.setRequiereRecetaMedica(recetaRequerida ? "Si" : "No"); // Asignando el valor de la receta según el estado del Switch
        medicamento.setIdArticulo(articuloSeleccionado.getIdArticulo());
        medicamento.setIdFormaFarmaceutica(formaFarmaceuticaSeleccionada.getIdFormaFarmaceutica());
        medicamento.setIdViaAdministracion(viaAdministracionSeleccionada.getIdViaAdministracion());
        medicamento.setIdLaboratorio(laboratorioSeleccionado.getIdLaboratorio());

        try{
            //Verificar si los campos estan vacios
            if(fechaExpedicion.isEmpty() || fechaExpiracion.isEmpty()) {
                Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            //Verificar si la fecha de expedición es menor a la fecha de expiración
            if (fechaExpedicion.compareTo(fechaExpiracion) > 0) {
                Toast.makeText(this, "La fecha de expedición no puede ser mayor a la fecha de expiración", Toast.LENGTH_SHORT).show();
                return;
            }

            //Actualizar el medicamento
            medicamentoDAO.modificar(medicamento);
            Toast.makeText(this, "Medicamento actualizado", Toast.LENGTH_SHORT).show();
        }catch (DAOException e){
            Toast.makeText(this, "Error al actualizar el medicamento", Toast.LENGTH_SHORT).show();
        }


    }
    public void limpiarCampos(View v) {
        fechaExpedicionS.setText("");
        fechaExperacionS.setText("");
        recetaMedicaS.setChecked(false);
    }


}








