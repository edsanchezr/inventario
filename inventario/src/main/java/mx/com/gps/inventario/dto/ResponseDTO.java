package mx.com.gps.inventario.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8316165756874019587L;
	
	private String message;
	
	private Object detail;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getDetail() {
		return detail;
	}
	public void setDetail(Object detail) {
		this.detail = detail;
	}
}
