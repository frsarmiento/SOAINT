package co.gov.ramajudicial.eap.model;

/**
 * @author yjmarquez
 * Clase en que se define los atributos con que se piensa mapear los mensajes de Error Generales
 */
public class Mensaje {
	
	private String codigo;
	private String mensaje;
	private String descripcion;
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
