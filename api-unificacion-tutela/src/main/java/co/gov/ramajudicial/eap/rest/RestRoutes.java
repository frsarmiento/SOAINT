package co.gov.ramajudicial.eap.rest;

import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.http4.HttpOperationFailedException;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;

import co.gov.ramajudicial.eap.exception.TutelaException;
import co.gov.ramajudicial.eap.processor.ErrorProcessor;
import co.gov.ramajudicial.eap.processor.ErrorTutelaProcessor;
import co.gov.ramajudicial.eap.processor.ResponseProcessor;

/**
 * @author yjmarquez Clase en el que se define: 1. la configuración de las rutas
 *         de los APIs y la configuración correspondiente de cada API. 2. Se
 *         define la ruta de respuesta de los servicios REST y que método
 *         responde, así como el modo de respuesta (en nuestro caso JSON) 3. Se
 *         define en que orden se ejecutan sentencias, query, conexión BD y
 *         escritura los Logs
 *
 */
@ContextName("tutela-context")
public class RestRoutes extends RouteBuilder {

	@Inject
	CamelContext context;
	ErrorTutelaProcessor errorTutelaProcessor = new ErrorTutelaProcessor();

	ResponseProcessor responseProcessor = new ResponseProcessor();

	ErrorProcessor error = new ErrorProcessor();

