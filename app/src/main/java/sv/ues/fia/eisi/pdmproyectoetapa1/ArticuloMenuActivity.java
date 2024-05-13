package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ArticuloMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_articulo_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.articulo_menu), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void consultarArticulo(View view) {
        Intent intent = new Intent(this, ConsultarArticuloActivity.class);
        startActivity(intent);
    }

    public void insertarArticulo(View view) {
        Intent intent = new Intent(this, InsertarArticuloActivity.class);
        startActivity(intent);
    }

    public void modificarArticulo(View view) {
        Intent intent = new Intent(this, ModificarArticuloActivity.class);
        startActivity(intent);
    }

    public void eliminarArticulo(View view) {
        Intent intent = new Intent(this, EliminarArticuloActivity.class);
        startActivity(intent);
    }
}