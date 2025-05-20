package co.edu.uco.FondaControl.crosscutting.excepciones;

public class ApiFondaControlException extends FondaControlException {

	private static final long serialVersionUID = 6950855047355313810L;

	private ApiFondaControlException(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz, LayerException.CROSSCUTTING);
	}
	
	public static ApiFondaControlException reportar(String mensajeUsuario) {
		return new ApiFondaControlException(mensajeUsuario, mensajeUsuario, new Exception());
	}
	
	public static ApiFondaControlException reportar(String mensajeUsuario, String mensajeTecnico) {
		return new ApiFondaControlException(mensajeUsuario, mensajeTecnico, new Exception());
	}
	
	public static ApiFondaControlException reportar(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new ApiFondaControlException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
