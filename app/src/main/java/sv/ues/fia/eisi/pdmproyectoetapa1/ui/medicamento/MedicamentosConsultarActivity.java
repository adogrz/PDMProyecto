package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.GetDataTask;

public class MedicamentosConsultarActivity extends AppCompatActivity {

    private static final String TAG = MedicamentosConsultarActivity.class.getSimpleName();

    EditText editMedicamento;

   TextView editFechaExpedicion, editFechaExpiracion, editRecetaMedica, editArticulo,
            editFormaFarmaceutica, editViaAdministracion, editLaboratorio;

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
String urlMedicamento = "https://pdmproyectouno.000webhostapp.com/medicamento_obtener_por_id.php?id=" +
                editMedicamento.getText().toString();
        new GetDataTask(result -> {
            if (result == null || !result.get("success").getAsBoolean()) {
                manejarError(result);
                return;
            }

            JsonObject medicamento = result.getAsJsonObject("medicamento");
            actualizarVistaMedicamento(medicamento);
            obtenerDatosAsociados(medicamento.get("id_articulo").getAsString(), medicamento.get("id_forma_farmaceutica").getAsString(),
                    medicamento.get("id_via_administracion").getAsString(), medicamento.get("id_laboratorio").getAsString());
        }).execute(urlMedicamento);
    }

    private void limpiarCampos() {
        editMedicamento.setText("");
        editFechaExpedicion.setText("");
        editFechaExpiracion.setText("");
        editRecetaMedica.setText("");
        editArticulo.setText("");
        editFormaFarmaceutica.setText("");
        editViaAdministracion.setText("");
        editLaboratorio.setText("");
    }
    private void manejarError(JsonObject result) {
        String message = result != null ? result.get("message").getAsString() : "Unknown error";
        Log.e(TAG, "Error: " + message);
        Toast.makeText(MedicamentosConsultarActivity.this, message, Toast.LENGTH_SHORT).show();
        limpiarCampos();
    }

    private void actualizarVistaMedicamento(JsonObject medicamento) {
        editFechaExpedicion.setText(medicamento.get("fecha_expedicion_medicamento").getAsString());
        editFechaExpiracion.setText(medicamento.get("fecha_expiracion_medicamento").getAsString());
        editRecetaMedica.setText(medicamento.get("requiere_receta_medica").getAsString());
    }

    private void obtenerDatosAsociados(String idArticulo, String idFormaFarmaceutica, String idViaAdministracion, String idLaboratorio) {
        obtenerArticulo(idArticulo);
        obtenerFormaFarmaceutica(idFormaFarmaceutica);
        obtenerViaAdministracion(idViaAdministracion);
        obtenerLaboratorio(idLaboratorio);
    }

    private void obtenerArticulo(String idArticulo) {
        String urlArticulo = "https://pdmproyectouno.000webhostapp.com/articulo_obtener_por_id.php?id=" + idArticulo;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject articulo = result.getAsJsonObject("articulo");
                editArticulo.setText(articulo.get("nombre_articulo").getAsString());
            } else {
                Log.e(TAG, "Error encontrando los datos del articulo.");
            }
        }).execute(urlArticulo);
    }

    private void obtenerFormaFarmaceutica(String idFormaFarmaceutica) {
        String urlFormaFarmaceutica = "https://pdmproyectouno.000webhostapp.com/forma_farmaceutica_obtener_por_id.php?id=" + idFormaFarmaceutica;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject formaFarmaceutica = result.getAsJsonObject("forma_farmaceutica");
                editFormaFarmaceutica.setText(formaFarmaceutica.get("tipo_forma_farmaceutica").getAsString());
            } else {
                Log.e(TAG, "Error encontrando los datos de la forma farmaceutica.");
            }
        }).execute(urlFormaFarmaceutica);
    }

    private void obtenerViaAdministracion(String idViaAdministracion) {
        String urlViaAdministracion = "https://pdmproyectouno.000webhostapp.com/via_administracion_obtener_por_id.php?id=" + idViaAdministracion;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject viaAdministracion = result.getAsJsonObject("via_administracion");
                editViaAdministracion.setText(viaAdministracion.get("tipo_via_administracion").getAsString());
            } else {
                Log.e(TAG, "Error encontrando los datos de la via de administracion.");
            }
        }).execute(urlViaAdministracion);
    }

    private void obtenerLaboratorio(String idLaboratorio) {
        String urlLaboratorio = "https://pdmproyectouno.000webhostapp.com/laboratorio_obtener_por_id.php?id=" + idLaboratorio;
        new GetDataTask(result -> {
            if (result != null && result.get("success").getAsBoolean()) {
                JsonObject laboratorio = result.getAsJsonObject("laboratorio");
                editLaboratorio.setText(laboratorio.get("nombre_laboratorio").getAsString());
            } else {
                Log.e(TAG, "Error encontrando los datos del laboratorio.");
            }
        }).execute(urlLaboratorio);
    }

}
