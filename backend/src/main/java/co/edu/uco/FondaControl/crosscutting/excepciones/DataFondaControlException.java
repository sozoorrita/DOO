package co.edu.uco.FondaControl.crosscutting.excepciones;

public class DataFondaControlException extends FondaControlException {

	private static final long serialVersionUID = 6950855047355313810L;

	private DataFondaControlException(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz, LayerException.DATA);
	}
	
	public static DataFondaControlException reportar(String mensajeUsuario) {
		return new DataFondaControlException(mensajeUsuario, mensajeUsuario, new Exception());
	}
	
	public static DataFondaControlException reportar(String mensajeUsuario, String mensajeTecnico) {
		return new DataFondaControlException(mensajeUsuario, mensajeTecnico, new Exception());
	}
	
	public static DataFondaControlException reportar(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new DataFondaControlException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
