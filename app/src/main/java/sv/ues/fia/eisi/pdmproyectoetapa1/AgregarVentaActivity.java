package sv.ues.fia.eisi.pdmproyectoetapa1;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import java.util.List;
import android.widget.ArrayAdapter;
import android.os.Build;
import java.time.LocalDate;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.MetodoPagoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.ProveedorDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.TipoArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.VentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Cliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.DetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.MetodoPago;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Proveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.TipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.modelo.Venta;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.DetalleVentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDetalleVenta;


public class AgregarVentaActivity extends Activity {

    EditText editCodigoCliente, editCodigoVenta, editCantidad, editNombreCliente, editApellidoCliente;

    Spinner metodoPago, articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_venta);

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(AgregarVentaActivity.this);
        ProveedorDAO proveedorDAO = db.getProveedorDAO();
        TipoArticuloDAO tipoArticuloDAO = db.getTipoArticuloDAO();
        ArticuloDAO articuloDAO = db.getArticuloDAO();

        /*
        try {

            List<Proveedor> proveedores = proveedorDAO.obtenerTodos();
            List<TipoArticulo> tipos = tipoArticuloDAO.obtenerTodos();

            String idProveedor = proveedores.get(0).getIdProveedor();
            String idTipo = tipos.get(0).getId();

            articuloDAO.insertar(new Articulo("1", "Virogrip", 0.60, 10, "Para la gripe", idProveedor, idTipo));
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        */

        editCodigoVenta = (EditText) findViewById(R.id.txt_codigoVenta);
        editCodigoCliente = (EditText) findViewById(R.id.txt_codigoCliente);
        editNombreCliente = (EditText) findViewById(R.id.txt_nombreCliente);
        editApellidoCliente = (EditText) findViewById(R.id.txt_apellidoCliente);
        editCantidad = (EditText) findViewById(R.id.txt_cantidad);
        metodoPago = findViewById(R.id.opcion_metodoPago);
        articulo = findViewById(R.id.opcion_articulo);
        spinnerMetodosPagos(metodoPago);
        spinnerArticulos(articulo);
    }

    public void spinnerMetodosPagos(View v){

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(AgregarVentaActivity.this);
        MetodoPagoDAO metodoPagoDAO = db.getMetodoPagoDAO();

        try {
            List<MetodoPago> metodoPagos = metodoPagoDAO.obtenerTodos();
            ArrayAdapter<MetodoPago> adapterMetodoPago=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, metodoPagos);

            metodoPago.setAdapter(adapterMetodoPago);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }

    public void spinnerArticulos(View v){

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(AgregarVentaActivity.this);
        ArticuloDAO articuloDAO= db.getArticuloDAO();

        try {
            List<Articulo> articulos = articuloDAO.obtenerTodos();
            ArrayAdapter<Articulo> adapterArticulo=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, articulos);

            articulo.setAdapter(adapterArticulo);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }

    }
    public void agregarVenta(View view) {
        try {
            ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(AgregarVentaActivity.this);
            VentaDAO ventaDAO = controlBaseDatos.getVentaDAO();
            ClienteDAO clienteDAO = controlBaseDatos.getClienteDAO();
            DetalleVentaDAO detalleVentaDAO = controlBaseDatos.getDetalleVentaDAO();

            Articulo articuloSeleccionado = (Articulo) articulo.getSelectedItem();
            MetodoPago metodoSeleccionado = (MetodoPago) metodoPago.getSelectedItem();

            double precioArticulo = articuloSeleccionado.getPrecioUnitario();
            String idMetodoPago = metodoSeleccionado.getIdMetodoPago();
            String idArticulo = articuloSeleccionado.getIdArticulo();
            String idDetalleVenta = EntradaDetalleVenta.generarIdDetalleVenta() ;
            String codigoVenta = editCodigoVenta.getText().toString();
            String codigoCliente = editCodigoCliente.getText().toString();
            String nombreCliente = editNombreCliente.getText().toString();
            String ApellidoCliente = editApellidoCliente.getText().toString();
            String Cantidad = editCantidad.getText().toString();
            String montoTotal = String.valueOf(precioArticulo * Double.parseDouble(Cantidad));

            Cliente cliente = new Cliente();
            Venta venta = new Venta();
            DetalleVenta detalleVenta = new DetalleVenta();

            cliente.setIdCliente(codigoCliente);
            cliente.setNombreCliente(nombreCliente);
            cliente.setApellidoCliente(ApellidoCliente);

            venta.setMontoTotalVenta(Double.parseDouble(montoTotal));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            venta.setFechaVenta(LocalDate.now().toString());
            }
            venta.setIdCliente(codigoCliente);
            venta.setIdMetodoPago(idMetodoPago);
            venta.setIdVenta(codigoVenta);

            detalleVenta.setIdVenta(codigoVenta);
            detalleVenta.setIdArticulo(idArticulo);
            detalleVenta.setIdDetalleVenta(idDetalleVenta);
            detalleVenta.setSubtotalVenta(Double.parseDouble(montoTotal));
            detalleVenta.setCantidadProductoVenta(Integer.parseInt(Cantidad));

            clienteDAO.insertar(cliente);
            ventaDAO.insertar(venta);
            detalleVentaDAO.insertar(detalleVenta);

            Toast.makeText(AgregarVentaActivity.this, "Venta insertada correctamente.", Toast.LENGTH_SHORT).show();
        } catch (DAOException e) {
            e.printStackTrace();
            Toast.makeText(AgregarVentaActivity.this, "Error al insertar el venta.", Toast.LENGTH_SHORT).show();
        }
    }
}