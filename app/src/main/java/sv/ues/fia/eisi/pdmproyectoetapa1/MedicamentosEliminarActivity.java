package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;

public class MedicamentosEliminarActivity extends AppCompatActivity {

    EditText eMedicamentos;
    Button eliminarM;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos_eliminar);
        eMedicamentos = findViewById(R.id.MedicamentoEl);
        eliminarM = findViewById(R.id.btnElimM);

        eliminarM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarMedicamento();
            }
        });

    }
    public void eliminarMedicamento() {
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosEliminarActivity.this);
        MedicamentoDAO medicamentoDAO = db.getMedicamentosDAO();
        String idMedicamento = eMedicamentos.getText().toString();

        try {
            // Verificar que el campo no esté vacío
            if (idMedicamento.isEmpty()) {
                Toast.makeText(this, "Ingrese un ID de medicamento", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener el medicamento de la base de datos
            Medicamento medicamento = medicamentoDAO.obtener(idMedicamento);

            // Verificar si el medicamento existe antes de intentar eliminarlo
            if (medicamento != null) {
                medicamentoDAO.eliminar(medicamento);
                Toast.makeText(this, "Medicamento eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El medicamento con ID " + idMedicamento + " no existe", Toast.LENGTH_SHORT).show();
            }
        } catch (DAOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al eliminar medicamento", Toast.LENGTH_SHORT).show();
        }
    }


}