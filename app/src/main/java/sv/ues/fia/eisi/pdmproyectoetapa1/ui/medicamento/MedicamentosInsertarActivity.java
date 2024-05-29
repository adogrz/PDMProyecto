package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.ViaAdministracion;

public class MedicamentosInsertarActivity extends AppCompatActivity {
    private static final String TAG = MedicamentosInsertarActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private EditText medicamentoid, fechaExpedicionS, fechaExperacionS;
    private Spinner articuloMedicamento, formaFarmaceutica, viaAdministracion, laboratorio;
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
        Button guardarMedicamento = findViewById(R.id.insertarM);

        requestQueue = Volley.newRequestQueue(this);

        //Llenado de los spinner
        spinnerArticuloM();
        spinnerViadministracion();
        spinnerFormaFarmaceutica();
        spinnerLaboratorio();

        //Guardar medicamento
        guardarMedicamento.setOnClickListener(v -> insertarMedicamento());
    }

    //Metodo para llenar el spinner de medicamento articulo
    private void spinnerArticuloM() {
        String url = "https://pdmproyectouno.000webhostapp.com/articulo_medicamento_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosInsertarActivity.this, "No se pudo obtener " +
                                    "los articulos", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Articulo>>() {
                        }.getType();
                        List<Articulo> articulos = gson.fromJson(
                                response.getJSONArray("articulo").toString(),
                                listType
                        );
                        ArrayAdapter<Articulo> articuloAdapter = new ArrayAdapter<>(
                                MedicamentosInsertarActivity.this,
                                android.R.layout.simple_spinner_item, articulos
                        );
                        articuloAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        articuloMedicamento.setAdapter(articuloAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosInsertarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MedicamentosInsertarActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    //Metodo para llenar el spinner de via de administracion
    private void spinnerViadministracion() {
        String url = "https://pdmproyectouno.000webhostapp.com/via_administracion_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosInsertarActivity.this, "Error al obtener las vias de administracion",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<ViaAdministracion>>() {
                        }.getType();
                        List<ViaAdministracion> viasAdministracion = gson.fromJson(
                                response.getJSONArray("via_administracion").toString(), listType
                        );
                        ArrayAdapter<ViaAdministracion> adapterViaAdministracion = new ArrayAdapter<>(
                                MedicamentosInsertarActivity.this,
                                android.R.layout.simple_spinner_item, viasAdministracion
                        );
                        adapterViaAdministracion.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        viaAdministracion.setAdapter(adapterViaAdministracion);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosInsertarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            error.printStackTrace();
            Toast.makeText(MedicamentosInsertarActivity.this, "Request error",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
        requestQueue.add(jsonObjectRequest);
    }

    //Metodo para llenar el spinner de forma farmaceutica
    public void spinnerFormaFarmaceutica() {
        String url = "https://pdmproyectouno.000webhostapp.com/forma_farmaceutica_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosInsertarActivity.this, "Error al obtener las formas farmaceuticas",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<FormaFarmaceutica>>() {
                        }.getType();
                        List<FormaFarmaceutica> formasFarmaceuticas = gson.fromJson(
                                response.getJSONArray("forma_farmaceutica").toString(), listType
                        );
                        ArrayAdapter<FormaFarmaceutica> adapterFormaFarmaceutica = new ArrayAdapter<>(
                                MedicamentosInsertarActivity.this,
                                android.R.layout.simple_spinner_item, formasFarmaceuticas);
                        adapterFormaFarmaceutica.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        formaFarmaceutica.setAdapter(adapterFormaFarmaceutica);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosInsertarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MedicamentosInsertarActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
        requestQueue.add(jsonObjectRequest);
    }

    //Metodo para llenar el spinner de laboratorio
    public void spinnerLaboratorio() {
        String url = "https://pdmproyectouno.000webhostapp.com/laboratorio_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosInsertarActivity.this, "Error al obtener los laboratorios",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Laboratorio>>() {
                        }.getType();
                        List<Laboratorio> laboratorios = gson.fromJson(
                                response.getJSONArray("laboratorio").toString(), listType
                        );
                        ArrayAdapter<Laboratorio> adapterLaboratorio = new ArrayAdapter<>(
                                MedicamentosInsertarActivity.this,
                                android.R.layout.simple_spinner_item, laboratorios);
                        adapterLaboratorio.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        laboratorio.setAdapter(adapterLaboratorio);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosInsertarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            error.printStackTrace();
            Toast.makeText(MedicamentosInsertarActivity.this, "Request error",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
        requestQueue.add(jsonObjectRequest);

    }

    // Método para insertar un medicamento
    public void insertarMedicamento() {

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
    }

    /**
     * AsyncTask para realizar la petición HTTP en un hilo secundario
     */
    private class InsertDataTask extends AsyncTask<String, Void, JsonObject> {
        @Override
        protected JsonObject doInBackground(String... urls) {
            JsonObject response = new HttpHandler().makeServiceCall(urls[0]);

            if (response != null) {
                Log.e(TAG, "Response from URL: " + response);
            } else {
                Log.e(TAG, "Couldn't get response from server.");
            }

            return response;
        }

        @Override
        protected void onPostExecute(JsonObject result) {
            super.onPostExecute(result);
            if (result == null || !result.get("success").getAsBoolean()) {
                String message = result != null ? result.get("message").getAsString() : "Unknown error";
                Log.e(TAG, "Error: " + message);
                Toast.makeText(MedicamentosInsertarActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            assert result != null;
            if (result.has("message")) {
                String message = result.get("message").getAsString();
                Log.e(TAG, "Message: " + message);
                Toast.makeText(MedicamentosInsertarActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}








