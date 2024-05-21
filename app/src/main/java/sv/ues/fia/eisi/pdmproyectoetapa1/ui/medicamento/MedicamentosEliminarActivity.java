package sv.ues.fia.eisi.pdmproyectoetapa1.ui.medicamento;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Medicamento;

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
        ControlBaseDatos db = ControlBaseDatos.obtenerInstancia(MedicamentosEliminarActivity.this);
        MedicamentoDAO medicamentoDAO = db.getMedicamentosDAO();
        String idMedicamento = eMedicamentos.getText().toString();

        // Verificar que el campo no esté vacío
        if (idMedicamento.isEmpty()) {
            Toast.makeText(this, "Ingrese un ID de medicamento", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Obtener el medicamento de la base de datos
            Medicamento medicamento = medicamentoDAO.obtener(idMedicamento);
            medicamentoDAO.eliminar(medicamento);
            Toast.makeText(this, "Medicamento eliminado", Toast.LENGTH_SHORT).show();

        } catch (DAOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al eliminar medicamento", Toast.LENGTH_SHORT).show();
        }
    }


}