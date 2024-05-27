package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.TipoArticulo;

public class InsertarArticuloActivity extends AppCompatActivity {
    private static final String TAG = InsertarArticuloActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private Spinner spinnerProveedor, spinnerTipoArticulo, spinnerLocal;
    private EditText editIdArticulo, editNombreArticulo, editDescripcionArticulo, editPrecioArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_articulo);
        editIdArticulo = findViewById(R.id.editTextIdArticulo);
        editNombreArticulo = findViewById(R.id.editTextNombreArticulo);
        editDescripcionArticulo = findViewById(R.id.editTextDescripcionArticulo);
        editPrecioArticulo = findViewById(R.id.editTextPrecioUnitarioArticulo);
        spinnerProveedor = findViewById(R.id.spinnerProveedorArticulo);
        spinnerTipoArticulo = findViewById(R.id.spinnerTipoArticuloArticulo);
        spinnerLocal = findViewById(R.id.spinnerLocalArticulo);
        FloatingActionButton botGuardarArticulo = findViewById(R.id.fabGuardarActiculo);

        requestQueue = Volley.newRequestQueue(this);

        llenarSpinnerProveedor();
        llenarSpinnerTipoArticulo();
        llenarSpinnerLocal();

        botGuardarArticulo.setOnClickListener(v -> insertarArticulo());
    }

    private void insertarArticulo() {
        // Obtener los datos de los campos
        String idArticulo = editIdArticulo.getText().toString();
        String nombreArticulo = editNombreArticulo.getText().toString();
        String descripcionArticulo = editDescripcionArticulo.getText().toString();
        String precioArticulo = editPrecioArticulo.getText().toString();
        Proveedor proveedor = (Proveedor) spinnerProveedor.getSelectedItem();
        TipoArticulo tipoArticulo = (TipoArticulo) spinnerTipoArticulo.getSelectedItem();
        Local local = (Local) spinnerLocal.getSelectedItem();

        // Validar que los campos no estén vacíos
        if (idArticulo.isEmpty() || nombreArticulo.isEmpty() || precioArticulo.isEmpty()) {
            Toast.makeText(InsertarArticuloActivity.this, "Por favor, rellene todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String urlProv = "https://pdmproyectouno.000webhostapp.com/articulo_insertar.php?id="
                + idArticulo + "&nombre=" + nombreArticulo + "&precio_unitario="
                + precioArticulo + "&descripcion=" + descripcionArticulo + "&id_proveedor="
                + proveedor.getIdProveedor() + "&id_tipo_articulo=" + tipoArticulo.getId();

        new InsertDataTask().execute(urlProv);

        String urlLoc = "https://pdmproyectouno.000webhostapp.com/articulo_sucursal_insertar.php" +
                "?id_articulo=" + idArticulo + "&id_sucursal=" + local.getIdLocal();
        
        new InsertDataTask().execute(urlLoc);

        // Finalizar la actividad
        finish();
    }

    private void llenarSpinnerProveedor() {
        String url = "https://pdmproyectouno.000webhostapp.com/proveedor_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(InsertarArticuloActivity.this, "No se pudo obtener " +
                                    "los proveedores", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Proveedor>>() {
                        }.getType();
                        List<Proveedor> proveedores = gson.fromJson(
                                response.getJSONArray("proveedor").toString(),
                                listType
                        );
                        ArrayAdapter<Proveedor> proveedorAdapter = new ArrayAdapter<>(
                                InsertarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, proveedores
                        );
                        proveedorAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerProveedor.setAdapter(proveedorAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InsertarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                    Toast.makeText(InsertarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void llenarSpinnerTipoArticulo() {
        String url = "https://pdmproyectouno.000webhostapp.com/tipo_articulo_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(InsertarArticuloActivity.this, "No se pudo obtener " +
                                    "los tipos de articulo", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<TipoArticulo>>() {
                        }.getType();
                        List<TipoArticulo> tiposArticulo = gson.fromJson(
                                response.getJSONArray("tipo_articulo").toString(),
                                listType
                        );
                        ArrayAdapter<TipoArticulo> tipoArticuloAdapter = new ArrayAdapter<>(
                                InsertarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, tiposArticulo
                        );
                        tipoArticuloAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerTipoArticulo.setAdapter(tipoArticuloAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InsertarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                    Toast.makeText(InsertarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void llenarSpinnerLocal() {
        String url = "https://pdmproyectouno.000webhostapp.com/sucursal_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(InsertarArticuloActivity.this, "No se pudo obtener " +
                                    "los locales", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<Local>>() {
                        }.getType();
                        List<Local> locales = gson.fromJson(
                                response.getJSONArray("sucursal").toString(),
                                listType
                        );
                        ArrayAdapter<Local> localAdapter = new ArrayAdapter<>(
                                InsertarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, locales
                        );
                        localAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerLocal.setAdapter(localAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InsertarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                (Response.ErrorListener) error -> {
                    error.printStackTrace();
                    Toast.makeText(InsertarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
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
                Toast.makeText(InsertarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            assert result != null;
            if (result.has("message")) {
                String message = result.get("message").getAsString();
                Log.e(TAG, "Message: " + message);
                Toast.makeText(InsertarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}