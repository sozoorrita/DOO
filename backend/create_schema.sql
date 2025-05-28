-- Asegúrate de que exista pgcrypto si usas gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1) Rol
CREATE TABLE rol (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 2) Categoría
CREATE TABLE categoria (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 3) Subcategoría (→ categoría)
CREATE TABLE subcategoria (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  codigoCategoria UUID NOT NULL
    REFERENCES categoria(codigo)
);

-- 4) Estado de mesa
CREATE TABLE estado_mesa (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 5) Mesa (→ estado_mesa)
CREATE TABLE mesa (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  codigoEstadoMesa UUID NOT NULL
    REFERENCES estado_mesa(codigo)
);

-- 6) Tipo de venta
CREATE TABLE tipo_venta (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 7) Forma de pago
CREATE TABLE forma_pago (
  codigo UUID PRIMARY KEY NOT NULL,
  tipoPago VARCHAR(50) NOT NULL
);

-- 8) Usuario (→ rol)
CREATE TABLE usuario (
  id UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  contrasena VARCHAR(50) NOT NULL,
  codigoRol UUID NOT NULL
    REFERENCES rol(codigo)
);

-- 9) Sesión de trabajo (→ usuario)
CREATE TABLE sesion_trabajo (
  codigo UUID PRIMARY KEY NOT NULL,
  idUsuario UUID NOT NULL
    REFERENCES usuario(id),
  baseCaja      DOUBLE NOT NULL,   
  fechaApertura TIMESTAMP NOT NULL,
  fechaCierre   TIMESTAMP NOT NULL,
);

-- 10) Informe de caja (→ sesión de trabajo)
CREATE TABLE informe_caja (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoSesionTrabajo UUID NOT NULL
    REFERENCES sesion_trabajo(codigo),
  fecha              TIMESTAMP   NOT NULL,
  totalVenta        DOUBLE NOT NULL,
  pagoEfectivo      DOUBLE NOT NULL,
  pagoTransferencia DOUBLE NOT NULL
);

-- 11) Indicador de inventario
CREATE TABLE indicador_inventario (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
);

-- 12) Producto (→ categoría, subcategoría, indicador_inventario)
CREATE TABLE producto (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  precioLugar DOUBLE NOT NULL,
  precioLLevar DOUBLE NOT NULL,
  codigoSubcategoria UUID NOT NULL
    REFERENCES subcategoria(codigo),
  limiteCantidad INT NOT NULL,
);

-- 13) Inventario (→ producto)
CREATE TABLE inventario (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoProducto UUID NOT NULL
    REFERENCES producto(codigo),
  nombreProducto VARCHAR(50) NOT NULL,
  cantidad INT,
  codigoIndicador UUID NOT NULL
    REFERENCES indicador_inventario(codigo),
);

-- 14) Venta (→ sesión_trabajo, forma_pago, tipo_venta, mesa)
CREATE TABLE venta (
  codigo UUID PRIMARY KEY NOT NULL,
  fecha TIMESTAMP NOT NULL,
  codigoSesionTrabajo UUID NOT NULL
    REFERENCES sesion_trabajo(codigo),
  codigoFormaPago UUID NOT NULL
    REFERENCES forma_pago(codigo),
  codigoTipoVenta UUID NOT NULL
    REFERENCES tipo_venta(codigo),
  codigoMesa UUID NOT NULL
    REFERENCES mesa(codigo),
  totalVenta DOUBLE NOT NULL
);

-- 15) Detalle de venta (→ venta, producto)
CREATE TABLE detalle_venta (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoVenta UUID NOT NULL
    REFERENCES venta(codigo),
  codigoProducto UUID NOT NULL
    REFERENCES producto(codigo),
  cantidad INT NOT NULL,
  nombreProducto VARCHAR(50),
  precioUnitario DOUBLE NOT NULL
);
