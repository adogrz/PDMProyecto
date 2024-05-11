package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;

public class MainActivity extends AppCompatActivity {
   Button btnmedicamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      btnmedicamentos=(Button)findViewById(R.id.btn_Medicamentos);
        btnmedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnmedicamentos = new Intent(MainActivity.this, MenuMedicamentos.class);
                startActivity(btnmedicamentos);
            }
        });

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(MainActivity.this);
        ProveedorDAO proveedorDAO=db.getProveedorDAO();
        TipoArticuloDAO tipoArticuloDAO=db.getTipoArticuloDAO();
        ArticuloDAO articuloDAO=db.getArticuloDAO();
        MedicamentoDAO medicamentoDAO=db.getMedicamentosDAO();
        LaboratorioDAO laboratorioDAO=db.getLaboratorioDAO();
        FormaFarmaceuticaDAO formaFarmaceuticaDAO=db.getFormaFarmaceuticaDAO();
        ViaAdministracionDAO viaAdministracionDAO=db.getViaAdministracionDAO();

        try {
            List<Articulo> articulos=articuloDAO.obtenerTodos();
            List<Proveedor> proveedores=proveedorDAO.obtenerTodos();
            List<TipoArticulo> tipoArticulos=tipoArticuloDAO.obtenerTodos();
            List<Laboratorio> laboratorios=laboratorioDAO.obtenerTodos();
            List<FormaFarmaceutica> formaFarmaceuticas=formaFarmaceuticaDAO.obtenerTodos();
            List<ViaAdministracion> viaAdministraciones=viaAdministracionDAO.obtenerTodos();

            String idArticulo=articulos.get(0).getIdArticulo();
            String idProveedor=proveedores.get(0).getIdProveedor();
            String idTipoArticulo=tipoArticulos.get(0).getId();
            String idLaboratorio=laboratorios.get(0).getIdLaboratorio();
            String idFormaFarmaceutica=formaFarmaceuticas.get(0).getIdFormaFarmaceutica();
            String idViaAdministracion=viaAdministraciones.get(0).getIdViaAdministracion();

            medicamentoDAO.insertar(new Medicamento("MED-01","2021-01-01","2024-01-01","SI",
            idArticulo,idFormaFarmaceutica,idViaAdministracion,idLaboratorio));

            Toast.makeText(MainActivity.this,"Medicamento insertado",Toast.LENGTH_SHORT).show();
        }catch (DAOException e){
           Toast.makeText(MainActivity.this,"Error al medicamento articulo",Toast.LENGTH_SHORT).show();
        }

        textView = findViewById(R.id.textViewPrueba);

        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(MainActivity.this);
        ProveedorDAO proveedorDAO = controlBaseDatos.getProveedorDAO();

        try {
            List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
            Proveedor primerProveedor = proveedores.get(1);
            Proveedor proveedorEncontrado = proveedorDAO.obtener(primerProveedor.getIdProveedor());
            textView.setText(proveedorEncontrado.getNombre());
            Toast.makeText(MainActivity.this, "Proveedores obtenidos correctamente.", Toast.LENGTH_SHORT).show();
        } catch (DAOException e) {
            Toast.makeText(MainActivity.this, "Error al obtener los proveedores.", Toast.LENGTH_SHORT).show();
        }
    }
}