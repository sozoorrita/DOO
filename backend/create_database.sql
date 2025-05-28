-- 1) Creamos la BD fondaControl como propietario postgres
CREATE DATABASE "fondaControl"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       TEMPLATE = template0;

-- 2) Concede todos los permisos al usuario postgres
GRANT ALL PRIVILEGES ON DATABASE "fondaControl" TO postgres;
