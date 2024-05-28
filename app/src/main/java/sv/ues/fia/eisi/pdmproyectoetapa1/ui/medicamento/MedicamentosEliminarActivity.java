package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;

public class MedicamentosEliminarActivity extends AppCompatActivity {

    EditText eMedicamentos;
    Button eliminarM;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_eliminar);
        eMedicamentos = findViewById(R.id.et_id_medicamento);
        eliminarM = findViewById(R.id.btnElimM);

        eliminarM.setOnClickListener(v -> eliminarMedicamento());
    }

    public void eliminarMedicamento() {
        String idMedicamento = eMedicamentos.getText().toString().trim();

        // Verificar que el campo no esté vacío
        if (idMedicamento.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID de medicamento", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlEliminarMedicamento = "https://pdmproyectouno.000webhostapp.com/medicamento_eliminar.php?id=" + idMedicamento;
        new EliminarMedicamentoTask().execute(urlEliminarMedicamento);
    }

    private class EliminarMedicamentoTask extends AsyncTask<String, Void, JsonObject> {
        @Override
        protected JsonObject doInBackground(String... params) {
            String url = params[0];
            HttpHandler httpHandler = new HttpHandler();
            return httpHandler.makeServiceCall(url);
        }

        @Override
        protected void onPostExecute(JsonObject result) {
            super.onPostExecute(result);
            if (result != null) {
                boolean success = result.get("success").getAsBoolean();
                if (success) {
                    Toast.makeText(MedicamentosEliminarActivity.this, "Medicamento eliminado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    String message = result.get("message").getAsString();
                    Toast.makeText(MedicamentosEliminarActivity.this, "Error al eliminar medicamento: " + message, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MedicamentosEliminarActivity.this, "Error al obtener respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
