package sv.ues.fia.eisi.pdmproyectoetapa1.ui.venta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.VentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Venta;

public class EliminarVentaActivity extends AppCompatActivity {

    EditText editCodigoVenta;
    Button botonEliminarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_venta);

        editCodigoVenta = (EditText) findViewById(R.id.txt_codVenta);
        botonEliminarVenta = (Button) findViewById(R.id.btn_eliminar);

        botonEliminarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarVenta();
            }
        });
    }

    //Metodo para eliminar una venta
    private void eliminarVenta(){
        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(EliminarVentaActivity.this);
        VentaDAO ventaDAO = controlBaseDatos.getVentaDAO();
        String idVenta = editCodigoVenta.getText().toString();

        try {
            Venta venta = ventaDAO.obtener(idVenta);
            ventaDAO.eliminar(venta);

            Toast.makeText(EliminarVentaActivity.this, "La venta se elimino correctamente.", Toast.LENGTH_SHORT).show();
        }catch (DAOException e){
            e.printStackTrace();
            Toast.makeText(EliminarVentaActivity.this, "Error eliminando la venta.", Toast.LENGTH_SHORT).show();
        }

    }

    public void limpiar(View v){
        editCodigoVenta.setText("");
    }
}