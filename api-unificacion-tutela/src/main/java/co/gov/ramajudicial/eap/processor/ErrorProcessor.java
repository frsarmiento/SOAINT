package co.gov.ramajudicial.eap.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import co.gov.ramajudicial.eap.model.Mensaje;


/**
 * @author yjmarquez
 * Clase que permite obtener los mensajes del error que se presenta y organizarlo para que se pueda mostrar al cliente (En caso que ocurran)
 */
public class ErrorProcessor implements Processor {

	
	/**
	 * Metodo que sobre escribe la excepcion para poder formatearla y poder mostrarse mejor o personalizado
	 */
	public void process(Exchange exchange) throws Exception {
		// TODO Auto-generated method stub
		
		Mensaje mensaje = new Mensaje();
		mensaje.setCodigo((String)exchange.getProperty("codigo"));
		mensaje.setMensaje((String)exchange.getProperty("mensaje"));
		mensaje.setDescripcion((String)exchange.getProperty("descripcion"));
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 500);
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
		exchange.getIn().setBody(mensaje);
	}

}
