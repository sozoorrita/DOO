package co.edu.uco.FondaControl.crosscutting.excepciones;

import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilObjeto;
import co.edu.uco.FondaControl.crosscutting.utilitarios.UtilTexto;

public class FondaControlException extends Exception {

	private static final long serialVersionUID = 3761526893427066183L;

	private String mensajeUsuario;
	private String mensajeTecnico;
	private LayerException capa;

	public FondaControlException(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz, LayerException capa) {
		super(mensajeTecnico, excepcionRaiz);
		setMensajeUsuario(mensajeUsuario);
		setMensajeTecnico(mensajeTecnico);
		setCapa(capa);
	}

	public LayerException getCapa() {
		return capa;
	}

	private void setCapa(LayerException capa) {
		this.capa = capa;
	}

	public String getMensajeUsuario() {
		return mensajeUsuario;
	}

	private void setMensajeUsuario(String mensajeUsuario) {
		this.mensajeUsuario = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(mensajeUsuario);
	}

	public String getMensajeTecnico() {
		return mensajeTecnico;
	}

	private void setMensajeTecnico(String mensajeTecnico) {
		this.mensajeTecnico = UtilTexto.getInstancia().quitarEspaciosBlancoInicioFin(mensajeTecnico);
	}

	public Throwable getExcepcionRaiz() {
		return UtilObjeto.getInstancia().obtenerValorDefecto(getCause(), new Exception(getMensajeUsuario()));
	}
}
