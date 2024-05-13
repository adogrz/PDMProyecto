package sv.ues.fia.eisi.pdmproyectoetapa1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ConsultarVentaActivity extends AppCompatActivity {

    EditText editCodigoCliente, editCodigoVenta, editCodigoDetalle, editArticulo, editMetodoPago, editCantidad, editNombreCliente, editApellidoCliente;

    Button botonConsultarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_venta);

        editCodigoVenta = (EditText) findViewById(R.id.txt_codigoVenta);
        editCodigoDetalle = (EditText) findViewById(R.id.txt_codigoDetalle);
        editCodigoCliente = (EditText) findViewById(R.id.txt_codigoCliente);
        editNombreCliente = (EditText) findViewById(R.id.txt_nombreCliente);
        editApellidoCliente = (EditText) findViewById(R.id.txt_apellidoCliente);
        editCantidad = (EditText) findViewById(R.id.txt_cantidad);
        editArticulo = (EditText) findViewById(R.id.opcion_articulo);
        editMetodoPago = (EditText) findViewById(R.id.opcion_metodoPago);

        botonConsultarVenta = (Button) findViewById(R.id.btn_consultar);

        botonConsultarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarVenta();
            }
        });
    }

    private void consultarVenta() {
        ControlBaseDatos controlBaseDatos = ControlBaseDatos.obtenerInstancia(ConsultarVentaActivity.this);
        DetalleVenta detalleVentaEncontrado = null;
        String nombreArticulo = null;
        String nombreMetodoPago = null;
        Venta ventaEncontrada = null;
        Cliente clienteEncontrado = null;
        ClienteDAO clienteDAO = controlBaseDatos.getClienteDAO();
        DetalleVentaDAO detalleVentaDAO = controlBaseDatos.getDetalleVentaDAO();
        ArticuloDAO articuloDAO = controlBaseDatos.getArticuloDAO();
        MetodoPagoDAO metodoPagoDAO = controlBaseDatos.getMetodoPagoDAO();


        try {
            ventaEncontrada = controlBaseDatos.getVentaDAO().obtener(editCodigoVenta.getText().toString());
            nombreMetodoPago = metodoPagoDAO.obtener(ventaEncontrada.getIdMetodoPago()).getTipoMetodoPago();
            clienteEncontrado = clienteDAO.obtener(ventaEncontrada.getIdCliente());
            detalleVentaEncontrado = detalleVentaDAO.obtenerPorIdVenta(ventaEncontrada.getIdVenta());
            nombreArticulo = articuloDAO.obtener(detalleVentaEncontrado.getIdArticulo()).getNombre();


            Toast.makeText(ConsultarVentaActivity.this, "Datos obtenidos de la venta.", Toast.LENGTH_SHORT).show();
        } catch (DAOException e) {
            Toast.makeText(ConsultarVentaActivity.this, "Error al obtener datos de laa venta.", Toast.LENGTH_SHORT).show();
        }

        if (ventaEncontrada != null && clienteEncontrado != null && detalleVentaEncontrado != null) {
            editCodigoDetalle.setText(detalleVentaEncontrado.getIdDetalleVenta());
            editCodigoCliente.setText(clienteEncontrado.getIdCliente());
            editNombreCliente.setText(clienteEncontrado.getNombreCliente());
            editApellidoCliente.setText(clienteEncontrado.getApellidoCliente());
            editMetodoPago.setText(nombreMetodoPago);
            editCantidad.setText(String.valueOf(detalleVentaEncontrado.getCantidadProductoVenta()));
            editArticulo.setText(nombreArticulo);

        }else{
            editCodigoDetalle.setText("");
            editCodigoCliente.setText("");
            editNombreCliente.setText("");
            editApellidoCliente.setText("");
            editMetodoPago.setText("");
            editCantidad.setText("");
            editArticulo.setText("");
        }

    }
}