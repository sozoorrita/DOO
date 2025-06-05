package co.edu.uco.FondaControl.entity;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;

import java.util.UUID;

public final class UsuarioEntity {
    private UUID id;
    private String nombre;
    private UUID codigoRol;
    private String contrasena;

    public UsuarioEntity() {
        setId(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoRol(UtilUUID.obtenerValorDefecto());
        setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public UsuarioEntity(final UUID id) {
        setId(id);
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoRol(UtilUUID.obtenerValorDefecto());
        setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public UsuarioEntity(final UUID id, final String nombre, final UUID codigoRol, final String contrasena) {
        setId(id);
        setNombre(nombre);
        setCodigoRol(codigoRol);
        setContrasena(contrasena);
    }

    public static UsuarioEntity obtenerValorDefecto() {
        return new UsuarioEntity();
    }

    public static UsuarioEntity obtenerDesdeUUID(UUID idUsuario) {
        return new UsuarioEntity(idUsuario);
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
    }

    public UUID getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(final UUID codigoRol) {
        this.codigoRol = UtilUUID.obtenerValorDefecto(codigoRol);
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(final String contrasena) {
        this.contrasena = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(contrasena);
    }

    // Alias
    public UUID getCodigo() {
        return getId();
    }

    public static class Builder {
        private UUID id = UtilUUID.obtenerValorDefecto();
        private String nombre = UtilTexto.getInstancia().obtenerValorDefecto();
        private UUID codigoRol = UtilUUID.obtenerValorDefecto();
        private String contrasena = UtilTexto.getInstancia().obtenerValorDefecto();

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder codigoRol(UUID codigoRol) {
            this.codigoRol = codigoRol;
            return this;
        }

        public Builder contrasena(String contrasena) {
            this.contrasena = contrasena;
            return this;
        }

        public UsuarioEntity crear() {
            return new UsuarioEntity(id, nombre, codigoRol, contrasena);
        }
    }
}
