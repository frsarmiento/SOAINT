/*
 * #%L
 * Wildfly Camel :: Example :: Camel Rest Swagger
 * %%
 * Copyright (C) 2013 - 2017 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package co.gov.ramajudicial.eap.rest;

import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.dataformat.XmlJsonDataFormat;

import com.fasterxml.jackson.core.JsonParseException;


@SuppressWarnings("deprecation")
@ApplicationScoped
@ContextName("camel-rest-context")
public class RestRouteBuilder extends RouteBuilder {
	
	@Inject
	CamelContext context;
	
	
	public void configure() throws Exception {

		XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();
		xmlJsonFormat.setEncoding("UTF-8");
		xmlJsonFormat.setForceTopLevelObject(true);
		xmlJsonFormat.setTrimSpaces(true);
		xmlJsonFormat.setRootName("root");

		xmlJsonFormat.setSkipNamespaces(true);
		xmlJsonFormat.setRemoveNamespacePrefixes(true);
		xmlJsonFormat.setExpandableProperties(Arrays.asList("d", "e"));
		;

		PropertiesComponent pc = new PropertiesComponent();
		pc.setLocation("classpath:application.properties");
		context.addComponent("properties", (Component) pc);

		/**
		 * Configure an error handler to trap instances where the data posted to the
		 * REST API is invalid
		 */
		onException(JsonParseException.class).handled(true).setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
				.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.TEXT_PLAIN)).setBody()
				.constant("Invalid json data");

		/**
		 * Configure Camel REST to use the camel-undertow component
		 */
		restConfiguration()
				/// .bindingMode(RestBindingMode.json)
				.component("undertow").contextPath("rest/api").enableCORS(true)
				.apiProperty("api.title", "RUES Camel REST API").apiProperty("api.version", "1.0")
				.apiContextPath("swagger");

		/**
		 * Configure REST API with a base path of /customers
		 */
		rest("/uariv").description("servicio  Rest RUES  ")

				.get("/ConsultaRuv/{param1}/{param2}/{param3}/{param4}/{param5}/{param6}/{param7}")
				.description("ConsultaRuv: Retornar el RUV ").produces(MediaType.APPLICATION_JSON)
				.to("direct:ConsultaRuv")

				.get("/ConsultaRuvHechos/{param1}/{param2}/{param3}/{param4}/{param5}/{param6}/{param7}")
				.description("ConsultaRuvHechos: Retornar el RUV ").produces(MediaType.APPLICATION_JSON)
				.to("direct:ConsultaRuvHechos")

				.get("/ConsultaRuvGrupoFamiliar/{param1}/{param2}/{param3}/{param4}/{param5}/{param6}/{param7}")
				.description("ConsultaRuvGrupoFamiliar: Retornar el RUV ").produces(MediaType.APPLICATION_JSON)
				.to("direct:ConsultaRuvGrupoFamiliar")
				
				.get("/AutenticarUsuario/{param1}/{param2}/{param3}")
				.description("AutenticarUsuario: Retornar el RUV ").produces(MediaType.APPLICATION_JSON)
				.to("direct:AutenticarUsuario")
				
				.get("/CerrarSesion/{param1}/{param2}/{param3}")
				.description("CerrarSesion: Retornar el RUV ").produces(MediaType.APPLICATION_JSON)
				.to("direct:CerrarSesion");
				
		
		/** CerrarSesion */
		from("direct:CerrarSesion").log("get Inicio servicio CerrarSesion !")
				.toD("{{url.CerrarSesion}}/${header.param1},${header.param2},${header.param3}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.marshal(xmlJsonFormat).convertBodyTo(String.class);
		
		/** AutenticarUsuario */
		from("direct:AutenticarUsuario").log("get Inicio servicio AutenticarUsuario !")
				//.log("URL : {{url.AutenticarUsuario}}/${header.param1},${header.param2},${header.param3}")
				.toD("{{url.AutenticarUsuario}}/${header.param1},${header.param2},${header.param3}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.marshal(xmlJsonFormat).convertBodyTo(String.class);

		/** ConsultaRuvGrupoFamiliar */
		from("direct:ConsultaRuvGrupoFamiliar").log("get Inicio servicio ConsultaRuvGrupoFamiliar !")
				.toD("{{url.ConsultaRuvGrupoFamiliar}}/${header.param1},${header.param2},${header.param3},${header.param4},${header.param5},${header.param6},${header.param7}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.marshal(xmlJsonFormat).convertBodyTo(String.class);

		/** ConsultaRuvDocumento */
		from("direct:ConsultaRuv").log("get Inicio servicio ConsultaRuv !")
				//.log("URL : {{url.ConsultaRuv}}/${header.param1},${header.param2},${header.param3},${header.param4},${header.param5},${header.param6},${header.param7}")
				.toD("{{url.ConsultaRuv}}/${header.param1},${header.param2},${header.param3},${header.param4},${header.param5},${header.param6},${header.param7}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.marshal(xmlJsonFormat).convertBodyTo(String.class);

		/** ConsultaRuvHechos */
		from("direct:ConsultaRuvHechos").log("get Inicio servicio ConsultaRuvHechos !")
				.toD("{{url.ConsultaRuvHechos}}/${header.param1},${header.param2},${header.param3},${header.param4},${header.param5},${header.param6},${header.param7}?bridgeEndpoint=true&throwExceptionOnFailure=false")
				.marshal(xmlJsonFormat).convertBodyTo(String.class);

	}
}
