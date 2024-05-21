package sv.ues.fia.eisi.pdmproyectoetapa1.ui.venta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sv.ues.fia.eisi.pdmproyectoetapa1.R;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ArticuloDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.ClienteDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.MetodoPagoDAO;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DAOException;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.sqlite.ControlBaseDatos;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Cliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.DetalleVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.modelo.Venta;
import sv.ues.fia.eisi.pdmproyectoetapa1.data.dao.DetalleVentaDAO;

public class ConsultarVentaActivity extends AppCompatActivity {

    EditText editCodigoCliente, editCodigoVenta, editCodigoDetalle, editArticulo, editMetodoPago, editCantidad, editNombreCliente, editApellidoCliente;

    Button botonConsultarVenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_venta);

        editCodigoVenta = findViewById(R.id.txt_codigoVenta);
        editCodigoDetalle = findViewById(R.id.txt_codigoDetalle);
        editCodigoCliente = findViewById(R.id.txt_codigoCliente);
        editNombreCliente = findViewById(R.id.txt_nombreCliente);
        editApellidoCliente = findViewById(R.id.txt_apellidoCliente);
        editCantidad = findViewById(R.id.txt_cantidad);
        editArticulo = findViewById(R.id.opcion_articulo);
        editMetodoPago = findViewById(R.id.opcion_metodoPago);

        botonConsultarVenta = findViewById(R.id.btn_actualizar);

        botonConsultarVenta.setOnClickListener(v -> consultarVenta());
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
            Toast.makeText(ConsultarVentaActivity.this, "No se obtuvo ninguna venta", Toast.LENGTH_SHORT).show();
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