package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MedicamentosInsertatActivity extends AppCompatActivity {
    //ControlBDProyecto Proyecto;
    EditText editMedicamento;
    EditText editNombreArticulo;
    EditText editFechaExpedicion;
    EditText editFechaExperacion;
    EditText editFormaFarmaceutica;
    EditText editViaAdministracion;
    EditText editNombreLaboratorio;

    public  void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_insertar);
        editMedicamento = (EditText) findViewById(R.id.editMedicamento);
        editNombreArticulo = (EditText) findViewById(R.id.editNombreArticulo);
        editFechaExpedicion = (EditText) findViewById(R.id.editFechaExpedicion);
        editFechaExperacion = (EditText) findViewById(R.id.editFechaExperacion);
        editFormaFarmaceutica = (EditText) findViewById(R.id.editFormaFarmaceutica);
        editViaAdministracion = (EditText) findViewById(R.id.editViaAdministracion);
        editNombreLaboratorio = (EditText) findViewById(R.id.editNombreLaboratorio);
    }

   /* public void insertarMedicamento(View v){
        String medicamento = editMedicamento.getText().toString();
        String nombreArticulo = editNombreArticulo.getText().toString();
        String fechaExpedicion = editFechaExpedicion.getText().toString();
        String fechaExperacion = editFechaExperacion.getText().toString();
        String formaFarmaceutica = editFormaFarmaceutica.getText().toString();
        String viaAdministracion = editViaAdministracion.getText().toString();
        String nombreLaboratorio = editNombreLaboratorio.getText().toString();
        String regInsertados;

       Medicamentos medicamentos= new Medicamentos();
         medicamentos.setMedicamento(medicamento);
            medicamentos.setNombreArticulo(nombreArticulo);
            medicamentos.setFechaExpedicion(fechaExpedicion);
            medicamentos.setFechaExperacion(fechaExperacion);
            medicamentos.setFormaFarmaceutica(formaFarmaceutica);
            medicamentos.setViaAdministracion(viaAdministracion);
            medicamentos.setNombreLaboratorio(nombreLaboratorio);
            Proyecto.abrir();
            regInsertados = Proyecto.insertar(medicamentos);
            Proyecto.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }*/

    public void limpiarTexto(View v){
        editMedicamento.setText("");
        editNombreArticulo.setText("");
        editFechaExpedicion.setText("");
        editFechaExperacion.setText("");
        editFormaFarmaceutica.setText("");
        editViaAdministracion.setText("");
        editNombreLaboratorio.setText("");
    }
}