package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class MenuMedicamentos extends AppCompatActivity {

    Button insertarMedicamento;
    Button actualizarMedicamento;
    Button eliminarMedicamento;
    Button consultarMedicamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_medicamentos);

        insertarMedicamento=(Button)findViewById(R.id.btn_insertarMedicamento);
        insertarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMedicamentos.this, MedicamentosInsertarActivity.class);
                startActivity(intent);
            }
        });

        actualizarMedicamento=(Button)findViewById(R.id.btn_actualizarMedicamento);
        actualizarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMedicamentos.this, MedicamentosActualizarActivity.class);
                startActivity(intent);
            }
        });

        eliminarMedicamento=(Button)findViewById(R.id.btn_eliminarMedicamento);
        eliminarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMedicamentos.this, MedicamentosEliminarActivity.class);
                startActivity(intent);
            }
        });


        consultarMedicamento=(Button)findViewById(R.id.btn_consultarMedicamento);
        consultarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuMedicamentos.this, MedicamentosConsultarActivity.class);
                startActivity(intent);
            }
        });
    }

}