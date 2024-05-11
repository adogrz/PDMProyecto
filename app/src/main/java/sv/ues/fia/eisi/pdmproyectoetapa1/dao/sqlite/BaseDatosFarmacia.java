package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import static java.lang.String.format;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaProveedor;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaTipoArticulo;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaLaboratorio;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaViaAdministracion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaFormaFarmaceutica;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDepartamento;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaMunicipio;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDistrito;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaDireccion;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaLocal;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaMetodoPago;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaCliente;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaVenta;
import sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite.FarmaciaContrato.EntradaMedicamento;

/**
 * Clase que administra la conexión a la base de datos SQLite y su estructura.
 */
public final class BaseDatosFarmacia extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "proyectoEtapa1.s3db";
    private static final int VERSION_BASE_DATOS = 1;
    private final Context contexto;

    /**
     * Interfaz que establece los nombres de las tablas de la base de datos.
     */
    interface Tablas {
        String TIPO_ARTICULO = "tipo_articulo";
        String PROVEEDOR = "proveedor";
        String ARTICULO = "articulo";
        String DEPARTAMENTO = "departamento";
        String MUNICIPIO = "municipio";
        String DISTRITO = "distrito";
        String DIRECCION = "direccion";
        String LOCAL = "local";
        String LOCAL_ARTICULO = "local_articulo";
        String LABORATORIO = "laboratorio";
        String VIA_ADMINISTRACION = "via_administracion";
        String FORMA_FARMACEUTICA = "forma_farmaceutica";
        String METODO_PAGO = "metodo_pago";
        String MEDICAMENTO = "medicamento";
        String CLIENTE = "cliente";
        String VENTA = "venta";
    }

    /**
     * Interfaz que establece las referencias de las claves foráneas.
     */
    interface Referencias {
        String ID_TIPO_ARTICULO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.TIPO_ARTICULO, EntradaTipoArticulo.ID_TIPO_ARTICULO);
        String ID_PROVEEDOR = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.PROVEEDOR, EntradaProveedor.ID_PROVEEDOR);
        String ID_ARTICULO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.ARTICULO, EntradaArticulo.ID_ARTICULO);
        String ID_DEPARTAMENTO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.DEPARTAMENTO, EntradaDepartamento.ID_DEPARTAMENTO);
        String ID_MUNICIPIO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.MUNICIPIO, EntradaMunicipio.ID_MUNICIPIO);
        String ID_DISTRITO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.DISTRITO, EntradaDistrito.ID_DISTRITO);
        String ID_DIRECCION = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.DIRECCION, EntradaDireccion.ID_DIRECCION);
        String ID_LOCAL = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.LOCAL, EntradaLocal.ID_LOCAL);
        String ID_FORMA_FARMACEUTICA = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.FORMA_FARMACEUTICA, EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA);
        String ID_VIA_ADMINISTRACION = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.VIA_ADMINISTRACION, EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
        String ID_LABORATORIO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.LABORATORIO, EntradaLaboratorio.ID_LABORATORIO);
        String ID_METODO_PAGO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.METODO_PAGO, EntradaMetodoPago.ID_METODO_PAGO);
        String ID_CLIENTE = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.CLIENTE, EntradaCliente.ID_CLIENTE);
        String ID_MEDICAMENTO = format("REFERENCES %s(%s) ON UPDATE CASCADE ON DELETE CASCADE",
                Tablas.MEDICAMENTO, EntradaMedicamento.ID_MEDICAMENTO);
    }

    public BaseDatosFarmacia(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION_BASE_DATOS);
        this.contexto = context;
    }

    /**
     * Método que se ejecuta al abrir la conexión a la base de datos.
     * Habilita las claves foráneas.
     *
     * @param db Base de datos.
     */
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }

    /**
     * Método que crea las tablas de la base de datos.
     *
     * @param db Base de datos.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.TIPO_ARTICULO, BaseColumns._ID,
                EntradaTipoArticulo.ID_TIPO_ARTICULO, EntradaTipoArticulo.NOMBRE_TIPO_ARTICULO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)", Tablas.PROVEEDOR,
                BaseColumns._ID, EntradaProveedor.ID_PROVEEDOR, EntradaProveedor.NOMBRE_PROVEEDOR,
                EntradaProveedor.TELEFONO_PROVEEDOR));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s REAL NOT NULL, %s INTEGER " +
                        "NOT NULL CHECK(%s>=0), %s TEXT NOT NULL, %s TEXT NOT NULL %s, %s TEXT NOT NULL %s)",
                Tablas.ARTICULO, BaseColumns._ID, EntradaArticulo.ID_ARTICULO,
                EntradaArticulo.NOMBRE_ARTICULO, EntradaArticulo.PRECIO_UNITARIO_ARTICULO,
                EntradaArticulo.STOCK_ARTICULO, EntradaArticulo.STOCK_ARTICULO,
                EntradaArticulo.DESCRIPCION_ARTICULO, EntradaArticulo.ID_PROVEEDOR,
                Referencias.ID_PROVEEDOR, EntradaArticulo.ID_TIPO_ARTICULO,
                Referencias.ID_TIPO_ARTICULO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.DEPARTAMENTO,
                BaseColumns._ID, EntradaDepartamento.ID_DEPARTAMENTO,
                EntradaDepartamento.NOMBRE_DEPARTAMENTO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL %s)",
                Tablas.MUNICIPIO, BaseColumns._ID, EntradaMunicipio.ID_MUNICIPIO,
                EntradaMunicipio.NOMBRE_MUNICIPIO, EntradaMunicipio.ID_DEPARTAMENTO,
                Referencias.ID_DEPARTAMENTO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL %s)",
                Tablas.DISTRITO, BaseColumns._ID, EntradaDistrito.ID_DISTRITO,
                EntradaDistrito.NOMBRE_DISTRITO, EntradaDistrito.ID_MUNICIPIO,
                Referencias.ID_MUNICIPIO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, " +
                        "%s TEXT NOT NULL, %s TEXT NOT NULL %s)", Tablas.DIRECCION, BaseColumns._ID,
                EntradaDireccion.ID_DIRECCION, EntradaDireccion.COLONIA, EntradaDireccion.CALLE,
                EntradaDireccion.PASAJE, EntradaDireccion.NUMERO, EntradaDireccion.ID_DISTRITO,
                Referencias.ID_DISTRITO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL %s)",
                Tablas.LOCAL, BaseColumns._ID, EntradaLocal.ID_LOCAL, EntradaLocal.NOMBRE_LOCAL,
                EntradaLocal.ID_DIRECCION, Referencias.ID_DIRECCION));
//        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER AUTOINCREMENT, " +
//                        "%s TEXT NOT NULL %s, %s TEXT NOT NULL %s, PRIMARY KEY (%s, %s, %s))",
//                Tablas.LOCAL_ARTICULO, BaseColumns._ID, EntradaLocalArticulo.ID_LOCAL,
//                Referencias.ID_LOCAL, EntradaLocalArticulo.ID_ARTICULO, Referencias.ID_ARTICULO,
//                BaseColumns._ID, EntradaLocalArticulo.ID_LOCAL, EntradaLocalArticulo.ID_ARTICULO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.METODO_PAGO,
                BaseColumns._ID, EntradaMetodoPago.ID_METODO_PAGO, EntradaMetodoPago.TIPO_METODO_PAGO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.LABORATORIO,
                BaseColumns._ID, EntradaLaboratorio.ID_LABORATORIO,
                EntradaLaboratorio.NOMBRE_LABORATORIO));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.VIA_ADMINISTRACION,
                BaseColumns._ID, EntradaViaAdministracion.ID_VIA_ADMINISTRACION,
                EntradaViaAdministracion.TIPO_VIA_ADMINISTRACION));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s TEXT NOT NULL)", Tablas.FORMA_FARMACEUTICA,
                BaseColumns._ID, EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA,
                EntradaFormaFarmaceutica.TIPO_FORMA_FARMACEUTICA));
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT UNIQUE NOT NULL, %s REAL NOT NULL, %s DATE NOT NULL, %s TEXT NOT NULL %S," +
                "%s TEXT NOT NULL %s)", Tablas.VENTA, BaseColumns._ID, EntradaVenta.ID_VENTA,
                EntradaVenta.MONTO_TOTAL_VENTA, EntradaVenta.FECHA_VENTA,
                EntradaMetodoPago.ID_METODO_PAGO, Referencias.ID_METODO_PAGO,
                EntradaVenta.ID_CLIENTE, Referencias.ID_CLIENTE));
        //tabla medicamento
        db.execSQL(format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "%s TEXT UNIQUE NOT NULL, %s DATE NOT NULL, %s DATE NOT NULL,  " +
                        "%s, %s TEXT NOT NULL %s, %s, %s TEXT NOT NULL %s, %s TEXT NOT NULL %s, " +
                        "%s TEXT NOT NULL %s, %s TEXT NOT NULL %s)",
                Tablas.MEDICAMENTO, BaseColumns._ID, EntradaMedicamento.ID_MEDICAMENTO,
                EntradaMedicamento.FECHA_EXPEDICION, EntradaMedicamento.FECHA_EXPIRACION,
                EntradaMedicamento.REQUIERE_RECETA_MEDICA,
                EntradaMedicamento.ID_ARTICULO, Referencias.ID_ARTICULO,
                EntradaMedicamento.ID_LABORATORIO, Referencias.ID_LABORATORIO,
                EntradaMedicamento.ID_VIA_ADMINISTRACION, Referencias.ID_VIA_ADMINISTRACION,
                EntradaMedicamento.ID_FORMA_FARMACEUTICA, Referencias.ID_FORMA_FARMACEUTICA));



        // TODO Crear tabla de receta
        // TODO Crear tabla de detalle receta
        // TODO Crear tabla de detalle venta
        // TODO Crear tabla de venta
        // TODO Crear tabla de detalle compra
        // TODO Crear tabla de compra

        // Insertar datos iniciales
        insertarDatosInicial(db);
    }

    private void insertarDatosInicial(SQLiteDatabase db) {
        // Insertar datos de prueba en la tabla PROVEEDOR
        insertarDatosProveedores(db);

        // Insertar datos de prueba en la tabla TIPO_ARTICULO
        insertarDatosTipoArticulo(db);

        // Insertar datos de prueba en la tabla METODO_PAGO
        insertarDatosMetodosPago(db);

        // Insertar datos de prueba en la tabla LABORATORIO
        insertarDatosLaboratorio(db);

        // Insertar datos de prueba en la tabla VIA_ADMINISTRACION
        insertarDatosViaAdministracion(db);

        // Insertar datos de prueba en la tabla FORMA_FARMACEUTICA
        insertarDatosFormasFarmaceuticas(db);

        // TODO insetar locales
    }

    private void insertarDatosProveedores(SQLiteDatabase db) {
        String[][] proveedores = {
                {"Laboratorios Pharmedic", "2251-5716"},
                {"Megapharma Labs De Ceam", "+598 2683-6300"},
                {"Biokemical", "2227-1934"}
        };

        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(Tablas.PROVEEDOR);
        query.append(" (");
        query.append(EntradaProveedor.ID_PROVEEDOR);
        query.append(", ");
        query.append(EntradaProveedor.NOMBRE_PROVEEDOR);
        query.append(", ");
        query.append(EntradaProveedor.TELEFONO_PROVEEDOR);
        query.append(") VALUES ");

        for (int i = 0; i < proveedores.length; i++) {
            query.append(format("('%s', '%s', '%s')", EntradaProveedor.generarIdProveedor(),
                    proveedores[i][0], proveedores[i][1]));
            if (i < proveedores.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    private void insertarDatosTipoArticulo(SQLiteDatabase db) {
        String[] tiposArticulo = {"Medicamento", "Articulo de libreria"};
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(Tablas.TIPO_ARTICULO);
        query.append(" (");
        query.append(EntradaTipoArticulo.ID_TIPO_ARTICULO);
        query.append(", ");
        query.append(EntradaTipoArticulo.NOMBRE_TIPO_ARTICULO);
        query.append(") VALUES ");

        for (int i = 0; i < tiposArticulo.length; i++) {
            query.append(format("('%s', '%s')", EntradaTipoArticulo.generarIdTipoArticulo(),
                    tiposArticulo[i]));
            if (i < tiposArticulo.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    private void insertarDatosMetodosPago(SQLiteDatabase db) {
        String[] metodosPago = {"Efectivo", "Tarjeta de crédito", "Tarjeta de débito",
                "Transferencia bancaria", "Bitcoin"};
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(Tablas.METODO_PAGO);
        query.append(" (");
        query.append(EntradaMetodoPago.ID_METODO_PAGO);
        query.append(", ");
        query.append(EntradaMetodoPago.TIPO_METODO_PAGO);
        query.append(") VALUES ");

        for (int i = 0; i < metodosPago.length; i++) {
            query.append(format("('%s', '%s')", EntradaMetodoPago.generarIdMetodoPago(),
                    metodosPago[i]));
            if (i < metodosPago.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    private void insertarDatosLaboratorio(SQLiteDatabase db) {
        String[] laboratorios = {"Pfizer", "Johnson & Johnson", "Roche", "Novartis", "Merck",
                "GlaxoSmithKline", "AstraZeneca", "Sanofi", "AbbVie", "Bayer",
                "Eli Lilly and Company", "Abbott Laboratories", "Bristol Myers Squibb",
                "Teva Pharmaceutical Industries", "Boehringer Ingelheim"};
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(Tablas.LABORATORIO);
        query.append(" (");
        query.append(EntradaLaboratorio.ID_LABORATORIO);
        query.append(", ");
        query.append(EntradaLaboratorio.NOMBRE_LABORATORIO);
        query.append(") VALUES ");

        for (int i = 0; i < laboratorios.length; i++) {
            query.append(format("('%s', '%s')", EntradaLaboratorio.generarIdLaboratorio(),
                    laboratorios[i]));
            if (i < laboratorios.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    private void insertarDatosViaAdministracion(SQLiteDatabase db) {
        String[] viasAdministracion = {"Oral", "Sublingual", "Rectal", "Intravenosa",
                "Intramuscular", "Tópica", "Inhalatoria"};
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(Tablas.VIA_ADMINISTRACION);
        query.append(" (");
        query.append(EntradaViaAdministracion.ID_VIA_ADMINISTRACION);
        query.append(", ");
        query.append(EntradaViaAdministracion.TIPO_VIA_ADMINISTRACION);
        query.append(") VALUES ");

        for (int i = 0; i < viasAdministracion.length; i++) {
            query.append(format("('%s', '%s')", EntradaViaAdministracion.generarIdViaAdministracion(),
                    viasAdministracion[i]));
            if (i < viasAdministracion.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    private void insertarDatosFormasFarmaceuticas(SQLiteDatabase db) {
        String[] formasFarmaceuticas = {"Tableta", "Cápsula", "Jarabe", "Suspensión", "Solución",
                "Crema", "Gel", "Parche", "Supositorio", "Inyectable"};
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(Tablas.FORMA_FARMACEUTICA);
        query.append(" (");
        query.append(EntradaFormaFarmaceutica.ID_FORMA_FARMACEUTICA);
        query.append(", ");
        query.append(EntradaFormaFarmaceutica.TIPO_FORMA_FARMACEUTICA);
        query.append(") VALUES ");

        for (int i = 0; i < formasFarmaceuticas.length; i++) {
            query.append(format("('%s', '%s')", EntradaFormaFarmaceutica.generarIdFormaFarmaceutica(),
                    formasFarmaceuticas[i]));
            if (i < formasFarmaceuticas.length - 1) {
                query.append(", ");
            }
        }

        db.execSQL(query.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(format("DROP TABLE IF EXISTS %s", Tablas.ARTICULO));
        db.execSQL(format("DROP TABLE IF EXISTS %s", Tablas.PROVEEDOR));
        db.execSQL(format("DROP TABLE IF EXISTS %s", Tablas.TIPO_ARTICULO));

        onCreate(db);
    }
}