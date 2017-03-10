package mx.com.ejemplo.quartz.ws.client;

import java.util.Map;

import org.springframework.http.ResponseEntity;


public class LlamadoMensajeria {
	@SuppressWarnings({ "rawtypes", "unused" })
	public String llamadoServicioWeb() {
		String mensaje = "Mensaje1";
		String tipo = "";
		String url = "https://android.googleapis.com/gcm/send";
		String jsonParam = "{"
							+ "\"Notice\": \""  	+ mensaje + "\","
							+ "\"title\":   \"" 	+ "App Enfoque Creativo" + "\","
							+ "\"type\":   \""  	+  tipo + "\","
							+ "\"vibrate\":   \""   +  "1" + "\","
							+ "\"sound\":   \""  	+  "1" + "\","
						    + "}";
		
		ResponseEntity<?> response = UtilsRequest.doRequest(jsonParam, url, 2);
		Map rp = (Map) response.getBody();
		
		return "Llamada al Metodo llamadoServicioWeb()";
	}
}
