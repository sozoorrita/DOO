package co.edu.uco.FondaControl.crosscutting.excepciones;

public class CrossCuttingFondaControlException extends FondaControlException {

	private static final long serialVersionUID = 6950855047355313810L;

	private CrossCuttingFondaControlException(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz, LayerException.CROSSCUTTING);
	}
	
	public static CrossCuttingFondaControlException reportar(String mensajeUsuario) {
		return new CrossCuttingFondaControlException(mensajeUsuario, mensajeUsuario, new Exception());
	}
	
	public static CrossCuttingFondaControlException reportar(String mensajeUsuario, String mensajeTecnico) {
		return new CrossCuttingFondaControlException(mensajeUsuario, mensajeTecnico, new Exception());
	}
	
	public static CrossCuttingFondaControlException reportar(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new CrossCuttingFondaControlException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
