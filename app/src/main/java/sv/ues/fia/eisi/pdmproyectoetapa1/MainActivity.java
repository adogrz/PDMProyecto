package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
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
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MedicamentoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ViaAdministracionDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.FormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Laboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Medicamento;
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editRecta), (v, insets) -> {
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
        MedicamentoDAO medicamentoDAO=db.getMedicamentosDAO();
        TipoArticuloDAO tipoArticuloDAO=db.getTipoArticuloDAO();
        ArticuloDAO articuloDAO=db.getArticuloDAO();
        FormaFarmaceuticaDAO formaFarmaceuticaDAO=db.getFormaFarmaceuticaDAO();
        ViaAdministracionDAO viaAdministracionDAO=db.getViaAdministracionDAO();
        LaboratorioDAO laboratorioDAO=db.getLaboratorioDAO();


   /* try {
           List<Proveedor> proveedores=proveedorDAO.obtenerTodos();
            List<TipoArticulo> tipoArticulos=tipoArticuloDAO.obtenerTodos();
            List<Articulo> articulos=articuloDAO.obtenerTodos();
            List<Laboratorio> laboratorios=laboratorioDAO.obtenerTodos();
            List<Medicamento> medicamentos=medicamentoDAO.obtenerTodos();
            List<ViaAdministracion> viaAdministracions=viaAdministracionDAO.obtenerTodos();
            List<FormaFarmaceutica> formaFarmaceuticas=formaFarmaceuticaDAO.obtenerTodos();



            String idProveedor=proveedores.get(1).getIdProveedor();
            String idTipoArticulo=tipoArticulos.get(0).getId();
           // String idArticulo=articuloDAO.obtenerTodos().get(0).getIdArticulo();
            String idLaboratorio=laboratorios.get(0).getIdLaboratorio();
            String idViaAdministracion=viaAdministracionDAO.obtenerTodos().get(0).getIdViaAdministracion();
            String idFormaFarmaceutica=formaFarmaceuticaDAO.obtenerTodos().get(0).getIdFormaFarmaceutica();


          //medicamentoDAO.insertar(new Medicamento("MED-001","2021-01-01","2021-01-01","Si",idArticulo,idFormaFarmaceutica,idViaAdministracion,idLaboratorio));
            articuloDAO.insertar(new Articulo("ART-002","Broncol",3.50,10,"tratamiento para la bronquitis",idProveedor,idTipoArticulo));
           // articuloDAO.modificar(new Articulo("ART-002","Virogrip",0.70,10,"Para resfriados",idProveedor,idTipoArticulo));

            Toast.makeText(MainActivity.this,"Articulo insertado",Toast.LENGTH_SHORT).show();
        }catch (DAOException e){
           Toast.makeText(MainActivity.this,"Error al insertar articulo",Toast.LENGTH_SHORT).show();
        }*/


    }
}