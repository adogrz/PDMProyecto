package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;

public class MedicamentosConsultarActivity extends AppCompatActivity {


    EditText editMedicamento;

   TextView editFechaExpedicion, editFechaExpiracion, editRecetaMedica, editArticulo,
            editFormaFarmaceutica, editViaAdministracion, editLaboratorio;

    ControlBaseDatos helper;
    Button btnConsultarM;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_consultar_activity);

        //Busqueda de los elementos de la interfaz
        editMedicamento = findViewById(R.id.idConMedicamento);
        editFechaExpedicion = findViewById(R.id.ExpedCon);
        editFechaExpiracion = findViewById(R.id.ExpiCon);
        editRecetaMedica = findViewById(R.id.RequiereCon);
        editArticulo = findViewById(R.id.medicamentoCon);
        editFormaFarmaceutica = findViewById(R.id.FormaFCon);
        editViaAdministracion = findViewById(R.id.ViaAdmC);
        editLaboratorio = findViewById(R.id.LabCon);
        btnConsultarM = findViewById(R.id.btConsultar);


        //Mostar datos del medicamento seleccionado
        btnConsultarM.setOnClickListener(v -> mostrarDatosM());
    }
    private void mostrarDatosM() {

        helper = ControlBaseDatos.obtenerInstancia(MedicamentosConsultarActivity.this);
        Medicamento medicamento = null;
        String tipoFormaFarmaceutica = null;
        String tipoViaAdministracion = null;
        String nombreLaboratorio = null;
        String nombreArticulo = null;

        try {
            medicamento= helper.getMedicamentosDAO().obtener(editMedicamento.getText().toString());
            nombreArticulo= helper.getArticuloDAO().obtener(medicamento.getIdArticulo()).getNombre();
            tipoFormaFarmaceutica= helper.getFormaFarmaceuticaDAO().obtener(medicamento.getIdFormaFarmaceutica()).getFormaFarmaceutica();
            tipoViaAdministracion= helper.getViaAdministracionDAO().obtener(medicamento.getIdViaAdministracion()).getViaAdministracion();
            nombreLaboratorio= helper.getLaboratorioDAO().obtener(medicamento.getIdLaboratorio()).getNombreLaboratorio();

            Toast.makeText(this, "Medicamento encontrado", Toast.LENGTH_SHORT).show();

        }catch (DAOException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if(medicamento!=null){
            editFechaExpedicion.setText(medicamento.getFechaExpedicion());
            editFechaExpiracion.setText(medicamento.getFechaExpiracion());
            editRecetaMedica.setText(medicamento.getRequiereRecetaMedica());
            editArticulo.setText(nombreArticulo);
            editFormaFarmaceutica.setText(tipoFormaFarmaceutica);
            editViaAdministracion.setText(tipoViaAdministracion);
            editLaboratorio.setText(nombreLaboratorio);
        }else{
            editFechaExpedicion.setText("");
            editFechaExpiracion.setText("");
            editRecetaMedica.setText("");
            editArticulo.setText("");
            editFormaFarmaceutica.setText("");
            editViaAdministracion.setText("");
            editLaboratorio.setText("");
        }
    }




}
