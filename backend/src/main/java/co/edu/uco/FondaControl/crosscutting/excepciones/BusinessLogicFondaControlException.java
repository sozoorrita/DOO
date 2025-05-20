package co.edu.uco.FondaControl.crosscutting.excepciones;

public class BusinessLogicFondaControlException extends FondaControlException {

	private static final long serialVersionUID = 6950855047355313810L;

	private BusinessLogicFondaControlException(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		super(mensajeUsuario, mensajeTecnico, excepcionRaiz, LayerException.BUSINESS_LOGIC);
	}
	
	public static BusinessLogicFondaControlException reportar(String mensajeUsuario) {
		return new BusinessLogicFondaControlException(mensajeUsuario, mensajeUsuario, new Exception());
	}
	
	public static BusinessLogicFondaControlException reportar(String mensajeUsuario, String mensajeTecnico) {
		return new BusinessLogicFondaControlException(mensajeUsuario, mensajeTecnico, new Exception());
	}
	
	public static BusinessLogicFondaControlException reportar(String mensajeUsuario, String mensajeTecnico, Exception excepcionRaiz) {
		return new BusinessLogicFondaControlException(mensajeUsuario, mensajeTecnico, excepcionRaiz);
	}
}
