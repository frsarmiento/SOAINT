package co.gov.ramajudicial.eap.exception;


/**
 * @author yjmarquez
 * Clase que emula o mapea las excepciones correspondiente a la entidad Tutela, para mostrarla de forma organizada en la manera que defina
 */
public class TutelaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private String tipo;
	
	//Constructor
	public TutelaException(String msg, String tipo) {
		super(msg);
		this.message = msg;
		setTipo(tipo);
		
	}
	
	
	//Método Get del atributo message, para obtener el valor
	public String getMessage() {
		return message;
	}
	
	//Método Set del atributo message - recibe un String
	public void setMessage(String message) {
		this.message = message;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		
		if(tipo.isEmpty() ||  tipo.equals("") ) {
			this.tipo = "500";
		}else {
			this.tipo = tipo;
		}
		
	}
	
	
	
	
}
