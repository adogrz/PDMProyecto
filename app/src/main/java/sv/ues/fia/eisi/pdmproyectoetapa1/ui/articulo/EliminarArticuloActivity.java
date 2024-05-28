package sv.ues.fia.eisi.pdmproyectoetapa1.ui.articulo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.HttpHandler;

public class EliminarArticuloActivity extends AppCompatActivity {
    EditText editIdArticulo;
    Button buttonEliminarArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_articulo);
        editIdArticulo = findViewById(R.id.etIdArticuloEliminar);
        buttonEliminarArticulo = findViewById(R.id.btnEliminarArticulo);

        buttonEliminarArticulo.setOnClickListener(v -> eliminarArticulo());
    }

    public void eliminarArticulo() {
        String idArticulo = editIdArticulo.getText().toString().trim();
        if (idArticulo.isEmpty()) {
            editIdArticulo.setError("Campo obligatorio");
            return;
        }

        String urlEliminarArticulo = "https://pdmproyectouno.000webhostapp.com/articulo_eliminar.php?id=" + idArticulo;
        new EliminarArticuloTask().execute(urlEliminarArticulo);
    }

    private class EliminarArticuloTask extends AsyncTask<String, Void, JsonObject> {
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
                String message = result.get("message").getAsString();
                if (success) {
                    Toast.makeText(EliminarArticuloActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EliminarArticuloActivity.this,  message, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(EliminarArticuloActivity.this, "Error al obtener respuesta del servidor", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


