package sv.ues.fia.eisi.pdmproyectoetapa1.ui.venta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;
import java.util.List;
import android.widget.ArrayAdapter;
import android.os.Build;
import java.time.LocalDate;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MetodoPagoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.VentaDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Articulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Cliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.MetodoPago;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Venta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleVentaDAO;

public class AtualizarVentaActivity extends AppCompatActivity {

    EditText editCodigoCliente, editCodigoVenta, editCodigoDetalle, editCantidad, editNombreCliente, editApellidoCliente;

    Spinner metodoPago, articulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_venta);

        editCodigoVenta = (EditText) findViewById(R.id.txt_codigoVenta);
        editCodigoDetalle = (EditText) findViewById(R.id.txt_codigoDetalle);
        editCodigoCliente = (EditText) findViewById(R.id.txt_codigoCliente);
        editNombreCliente = (EditText) findViewById(R.id.txt_nombreCliente);
        editApellidoCliente = (EditText) findViewById(R.id.txt_apellidoCliente);
        editCantidad = (EditText) findViewById(R.id.txt_cantidad);
        metodoPago = findViewById(R.id.opcion_metodoPago);
        articulo = findViewById(R.id.opcion_articulo);
        spinnerMetodosPagos(metodoPago);
        spinnerArticulos(articulo);
    }

    //Metodo para implementar el spinner de metodos de pago
    public void spinnerMetodosPagos(View v){

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(AtualizarVentaActivity.this);
        MetodoPagoDAO metodoPagoDAO = db.getMetodoPagoDAO();

        try {
            List<MetodoPago> metodoPagos = metodoPagoDAO.obtenerTodos();
            ArrayAdapter<MetodoPago> adapterMetodoPago=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, metodoPagos);

            metodoPago.setAdapter(adapterMetodoPago);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para implementar el spinner de articulos
    public void spinnerArticulos(View v){

        ControlBaseDatos db=ControlBaseDatos.obtenerInstancia(AtualizarVentaActivity.this);
        ArticuloDAO articuloDAO= db.getArticuloDAO();

        try {
            List<Articulo> articulos = articuloDAO.obtenerTodos();
            ArrayAdapter<Articulo> adapterArticulo=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, articulos);

            articulo.setAdapter(adapterArticulo);

        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para actualizar una venta
    public void agregarVenta(View view) {

        //Se obtienen los datos de los campos de texto y verifica que no exista ningun error
        try {

            //Obteniendo la instancia de la base de datos
            ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(AtualizarVentaActivity.this);

            //Obteniendo las instancias de los DAO
            VentaDAO ventaDAO = controlBaseDatos.getVentaDAO();
            ClienteDAO clienteDAO = controlBaseDatos.getClienteDAO();
            DetalleVentaDAO detalleVentaDAO = controlBaseDatos.getDetalleVentaDAO();
            ArticuloDAO articuloDAO = controlBaseDatos.getArticuloDAO();

            //Obteniendo la opcion seleccionada en los spinners
            Articulo articuloSeleccionado = (Articulo) articulo.getSelectedItem();
            MetodoPago metodoSeleccionado = (MetodoPago) metodoPago.getSelectedItem();

            //Variables para almacenar los datos de la venta, cliente y detalle de la venta
            double precioArticulo = articuloSeleccionado.getPrecioUnitario();
            String idMetodoPago = metodoSeleccionado.getIdMetodoPago();
            String idArticulo = articuloSeleccionado.getIdArticulo();
            String idDetalleVenta = editCodigoDetalle.getText().toString();
            String codigoVenta = editCodigoVenta.getText().toString();
            String codigoCliente = editCodigoCliente.getText().toString();
            String nombreCliente = editNombreCliente.getText().toString();
            String ApellidoCliente = editApellidoCliente.getText().toString();
            String Cantidad = editCantidad.getText().toString();
            String montoTotal = String.valueOf(precioArticulo * Double.parseDouble(Cantidad));



            //Creando las instancias de los objetos
            Cliente cliente = new Cliente();
            Venta venta = new Venta();
            DetalleVenta detalleVenta = new DetalleVenta();

            //Asignando los valores al cliente
            cliente.setIdCliente(codigoCliente);
            cliente.setNombreCliente(nombreCliente);
            cliente.setApellidoCliente(ApellidoCliente);

            //Asignando los valores a la venta
            venta.setMontoTotalVenta(Double.parseDouble(montoTotal));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                venta.setFechaVenta(LocalDate.now().toString());
            }
            venta.setIdCliente(codigoCliente);
            venta.setIdMetodoPago(idMetodoPago);
            venta.setIdVenta(codigoVenta);

            //Asignando los valores al detalle de la venta
            detalleVenta.setIdDetalleVenta(idDetalleVenta);
            detalleVenta.setIdVenta(codigoVenta);
            detalleVenta.setIdArticulo(idArticulo);
            detalleVenta.setSubtotalVenta(Double.parseDouble(montoTotal));
            detalleVenta.setCantidadProductoVenta(Integer.parseInt(Cantidad));

            if (codigoVenta.isEmpty() || codigoCliente.isEmpty() || nombreCliente.isEmpty() || ApellidoCliente.isEmpty() || Cantidad.isEmpty()) {
                Toast.makeText(AtualizarVentaActivity.this, "Por favor, rellene todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            //Insertando los datos en la base de datos
            clienteDAO.modificar(cliente);
            ventaDAO.modificar(venta);
            detalleVentaDAO.modificar(detalleVenta);

            Toast.makeText(AtualizarVentaActivity.this, "Venta actualizada correctamente.", Toast.LENGTH_SHORT).show();
        } catch (DAOException e) {
            e.printStackTrace();
            Toast.makeText(AtualizarVentaActivity.this, "Error al actualizar el venta.", Toast.LENGTH_SHORT).show();
        }
    }

    public void limpiarCampos(View view) {
        editCodigoVenta.setText("");
        editCodigoDetalle.setText("");
        editCodigoCliente.setText("");
        editNombreCliente.setText("");
        editApellidoCliente.setText("");
        editCantidad.setText("");
    }

}