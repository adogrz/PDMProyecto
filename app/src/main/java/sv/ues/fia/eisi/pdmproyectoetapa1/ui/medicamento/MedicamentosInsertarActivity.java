package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.ViaAdministracion;



public class MedicamentosInsertarActivity extends AppCompatActivity {

    EditText medicamentoid,fechaExpedicionS, fechaExperacionS;
    Spinner articuloMedicamento,formaFarmaceutica,viaAdministracion,laboratorio;
    Button guardarMedicamento;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch recetaMedicaS;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_insertar);

        //Busqueda de los elementos de la interfaz
        medicamentoid = findViewById(R.id.editMedicamento);
        fechaExpedicionS = findViewById(R.id.editFechaExpedicion);
        fechaExperacionS = findViewById(R.id.editFechaExpiracion);
        recetaMedicaS = findViewById(R.id.editRecetaMedica);
        articuloMedicamento = findViewById(R.id.editArticulo);
        formaFarmaceutica = findViewById(R.id.editFormaFarmaceutica);
        viaAdministracion = findViewById(R.id.editViaAdministracion);
        laboratorio = findViewById(R.id.editLaboratorio);
        guardarMedicamento = findViewById(R.id.insertarM);

        //Llenado de los spinner
        spinnerArticuloM();
        spinnerViadministracion();
        spinnerFormaFarmaceutica();
        spinnerLaboratorio();

        //Guardar medicamento
        guardarMedicamento.setOnClickListener(v -> insertarMedicamento());


    }

    //Metodo para llenar el spinner de medicamento articulo
    public void spinnerArticuloM() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosInsertarActivity.this);
        ArticuloDAO articuloDAO = db.getArticuloDAO();
        List<Articulo> articuloList;
        try {
            String idTipoArticulo =db.getTipoArticuloDAO().obtenerTodos().get(0).getId();
          
            // Obtener todos los artículos
             articuloList = articuloDAO.obtenerTodos();
          
            // Filtrar solo los artículos de tipo "medicamento"
            List<Articulo> articulosMedicamento = new ArrayList<>();
            for (Articulo articulo : articuloList) {
                if (articulo.getIdTipoArticulo().equals(idTipoArticulo)) {
                    articulosMedicamento.add(articulo);
                }
            }
          
            // Verificar si la lista de artículos está vacía
            if (articulosMedicamento.isEmpty()) {
                Toast.makeText(this, "No hay artículos de tipo medicamento registrados", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
          
            // Crear el adaptador con la lista filtrada
            ArrayAdapter<Articulo> adapterArticulo = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, articulosMedicamento);

            // Asignar el adaptador al spinner
            articuloMedicamento.setAdapter(adapterArticulo);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para llenar el spinner de via de administracion
    public void spinnerViadministracion() {

        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosInsertarActivity.this);
        ViaAdministracionDAO viaAdministracionDAO = db.getViaAdministracionDAO();

        try {
            List<ViaAdministracion> viaAdministracionList = viaAdministracionDAO.obtenerTodos();
            ArrayAdapter<ViaAdministracion> adapterViaAdministracion = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, viaAdministracionList);

            viaAdministracion.setAdapter(adapterViaAdministracion);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo para llenar el spinner de forma farmaceutica
    public void spinnerFormaFarmaceutica() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosInsertarActivity.this);
        FormaFarmaceuticaDAO formaFarmaceuticaDAO = db.getFormaFarmaceuticaDAO();

        try {
            List<FormaFarmaceutica> formaFarmaceuticaList = formaFarmaceuticaDAO.obtenerTodos();
            ArrayAdapter<FormaFarmaceutica> adapterFormaFarmaceutica = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, formaFarmaceuticaList);

            formaFarmaceutica.setAdapter(adapterFormaFarmaceutica);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    //Metodo para llenar el spinner de laboratorio
    public void spinnerLaboratorio() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosInsertarActivity.this);
        LaboratorioDAO laboratorioDAO = db.getLaboratorioDAO();

        try {
            List<Laboratorio> laboratorioList = laboratorioDAO.obtenerTodos();
            ArrayAdapter<Laboratorio> adapterLaboratorio = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, laboratorioList);

            laboratorio.setAdapter(adapterLaboratorio);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    // Método para insertar un medicamento
    public void insertarMedicamento() {

        //Obteniendo la instancia de la base de datos
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosInsertarActivity.this);
        //Obteniendo la instancia del DAO
        MedicamentoDAO medicamentoDAO = db.getMedicamentosDAO();

        //Obteniendo la opcion seleccionada de los spinners
        Articulo articuloSeleccionado = (Articulo) articuloMedicamento.getSelectedItem();
        FormaFarmaceutica formaFarmaceuticaSeleccionada = (FormaFarmaceutica) formaFarmaceutica.getSelectedItem();
        ViaAdministracion viaAdministracionSeleccionada = (ViaAdministracion) viaAdministracion.getSelectedItem();
        Laboratorio laboratorioSeleccionado = (Laboratorio) laboratorio.getSelectedItem();

        //Obteniendo los valores de los campos de texto
        String medicamentoId = medicamentoid.getText().toString();
        String fechaExpedicion = fechaExpedicionS.getText().toString();
        String fechaExpiracion = fechaExperacionS.getText().toString();
        boolean recetaRequerida = recetaMedicaS.isChecked(); // Obteniendo el estado del Switch

        //Creando las instancias de los objetos
        Medicamento medicamento = new Medicamento();

        //Verificar si los campos están vacíos
        if (medicamentoId.isEmpty() || fechaExpedicion.isEmpty() || fechaExpiracion.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //Verificar si la fecha de expedición es menor a la fecha de expiración
        if (fechaExpedicion.compareTo(fechaExpiracion) > 0) {
            Toast.makeText(this, "La fecha de expiracion no puede ser menor a la fecha de expedicion", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //Asignando los valores a los objetos
            medicamento.setIdMedicamento(medicamentoId);
            medicamento.setFechaExpedicion(fechaExpedicion);
            medicamento.setFechaExpiracion(fechaExpiracion);
            medicamento.setRequiereRecetaMedica(recetaRequerida ? "Si" : "No"); // Asignando el valor de la receta según el estado del Switch
            medicamento.setIdArticulo(articuloSeleccionado.getIdArticulo());
            medicamento.setIdFormaFarmaceutica(formaFarmaceuticaSeleccionada.getIdFormaFarmaceutica());
            medicamento.setIdViaAdministracion(viaAdministracionSeleccionada.getIdViaAdministracion());
            medicamento.setIdLaboratorio(laboratorioSeleccionado.getIdLaboratorio());
            //Insertando el medicamento
            medicamentoDAO.insertar(medicamento);
            Toast.makeText(this, "Medicamento insertado correctamente", Toast.LENGTH_SHORT).show();
            finish();

        } catch (DAOException e) {
            Toast.makeText(this, "Error al insertar el medicamento", Toast.LENGTH_SHORT).show();
        }
    }
}








