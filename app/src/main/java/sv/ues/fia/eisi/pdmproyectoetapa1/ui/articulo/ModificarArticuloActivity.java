package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.GetDataTask;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Local;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.TipoArticulo;

public class ModificarArticuloActivity extends AppCompatActivity {
    private static final String TAG = ModificarArticuloActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    EditText editIdArticulo, editNombreArticulo, editDescripcionArticulo, editPrecioArticulo;
    Spinner spinnerProveedor, spinnerTipoArticulo, spinnerLocal;
    Button botModificarArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_articulo);
        editIdArticulo = findViewById(R.id.et_id_articulo);
        editNombreArticulo = findViewById(R.id.et_nombre_articulo);
        editDescripcionArticulo = findViewById(R.id.et_descripcion_articulo);
        editPrecioArticulo = findViewById(R.id.et_precio_unitario_articulo);
        spinnerProveedor = findViewById(R.id.spinner_proveedor);
        spinnerTipoArticulo = findViewById(R.id.spinner_tipo_articulo);
        spinnerLocal = findViewById(R.id.spinner_sucursal);
        botModificarArticulo = findViewById(R.id.btn_guardar_articulo);

        requestQueue = Volley.newRequestQueue(this);

        llenarSpinnerProveedor();
        llenarSpinnerTipoArticulo();
        llenarSpinnerLocal();

        botModificarArticulo.setOnClickListener(v -> modificarArticulo());
    }

    private void llenarSpinnerProveedor() {
        String url = "https://pdmproyectouno.000webhostapp.com/proveedor_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(ModificarArticuloActivity.this, "No se pudo obtener " +
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
                                ModificarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, proveedores
                        );
                        proveedorAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerProveedor.setAdapter(proveedorAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ModificarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(ModificarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void llenarSpinnerTipoArticulo() {
        String url = "https://pdmproyectouno.000webhostapp.com/tipo_articulo_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(ModificarArticuloActivity.this, "No se pudo obtener " +
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
                                ModificarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, tiposArticulo
                        );
                        tipoArticuloAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerTipoArticulo.setAdapter(tipoArticuloAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ModificarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(ModificarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void llenarSpinnerLocal() {
        String url = "https://pdmproyectouno.000webhostapp.com/sucursal_obtener_todos.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (!success) {
                            Toast.makeText(ModificarArticuloActivity.this, "No se pudo obtener " +
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
                                ModificarArticuloActivity.this,
                                android.R.layout.simple_spinner_item, locales
                        );
                        localAdapter.setDropDownViewResource(
                                android.R.layout.simple_spinner_dropdown_item
                        );
                        spinnerLocal.setAdapter(localAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ModificarArticuloActivity.this, "JSON parse error",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(ModificarArticuloActivity.this, "Request error",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void modificarArticulo() {
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
            Toast.makeText(ModificarArticuloActivity.this, "Por favor, rellene todos los campos",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String urlProv = "https://pdmproyectouno.000webhostapp.com/articulo_modificar.php?id="
                + idArticulo + "&nombre=" + nombreArticulo + "&precio_unitario="
                + precioArticulo + "&descripcion=" + descripcionArticulo + "&id_proveedor="
                + proveedor.getIdProveedor() + "&id_tipo_articulo=" + tipoArticulo.getId();

        new ModificarArticuloActivity.InsertDataTask().execute(urlProv);

        String urlLoc = "https://pdmproyectouno.000webhostapp.com/articulo_sucursal_modificar.php" +
                "?id_articulo=" + idArticulo + "&new_id_sucursal=" + local.getIdLocal();

        new ModificarArticuloActivity.InsertDataTask().execute(urlLoc);

        // Finalizar la actividad
        finish();
    }

    private void manejarError(JsonObject result) {
        String message = result != null ? result.get("message").getAsString() : "Unknown error";
        Log.e(TAG, "Error: " + message);
        Toast.makeText(ModificarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ModificarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            assert result != null;
            if (result.has("message")) {
                String message = result.get("message").getAsString();
                Log.e(TAG, "Message: " + message);
                Toast.makeText(ModificarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}