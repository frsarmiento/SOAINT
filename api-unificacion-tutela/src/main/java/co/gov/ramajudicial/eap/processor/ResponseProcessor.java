package co.gov.ramajudicial.eap.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


/**
 * @author yjmarquez
 * Clase que permite obtener los mensajes del error que se presenta y organizarlo para que se pueda mostrar al cliente (En caso que ocurran)
 */
public class ResponseProcessor implements Processor {

	
	/**
	 * Metodo que sobre escribe la excepcion para poder formatearla y poder mostrarse mejor o personalizado
	 */
	public void process(Exchange exchange) throws Exception {
		
		Gson	gson =	new Gson();
		String cuerpo = (String) exchange.getIn().getBody();
     	JsonObject convertedObject = new Gson().fromJson(cuerpo, JsonObject.class);
	     exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
	     exchange.getIn().setBody(gson.toJson(convertedObject));
	}

}