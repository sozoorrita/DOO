package co.edu.uco.FondaControl.dto;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

import java.util.UUID;

public final class UsuarioDTO {
    private UUID id;
    private String nombre;
    private UUID codigoRol;
    private String contrasena;

    public UsuarioDTO() {
        setId(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoRol(UtilUUID.obtenerValorDefecto());
        setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public UsuarioDTO(final UUID id) {
        setId(id);
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoRol(UtilUUID.obtenerValorDefecto());
        setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public UsuarioDTO(final UUID id, final String nombre, final UUID codigoRol, final String contrasena) {
        setId(id);
        setNombre(nombre);
        setCodigoRol(codigoRol);
        setContrasena(contrasena);
    }

    public static UsuarioDTO obtenerValorDefecto() {
        return new UsuarioDTO();
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

        public UsuarioDTO crear() {
            return new UsuarioDTO(id, nombre, codigoRol, contrasena);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