	@Override
	public void configure() throws Exception {
		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:application.properties");
		context.addComponent("properties", (Component) pc);

		restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

		rest("/api")
		
				//GET
				.get("/Envio/ConsultarTutelaCUI").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTutelaCUI")
				
				.get("/Lista/ConsultarEstadoId").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarEstadoId")
				
				.get("/Lista/ConsultarEstado").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarEstado")
				
				.get("/Lista/ConsultarInstancia").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarInstancia")
				
				.get("/Lista/ConsultarDepartamento").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarDepartamento")
				
				.get("/Lista/ConsultarCiudad").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarCiudad")
				
				.get("/Lista/ConsultarDespacho").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarDespacho")
				
				.get("/Lista/ConsultarDerecho").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarDerecho")
				
				.get("/Lista/ConsultarTipoPersona").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTipoPersona")
				
				.get("/Lista/ConsultarRetenciones").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarRetenciones")
				
				.get("/Lista/ConsultarTipoArchivos").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTipoArchivos")
				
				.get("/Lista/ConsultarTipoDocumentos").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTipoDocumentos")
				
				.get("/Lista/ConsultarSerie").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarSerie")
				
				.get("/Lista/ConsultarSubSerie").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarSubSerie")
				
				.get("/Notificacion/ConsultarNotificacion").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarNotificacion")
				
				.get("/Tutela/ConsultarTrazabilidadEstadoTutelaSiicor").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTrazabilidadEstadoTutelaSiicor")
				
				.get("/Tutela/ConsultarDemandanteTutela").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarDemandanteTutela")
						
				//POST
				.post("/Envio/ActualizarEstadoTutelaLinea").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ActualizarEstadoTutelaLinea")
		
				.post("/Envio/EnviarTutelaCorteConstitucional").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:EnviarTutelaCorteConstitucional")
				
				.post("/Envio/ActualizarEstadoTutelaCUI").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ActualizarEstadoTutelaCUI")
				
				.post("/Envio/RetiroExpediente").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:RetiroExpediente")
		
				.post("/Envio/SolicitudRetiroExpediente").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:SolicitudRetiroExpediente")
				
				.post("/Notificacion/ActualizarEstadoNotificacion").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ActualizarEstadoNotificacion")
				
				.post("/Notificacion/AdicionarNotificaciones").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:AdicionarNotificaciones")
		
				.post("/Trazabilidad/ConsultarTraza").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTraza")
				
				.post("/Trazabilidad/HacerTrazabilidadTutela").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:HacerTrazabilidadTutela")
				
				.post("/Tutela/ConsultarTutela").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:ConsultarTutela")
				
				.post("/Tutela/CrearRadicacionTutela").description("POR ACTUALIZAR.").produces("application/json")
				.to("direct:CrearRadicacionTutela");
				
		// Definicion de las rutas
		from("direct:ConsultarTutelaCUI").log("GET Inicio servicio ConsultarTutelaCUI !")

			.to("{{url.ConsultarTutelaCUI}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
			.convertBodyTo(String.class)
 
			.choice()
	
				.when().simple("${header.CamelHttpResponseCode} != 200")
	            .process(responseProcessor)
	          	.otherwise()
	            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
			
		
		//Lista
		//Consultar Estado ID
		from("direct:ConsultarEstadoId").log("GET Inicio servicio ConsultarEstadoId !")

				.to("{{url.ConsultarEstadoId}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//Consultar Estado
		from("direct:ConsultarEstado").log("GET Inicio servicio ConsultarEstado !")

				.to("{{url.ConsultarEstado}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//Consultar Instancia
		from("direct:ConsultarInstancia").log("GET Inicio servicio ConsultarInstancia !")

				.to("{{url.ConsultarInstancia}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarDepartamento
		from("direct:ConsultarDepartamento").log("GET Inicio servicio ConsultarDepartamento !")

				.to("{{url.ConsultarDepartamento}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarCiudad
		from("direct:ConsultarCiudad").log("GET Inicio servicio ConsultarCiudad !")

				.to("{{url.ConsultarCiudad}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarDespacho
		from("direct:ConsultarDespacho").log("GET Inicio servicio ConsultarDespacho !")

				.to("{{url.ConsultarDespacho}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarDerecho
		from("direct:ConsultarDerecho").log("GET Inicio servicio ConsultarDerecho !")

				.to("{{url.ConsultarDerecho}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarTipoPersona
		from("direct:ConsultarTipoPersona").log("GET Inicio servicio ConsultarTipoPersona !")

				.to("{{url.ConsultarTipoPersona}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarRetenciones
		from("direct:ConsultarRetenciones").log("GET Inicio servicio ConsultarRetenciones !")

				.to("{{url.ConsultarRetenciones}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
				
		//ConsultarTipoArchivos 
		from("direct:ConsultarTipoArchivos").log("GET Inicio servicio ConsultarTipoArchivos !")

				.to("{{url.ConsultarTipoArchivos}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarTipoDocumentos
		from("direct:ConsultarTipoDocumentos").log("GET Inicio servicio ConsultarTipoDocumentos !")

				.to("{{url.ConsultarTipoDocumentos}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarSerie
		from("direct:ConsultarSerie").log("GET Inicio servicio ConsultarSerie !")

				.to("{{url.ConsultarSerie}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarSubSerie
		from("direct:ConsultarSubSerie").log("GET Inicio servicio ConsultarSubSerie !")

				.to("{{url.ConsultarSubSerie}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//Notificacion
		//ConsultarNotificacion
		from("direct:ConsultarNotificacion").log("GET Inicio servicio ConsultarNotificacion !")

				.to("{{url.ConsultarNotificacion}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//Tutela
		//ConsultarTrazabilidadEstadoTutelaSiicor
		from("direct:ConsultarTrazabilidadEstadoTutelaSiicor").log("GET Inicio servicio ConsultarTrazabilidadEstadoTutelaSiicor !")

				.to("{{url.ConsultarTrazabilidadEstadoTutelaSiicor}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
		//ConsultarDemandanteTutela
		from("direct:ConsultarDemandanteTutela").log("GET Inicio servicio ConsultarDemandanteTutela !")

				.to("{{url.ConsultarDemandanteTutela}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.convertBodyTo(String.class)
				 
				.choice()

					.when().simple("${header.CamelHttpResponseCode} != 200")
		            .process(responseProcessor)
		          	.otherwise()
		            .unmarshal().json(JsonLibrary.Jackson).setHeader(Exchange.CONTENT_TYPE, constant("application/json"));
		
				
		//POST 
		//ActualizarEstadoTutelaLinea
		from("direct:ActualizarEstadoTutelaLinea").log("POST Inicio servicio ActualizarEstadoTutelaLinea ! ${body}")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.ActualizarEstadoTutelaLinea}}?bridgeEndpoint=true&throwExceptionOnFailure=false")

			     .choice()
			     
					.when().simple("${header.CamelHttpResponseCode} != 200")
					.convertBodyTo(String.class)
					.process(responseProcessor)
					.otherwise()
					.unmarshal().json(JsonLibrary.Jackson);
				
						
		//EnviarTutelaCorteConstitucional
		from("direct:EnviarTutelaCorteConstitucional").log("POST Inicio servicio EnviarTutelaCorteConstitucional !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.EnviarTutelaCorteConstitucional}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
			     .choice()
			     
					.when().simple("${header.CamelHttpResponseCode} != 200")
					.convertBodyTo(String.class)
					.process(responseProcessor)
					.otherwise()
					.unmarshal().json(JsonLibrary.Jackson);
			
		//ActualizarEstadoTutelaCUI
		from("direct:ActualizarEstadoTutelaCUI").log("POST Inicio servicio ActualizarEstadoTutelaCUI !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.ActualizarEstadoTutelaCUI}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//RetiroExpediente
		from("direct:RetiroExpediente").log("POST Inicio servicio RetiroExpediente !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.RetiroExpediente}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//SolicitudRetiroExpediente
		from("direct:SolicitudRetiroExpediente").log("POST Inicio servicio SolicitudRetiroExpediente !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.SolicitudRetiroExpediente}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//ActualizarEstadoNotificacion
		from("direct:ActualizarEstadoNotificacion").log("POST Inicio servicio ActualizarEstadoNotificacion !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.ActualizarEstadoNotificacion}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//AdicionarNotificaciones
		from("direct:AdicionarNotificaciones").log("POST Inicio servicio AdicionarNotificaciones !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.AdicionarNotificaciones}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//ConsultarTraza
		from("direct:ConsultarTraza").log("POST Inicio servicio ConsultarTraza !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.ConsultarTraza}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//HacerTrazabilidadTutela
		from("direct:HacerTrazabilidadTutela").log("POST Inicio servicio HacerTrazabilidadTutela !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.HacerTrazabilidadTutela}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//ConsultarTutela
		from("direct:ConsultarTutela").log("POST Inicio servicio ConsultarTutela !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.ConsultarTutela}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
		
		//CrearRadicacionTutela
		from("direct:CrearRadicacionTutela").log("POST Inicio servicio CrearRadicacionTutela !")
				.marshal().json(JsonLibrary.Jackson)
				.to("{{url.CrearRadicacionTutela}}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				
				.choice()
			     
				.when().simple("${header.CamelHttpResponseCode} != 200")
				.convertBodyTo(String.class)
				.process(responseProcessor)
				.otherwise()
				.unmarshal().json(JsonLibrary.Jackson);
	
				
	}
}
