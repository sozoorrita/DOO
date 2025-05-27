package co.edu.uco.FondaControl.dto;

import java.util.UUID;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilUUID;

public final class DetalleVentaDTO {
    private UUID codigo;
    private String nombreProducto;
    private double precioAplicado;
    private int cantidad;

    private DetalleVentaDTO() {
        setCodigo(UtilUUID.obtenerValorDefecto());
        setNombreProducto(UtilTexto.getInstancia().obtenerValorDefecto());
        setPrecioAplicado(0.0);
        setCantidad(0);
    }

    private DetalleVentaDTO(final Builder builder) {
        setCodigo(builder.codigo);
        setNombreProducto(builder.nombreProducto);
        setPrecioAplicado(builder.precioAplicado);
        setCantidad(builder.cantidad);
    }

    public static DetalleVentaDTO obtenerValorDefecto() {
        return builder().crear();
    }

    public static DetalleVentaDTO obtenerValorDefecto(final DetalleVentaDTO entidad) {
        return UtilObjeto.getInstancia().obtenerValorDefecto(entidad, obtenerValorDefecto());
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(final UUID codigo) {
        this.codigo = UtilUUID.obtenerValorDefecto(codigo);
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(final String nombreProducto) {
        String valor = UtilTexto.getInstancia().obtenerValorDefecto(nombreProducto);
        this.nombreProducto = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(valor);
    }

    public double getPrecioAplicado() {
        return precioAplicado;
    }

    public void setPrecioAplicado(final double precioAplicado) {
        this.precioAplicado = precioAplicado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UUID codigo = UtilUUID.obtenerValorDefecto();
        private String nombreProducto = UtilTexto.getInstancia().obtenerValorDefecto();
        private double precioAplicado = 0.0;
        private int cantidad = 0;

        private Builder() {}

        public Builder codigo(final UUID codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder nombreProducto(final String nombreProducto) {
            this.nombreProducto = nombreProducto;
            return this;
        }

        public Builder precioAplicado(final double precioAplicado) {
            this.precioAplicado = precioAplicado;
            return this;
        }

        public Builder cantidad(final int cantidad) {
            this.cantidad = cantidad;
            return this;
        }

        public DetalleVentaDTO crear() {
            return new DetalleVentaDTO(this);
        }

		public Builder codigoDetalleVenta(UUID codigoDetalleVenta) {
			// TODO Auto-generated method stub
			return null;
		}
    }

	public UUID getCodigoDetalleVenta() {
		// TODO Auto-generated method stub
		return null;
	}
}
