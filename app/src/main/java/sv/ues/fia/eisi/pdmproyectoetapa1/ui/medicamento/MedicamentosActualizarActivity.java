package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;

public class MedicamentosActualizarActivity extends AppCompatActivity {
    private static final String TAG = "MedicamentosActualizarActivity";
    private RequestQueue requestQueue;
    private EditText fechaExpedicion, fechaExpiracion;
    private Spinner medicamentoid, articuloMedicamento, formaFarmaceutica, viaAdministracion, laboratorio;
    Button actualizarMedicamento;
    SwitchMaterial requiereRecetaMedica;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_actualizar);

        medicamentoid = findViewById(R.id.spnn_id_medicamento);
        articuloMedicamento = findViewById(R.id.spnn_id_articulo_medicamento);
        viaAdministracion = findViewById(R.id.spnn_via_administracion);
        formaFarmaceutica = findViewById(R.id.spnn_forma_farmaceutica);
        laboratorio = findViewById(R.id.spnn_laboratorio);
        fechaExpedicion = findViewById(R.id.editFechaExpedicionAct);
        fechaExpiracion = findViewById(R.id.editFechaExpiracionAct);
        requiereRecetaMedica = findViewById(R.id.swh_requiere_receta_medica);
        actualizarMedicamento = findViewById(R.id.btnActualizarMedicamento);
        TextInputLayout fechaExpedicionLayout = findViewById(R.id.tilFechaExpedicionAct);
        TextInputLayout fechaExpiracionLayout = findViewById(R.id.tilFechaExpiracionAct);

        requestQueue = Volley.newRequestQueue(this);

        SpinnerMedicamento();
        spinnerArticuloM();
        spinnerViadministracion();
        spinnerFormaFarmaceutica();
        spinnerLaboratorio();

        fechaExpedicionLayout.setEndIconOnClickListener(v -> mostrarCalendario(fechaExpedicion));
        fechaExpiracionLayout.setEndIconOnClickListener(v -> mostrarCalendario(fechaExpiracion));

        // Acción del botón actualizar
        actualizarMedicamento.setOnClickListener(v -> actualizarMedicamento());
    }

    private void mostrarCalendario(EditText etFecha) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        new DatePickerDialog(MedicamentosActualizarActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    selectedMonth += 1;
                    String date = selectedYear + "-" + selectedMonth + "-" + selectedDay;
                    etFecha.setText(date);
                }, year, month, dayOfMonth).show();
    }

    //Metodo actualizar medicamento
    public void actualizarMedicamento() {
        //Obteniendo los valores de los campos de texto
        String fechaExpedicion = this.fechaExpedicion.getText().toString();
        String fechaExpiracion = this.fechaExpiracion.getText().toString();

        // Obteniendo el estado del Switch
        String tieneReceta = requiereRecetaMedica.isChecked() ? "1" : "0";

        //Obteniendo la opcion seleccionada de los spinners
        Medicamento medicamentoSeleccionado = (Medicamento) medicamentoid.getSelectedItem();
        Articulo articuloSeleccionado = (Articulo) articuloMedicamento.getSelectedItem();
        FormaFarmaceutica formaFarmaceuticaSeleccionada = (FormaFarmaceutica) formaFarmaceutica.getSelectedItem();
        ViaAdministracion viaAdministracionSeleccionada = (ViaAdministracion) viaAdministracion.getSelectedItem();
        Laboratorio laboratorioSeleccionado = (Laboratorio) laboratorio.getSelectedItem();

        //Verificar si los campos están vacíos
        if (fechaExpedicion.isEmpty() || fechaExpiracion.isEmpty()) {
            Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //Verificar si la fecha de expedición es menor a la fecha de expiración
        if (fechaExpedicion.compareTo(fechaExpiracion) > 0) {
            Toast.makeText(this, "La fecha de expiracion no puede ser menor a la fecha de expedicion", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlProv = "https://pdmproyectouno.000webhostapp.com/medicamento_modificar.php?id="
                + medicamentoSeleccionado + "&fecha_expedicion=" + fechaExpedicion + "&fecha_expiracion="
                + fechaExpiracion + "&requiere_receta_medica=" + tieneReceta + "&id_articulo="
                + articuloSeleccionado.getIdArticulo() + "&id_forma_farmaceutica="
                + formaFarmaceuticaSeleccionada.getIdFormaFarmaceutica()
                + "&id_via_administracion=" + viaAdministracionSeleccionada.getIdViaAdministracion()
                + "&id_laboratorio=" + laboratorioSeleccionado.getIdLaboratorio();
        new InsertDataTask().execute(urlProv);

        finish();
    }

    //Metodo para llenar el spinner de medicamento
    public void SpinnerMedicamento() {
        String url = "https://pdmproyectouno.000webhostapp.com/medicamento_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosActualizarActivity.this, "No se pudo obtener " +
                                    "los medicamentos", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Medicamento>>() {
                        }.getType();
                        List<Medicamento> medicamentos = gson.fromJson(
                                response.getJSONArray("medicamento").toString(),
                                listType
                        );
                        ArrayAdapter<Medicamento> medicamentoAdapter = new ArrayAdapter<>(
                                MedicamentosActualizarActivity.this,
                                android.R.layout.simple_spinner_item, medicamentos
                        );
                        medicamentoAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        medicamentoid.setAdapter(medicamentoAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosActualizarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MedicamentosActualizarActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
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
                            Toast.makeText(MedicamentosActualizarActivity.this, "No se pudo obtener " +
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
                                MedicamentosActualizarActivity.this,
                                android.R.layout.simple_spinner_item, articulos
                        );
                        articuloAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        articuloMedicamento.setAdapter(articuloAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosActualizarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MedicamentosActualizarActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    //Metodo para llenar el spinner de via de administracion
    public void spinnerViadministracion() {
        String url = "https://pdmproyectouno.000webhostapp.com/via_administracion_obtener_todos.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(MedicamentosActualizarActivity.this, "Error al obtener las vias de administracion",
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
                                MedicamentosActualizarActivity.this,
                                android.R.layout.simple_spinner_item, viasAdministracion
                        );
                        adapterViaAdministracion.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        viaAdministracion.setAdapter(adapterViaAdministracion);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosActualizarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            error.printStackTrace();
            Toast.makeText(MedicamentosActualizarActivity.this, "Request error",
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
                            Toast.makeText(MedicamentosActualizarActivity.this, "Error al obtener las formas farmaceuticas",
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
                                MedicamentosActualizarActivity.this,
                                android.R.layout.simple_spinner_item, formasFarmaceuticas);
                        adapterFormaFarmaceutica.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        formaFarmaceutica.setAdapter(adapterFormaFarmaceutica);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosActualizarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(MedicamentosActualizarActivity.this, "Request error",
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
                            Toast.makeText(MedicamentosActualizarActivity.this, "Error al obtener los laboratorios",
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
                                MedicamentosActualizarActivity.this,
                                android.R.layout.simple_spinner_item, laboratorios);
                        adapterLaboratorio.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        laboratorio.setAdapter(adapterLaboratorio);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MedicamentosActualizarActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            error.printStackTrace();
            Toast.makeText(MedicamentosActualizarActivity.this, "Request error",
                    Toast.LENGTH_SHORT).show();
            finish();
        });
        requestQueue.add(jsonObjectRequest);

    }

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
                Toast.makeText(MedicamentosActualizarActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            assert result != null;
            if (result.has("message")) {
                String message = result.get("message").getAsString();
                Log.e(TAG, "Message: " + message);
                Toast.makeText(MedicamentosActualizarActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}