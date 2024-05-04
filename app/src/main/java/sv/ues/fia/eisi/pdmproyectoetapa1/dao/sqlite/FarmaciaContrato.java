package sv.ues.fia.eisi.pdmproyectoetapa1.dao.sqlite;

import java.util.UUID;

/**
 * Clase que establece los nombres de las columnas de la base de datos.
 */
public class FarmaciaContrato {
    public interface ColumnasProveedor {
        String ID_PROVEEDOR = "id_proveedor";
        String NOMBRE_PROVEEDOR = "nombre_proveedor";
        String TELEFONO_PROVEEDOR = "telefono_proveedor";
    }

    public interface ColumnasTipoArticulo {
        String ID_TIPO_ARTICULO = "id_tipo_articulo";
        String NOMBRE_TIPO_ARTICULO = "nombre_tipo_articulo";
    }

    public interface ColumnasArticulo {
        String ID_ARTICULO = "id_articulo";
        String NOMBRE_ARTICULO = "nombre_articulo";
        String PRECIO_UNITARIO_ARTICULO = "precio_unitario_articulo";
        String STOK_ARTICULO = "stok_articulo";
        String DESCRIPCION_ARTICULO = "descripcion_articulo";
        String ID_PROVEEDOR = "id_proveedor";
        String ID_TIPO_ARTICULO = "id_tipo_articulo";
    }

    public interface ColumnasDepartamento {
        String ID_DEPARTAMENTO = "id_departamento";
        String NOMBRE_DEPARTAMENTO = "nombre_departamento";
    }

    public interface ColumnasMunicipio {
        String ID_MUNICIPIO = "id_municipio";
        String NOMBRE_MUNICIPIO = "nombre_municipio";
        String ID_DEPARTAMENTO = "id_departamento";
    }

    public interface ColumnasDistrito {
        String ID_DISTRITO = "id_distrito";
        String NOMBRE_DISTRITO = "nombre_distrito";
        String ID_MUNICIPIO = "id_municipio";
    }

    public interface ColumnasDireccion {
        String ID_DIRECCION = "id_direccion";
        String COLONIA = "colonia";
        String CALLE = "calle";
        String PASAJE = "pasaje";
        String NUMERO = "numero";
        String ID_DISTRITO = "id_distrito";
    }

    public interface ColumnasLocal {
        String ID_LOCAL = "id_local";
        String NOMBRE_LOCAL = "nombre_local";
        String ID_DIRECCION = "id_direccion";
    }

    public interface ColumnasLocalArticulo {
        String ID_LOCAL = "id_local";
        String ID_ARTICULO = "id_articulo";
    }

    public interface ColumnasLaboratorio {
        String ID_LABORATORIO = "id_laboratorio";
        String NOMBRE_LABORATORIO = "nombre_laboratorio";
    }

    public interface ColumnasFormaFarmaceutica {
        String ID_FORMA_FARMACEUTICA = "id_forma_farmaceutica";
        String TIPO_FORMA_FARMACEUTICA = "tipo_forma_farmaceutica";
    }

    public interface ColumnasViaAdministracion {
        String ID_VIA_ADMINISTRACION = "id_via_administracion";
        String TIPO_VIA_ADMINISTRACION = "tipo_via_administracion";
    }

    public interface ColumnasMedicamento {
        String ID_MEDICAMENTO = "id_medicamento";
        String FECHA_EXPEDICION = "fecha_expedicion";
        String FECHA_EXPIRACION = "fecha_expiracion";
        String REQUIERE_RECETA_MEDICA = "requiere_receta_medica";
        String ID_ARTICULO = "id_articulo";
        String ID_FORMA_FARMACEUTICA = "id_forma_farmaceutica";
        String ID_VIA_ADMINISTRACION = "id_via_administracion";
        String ID_LABORATORIO = "id_laboratorio";
    }

    public interface ColumnasMetodoPago {
        String ID_METODO_PAGO = "id_metodo_pago";
        String TIPO_METODO_PAGO = "tipo_metodo_pago";
    }

    public static class EntradaProveedor implements ColumnasProveedor {
        public static String generarIdProveedor() {
            return "PROV-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaTipoArticulo implements ColumnasTipoArticulo {
        public static String generarIdTipoArticulo() {
            return "TA-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaArticulo implements ColumnasArticulo {
        public static String generarIdArticulo() {
            return "ART-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaDepartamento implements ColumnasDepartamento {
        public static String generarIdDepartamento() {
            return "DEP-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaMunicipio implements ColumnasMunicipio {
        public static String generarIdMunicipio() {
            return "MUN-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaDistrito implements ColumnasDistrito {
        public static String generarIdDistrito() {
            return "DIS-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaDireccion implements ColumnasDireccion {
        public static String generarIdDireccion() {
            return "DIR-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaLocal implements ColumnasLocal {
        public static String generarIdLocal() {
            return "LOC-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaLocalArticulo implements ColumnasLocalArticulo {
    }

    public static class EntradaLaboratorio implements ColumnasLaboratorio {
        public static String generarIdLaboratorio() {
            return "LAB-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaFormaFarmaceutica implements ColumnasFormaFarmaceutica {
        public static String generarIdFormaFarmaceutica() {
            return "FF-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaViaAdministracion implements ColumnasViaAdministracion {
        public static String generarIdViaAdministracion() {
            return "VA-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaMedicamento implements ColumnasMedicamento {
        public static String generarIdMedicamento() {
            return "MED-" + UUID.randomUUID().toString();
        }
    }

    public static class EntradaMetodoPago implements ColumnasMetodoPago {
        public static String generarIdMetodoPago() {
            return "MP-" + UUID.randomUUID().toString();
        }
    }

    private FarmaciaContrato() {
    }
}
