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
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleRecetaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.FormaFarmaceuticaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.LaboratorioDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.RecetaMedicaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleReceta;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medico;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.RecetaMedica;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.ViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;

public class MainActivity extends AppCompatActivity {

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

        //metodo para cambiar a MenuRecetaActivity
        Button btnMenuReceta = findViewById(R.id.btn_RecetaMedica);
        btnMenuReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuRecetaActivity.class);
                startActivity(intent);
            }
        });

        try{
            ControlBaseDatos controlBD = ControlBaseDatos.obtenerInstancia(MainActivity.this);
            ProveedorDAO proveedorDAO = controlBD.getProveedorDAO();
            List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
            String idProveedor = proveedores.get(0).getIdProveedor();
            TipoArticuloDAO tipoArticuloDAO = controlBD.getTipoArticuloDAO();
            List<TipoArticulo> tipoArticulos = tipoArticuloDAO.obtenerTodos();
            String idTipoArticulo = tipoArticulos.get(0).getId();
            MedicoDAO medicoDAO = controlBD.getMedicoDAO();
            List<Medico> medicos = medicoDAO.obtenerTodos();
            //medicoDAO.insertar(new Medico("1", "Gregorio", "Casa",
            //        "Nefrologia", "00420"));

            ArticuloDAO articuloDAO = controlBD.getArticuloDAO();
            /*articuloDAO.insertar(new Articulo("Art-001", "Fentanilo", 40.99,
                    200, "Para tener zombies", idProveedor, idTipoArticulo));
             */
            FormaFarmaceuticaDAO formaFarmaceuticaDAO = controlBD.getFormaFarmaceuticaDAO();
            List<FormaFarmaceutica> formasFarmaceuticas = formaFarmaceuticaDAO.obtenerTodos();

            ViaAdministracionDAO viaAdministracionDAO = controlBD.getViaAdministracionDAO();
            List<ViaAdministracion> viasAdministracion = viaAdministracionDAO.obtenerTodos();

            LaboratorioDAO laboratorioDAO = controlBD.getLaboratorioDAO();
            List<Laboratorio> laboratorios = laboratorioDAO.obtenerTodos();

            //ArticuloDAO articuloDAO = controlBD.getArticuloDAO();
            List<Articulo> articulos = articuloDAO.obtenerTodos();

            MedicamentoDAO medicamentoDAO = controlBD.getMedicamentosDAO();
            /*medicamentoDAO.insertar(new Medicamento("Med-001", "2020-01-01",
                    "2028-01-01", "true",articulos.get(0).getIdArticulo(),
                    formasFarmaceuticas.get(0).getIdFormaFarmaceutica(),
                    viasAdministracion.get(0).getIdViaAdministracion(),laboratorios.get(9).getIdLaboratorio()));
             */
            //MedicoDAO medicoDAO = controlBD.getMedicoDAO();
            //List<Medico> medicos = medicoDAO.obtenerTodos();
            RecetaMedicaDAO recetaMedicaDAO = controlBD.getRecetaMedicaDAO();
            /*
            recetaMedicaDAO.insertar(new RecetaMedica("Rec-002", "420",
                    "2024-04-20",medicos.get(0).getIdMedico()));
             */

            List<RecetaMedica> recetas = recetaMedicaDAO.obtenerTodos();
            List<Medicamento> medicamentos = medicamentoDAO.obtenerTodos();

            /*
            DetalleRecetaDAO detalleRecetaDAO = controlBD.getDetalleRecetaDAO();
            detalleRecetaDAO.insertar(new DetalleReceta("DRC-002", "cronica permamente","3 cada 2 horas",
                    "2024-04-20","2050-01-01",recetas.get(0).getIdReceta(),
                    medicamentos.get(0).getIdMedicamento()));
             */

        }catch (DAOException e){
            Toast.makeText(this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}