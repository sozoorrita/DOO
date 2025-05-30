-- Asegúrate de que exista pgcrypto si usas gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1) Rol
CREATE TABLE Rol (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 2) Categoría
CREATE TABLE Categoria (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 3) Subcategoría (→ categoría)
CREATE TABLE Subcategoria (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  codigoCategoria UUID NOT NULL
    REFERENCES Categoria(codigo)
);

-- 4) Estado de mesa
CREATE TABLE EstadoMesa (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 5) Mesa (→ estado_mesa)
CREATE TABLE Mesa (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  codigoEstadoMesa UUID NOT NULL
    REFERENCES EstadoMesa(codigo)
);

-- 6) Tipo de venta
CREATE TABLE TipoVenta (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 7) Forma de pago
CREATE TABLE FormaPago (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 8) Usuario (→ rol)
CREATE TABLE Usuario (
  id UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  contrasena VARCHAR(50) NOT NULL,
  codigoRol UUID NOT NULL
    REFERENCES Rol(codigo)
);

-- 9) Sesión de trabajo (→ usuario)
CREATE TABLE SesionTrabajo (
  codigo UUID PRIMARY KEY NOT NULL,
  idUsuario UUID NOT NULL
    REFERENCES Usuario(id),
  baseCaja      NUMERIC(12,2) NOT NULL,   
  fechaApertura TIMESTAMP NOT NULL,
  fechaCierre   TIMESTAMP NOT NULL
);

-- 10) Informe de caja (→ sesión de trabajo)
CREATE TABLE InformeCaja (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoSesionTrabajo UUID NOT NULL
    REFERENCES SesionTrabajo(codigo),
  fecha             TIMESTAMP   NOT NULL,
  totalVenta        NUMERIC(12,2) NOT NULL,
  pagoEfectivo      NUMERIC(12,2) NOT NULL,
  pagoTransferencia NUMERIC(12,2) NOT NULL
);

-- 11) Indicador de inventario
CREATE TABLE IndicadorInventario (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL
);

-- 12) Producto (→ categoría, subcategoría, indicador_inventario)
CREATE TABLE Producto (
  codigo UUID PRIMARY KEY NOT NULL,
  nombre VARCHAR(50) NOT NULL,
  precioLugar NUMERIC(12,2) NOT NULL,
  precioLLevar NUMERIC(12,2) NOT NULL,
  codigoSubcategoria UUID NOT NULL
    REFERENCES Subcategoria(codigo),
  limiteCantidad INT NOT NULL
);

-- 13) Inventario (→ producto)
CREATE TABLE Inventario (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoProducto UUID NOT NULL
    REFERENCES Producto(codigo),
  nombreProducto VARCHAR(50) NOT NULL,
  cantidad INT,
  codigoIndicador UUID NOT NULL
    REFERENCES IndicadorInventario(codigo)
);

-- 14) Venta (→ sesión_trabajo, forma_pago, tipo_venta, mesa)
CREATE TABLE Venta (
  codigo UUID PRIMARY KEY NOT NULL,
  fecha TIMESTAMP NOT NULL,
  codigoSesionTrabajo UUID NOT NULL
    REFERENCES SesionTrabajo(codigo),
  codigoFormaPago UUID NOT NULL
    REFERENCES FormaPago(codigo),
  codigoTipoVenta UUID NOT NULL
    REFERENCES TipoVenta(codigo),
  codigoMesa UUID NOT NULL
    REFERENCES Mesa(codigo),
  totalVenta NUMERIC(12,2) NOT NULL
);

-- 15) Detalle de venta (→ venta, producto)
CREATE TABLE DetalleVenta (
  codigo UUID PRIMARY KEY NOT NULL,
  codigoVenta UUID NOT NULL
    REFERENCES Venta(codigo),
  codigoProducto UUID NOT NULL
    REFERENCES Producto(codigo),
  cantidad INT NOT NULL,
  nombreProducto VARCHAR(50),
  precioAplicado NUMERIC(12,2) NOT NULL
);
