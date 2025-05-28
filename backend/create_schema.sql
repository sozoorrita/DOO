-- Asegúrate de que exista pgcrypto si usas gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1) Rol
CREATE TABLE rol (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 2) Categoría
CREATE TABLE categoria (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 3) Subcategoría (→ categoría)
CREATE TABLE subcategoria (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  codigo_categoria UUID NOT NULL
    REFERENCES categoria(codigo)
);

-- 4) Estado de mesa
CREATE TABLE estado_mesa (
  codigo UUID PRIMARY KEY,
  descripcion VARCHAR(100) NOT NULL
);

-- 5) Mesa (→ estado_mesa)
CREATE TABLE mesa (
  codigo UUID PRIMARY KEY,
  numero INTEGER NOT NULL,
  capacidad INTEGER NOT NULL,
  codigo_estado UUID NOT NULL
    REFERENCES estado_mesa(codigo)
);

-- 6) Tipo de venta
CREATE TABLE tipo_venta (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 7) Forma de pago
CREATE TABLE forma_pago (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

-- 8) Usuario (→ rol)
CREATE TABLE usuario (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  apellido VARCHAR(100) NOT NULL,
  correo_electronico VARCHAR(150) NOT NULL UNIQUE,
  contrasena VARCHAR(255) NOT NULL,
  codigo_rol UUID NOT NULL
    REFERENCES rol(codigo)
);

-- 9) Sesión de trabajo (→ usuario)
CREATE TABLE sesion_trabajo (
  codigo UUID PRIMARY KEY,
  codigo_usuario UUID NOT NULL
    REFERENCES usuario(codigo),
  fecha_inicio TIMESTAMP NOT NULL,
  fecha_fin    TIMESTAMP
);

-- 10) Informe de caja (→ sesión de trabajo)
CREATE TABLE informe_caja (
  codigo UUID PRIMARY KEY,
  codigo_sesion_trabajo UUID NOT NULL
    REFERENCES sesion_trabajo(codigo),
  fecha              DATE    NOT NULL,
  total_venta        NUMERIC(12,2) NOT NULL,
  pago_efectivo      NUMERIC(12,2) NOT NULL,
  pago_transferencia NUMERIC(12,2) NOT NULL
);

-- 11) Indicador de inventario
CREATE TABLE indicador_inventario (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(255)
);

-- 12) Producto (→ categoría, subcategoría, indicador_inventario)
CREATE TABLE producto (
  codigo UUID PRIMARY KEY,
  nombre VARCHAR(150) NOT NULL,
  precio NUMERIC(12,2) NOT NULL,
  codigo_categoria UUID NOT NULL
    REFERENCES categoria(codigo),
  codigo_subcategoria UUID NOT NULL
    REFERENCES subcategoria(codigo),
  codigo_indicador_inventario UUID NOT NULL
    REFERENCES indicador_inventario(codigo)
);

-- 13) Inventario (→ producto)
CREATE TABLE inventario (
  codigo UUID PRIMARY KEY,
  codigo_producto UUID NOT NULL
    REFERENCES producto(codigo),
  cantidad INTEGER NOT NULL
);

-- 14) Venta (→ sesión_trabajo, forma_pago, tipo_venta, mesa)
CREATE TABLE venta (
  codigo UUID PRIMARY KEY,
  fecha TIMESTAMP NOT NULL,
  codigo_sesion_trabajo UUID NOT NULL
    REFERENCES sesion_trabajo(codigo),
  codigo_forma_pago UUID NOT NULL
    REFERENCES forma_pago(codigo),
  codigo_tipo_venta UUID NOT NULL
    REFERENCES tipo_venta(codigo),
  codigo_mesa UUID NOT NULL
    REFERENCES mesa(codigo),
  total NUMERIC(12,2) NOT NULL
);

-- 15) Detalle de venta (→ venta, producto)
CREATE TABLE detalle_venta (
  codigo UUID PRIMARY KEY,
  codigo_venta UUID NOT NULL
    REFERENCES venta(codigo),
  codigo_producto UUID NOT NULL
    REFERENCES producto(codigo),
  cantidad INTEGER NOT NULL,
  precio_unitario NUMERIC(12,2) NOT NULL
);
