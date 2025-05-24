package co.edu.uco.FondaControl.businesslogic.businesslogic.domain;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

import java.util.UUID;

public final class UsuarioDomain {
    private UUID id;
    private String nombre;
    private UUID codigoRol;
    private String contrasena;

    public UsuarioDomain() {
        setId(UtilUUID.obtenerValorDefecto());
        setNombre(UtilTexto.getInstancia().obtenerValorDefecto());
        setCodigoRol(UtilUUID.obtenerValorDefecto());
        setContrasena(UtilTexto.getInstancia().obtenerValorDefecto());
    }

    public UsuarioDomain(final UUID id, final String nombre, final UUID codigoRol, final String contrasena) {
        setId(id);
        setNombre(nombre);
        setCodigoRol(codigoRol);
        setContrasena(contrasena);
    }

    // Constructor para creación sin ID (dejando que la DB lo genere)
    public static UsuarioDomain crearParaRegistro(final String nombre, final UUID codigoRol, final String contrasena) {
        return new UsuarioDomain(null, nombre, codigoRol, contrasena);
    }

    public static UsuarioDomain obtenerValorDefecto() {
        return new UsuarioDomain();
    }

    public static UsuarioDomain obtenerValorDefecto(final UsuarioDomain usuario) {
        return (usuario != null) ? usuario : obtenerValorDefecto();
    }

    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        this.id = UtilUUID.obtenerValorDefecto(id);
    }

    public String getNombre() {
        return nombre;
    }

    private void setNombre(final String nombre) {
        this.nombre = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(nombre);
    }

    public UUID getCodigoRol() {
        return codigoRol;
    }

    private void setCodigoRol(final UUID codigoRol) {
        this.codigoRol = UtilUUID.obtenerValorDefecto(codigoRol);
    }

    public String getContrasena() {
        return contrasena;
    }

    private void setContrasena(final String contrasena) {
        this.contrasena = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(contrasena);
    }
}
