package mx.com.ejemplo.quartz.ws.client;

import java.nio.charset.Charset;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Alejandrino Ortiz Martinez
 */
public class UtilsRequest {
	private static final String MSG_ENDPOINT = "Serv Priv. EndPoint: ";
	private static final String MSG_ACCEPT = "Accept";
	private static final String MSG_ENCODING = "application/json;charset=utf-8";
	private static final String UTF8_MSG = "UTF-8";
	private static final String API_ACCESS_KEY = "AIzaSyDPRL1DBVC_yARthGyWiiFGgtUbKT5EqiE";
	/**
	 * Public constructor
	 */
	private UtilsRequest() {
		super();

	}

	/**
	 * Configuracion de las cabeceras y ejecucion del request.
	 *
	 * @param param
	 * @param url
	 * @param metodo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ResponseEntity doRequest(String param, String url, int metodo) {

		ResponseEntity response;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.set("Authorization", API_ACCESS_KEY);

		switch (metodo) {
		case 1:// GET
			response = doGet(url, param, headers);
			break;
		case 2:// POST
			response = doPost(url, param, headers);
			break;
		case 3:// PUT
			response = doPut(url, param, headers);
			break;
		case 4:// DELETE
			response = doDelete(url, param, headers);
			break;	
		default:
			response = null;
			break;
		}

		if (response != null && "200".equals(response.getStatusCode().toString())) {
			return response;
		} else {
			return null;
		}

	}

	/**
	 * Ejecuta request GET.
	 *
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static ResponseEntity doGet(String url, String param, HttpHeaders headers) {
		RestTemplate restTemplate = new RestTemplate();
		
		System.out.println(MSG_ENDPOINT + url + "  method: GET  IN: " + param);
		
		HttpEntity entity = new HttpEntity<>("", headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, Object.class, param);
	}

	/**
	 * Ejecuta request POST.
	 *
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static ResponseEntity doPost(String url, String param, HttpHeaders headers) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(UTF8_MSG)));

		System.out.println(MSG_ENDPOINT + url + "  method: POST  IN: " + param);

		headers.add(MSG_ACCEPT, MSG_ENCODING);
		HttpEntity entity = new HttpEntity<>(param, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
	}

	/**
	 * Ejecuta Request Put.
	 *
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static ResponseEntity doPut(String url, String param, HttpHeaders headers) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(UTF8_MSG)));

		System.out.println(MSG_ENDPOINT + url + "  method: PUT IN: " + param);
		
		headers.add(MSG_ACCEPT, MSG_ENCODING);
		HttpEntity entity = new HttpEntity<>(param, headers);
		return restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
	}
	/**
	 * Ejecuta Request Delete.
	 * @param url
	 * @param param
	 * @param headers
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static ResponseEntity doDelete(String url, String param, HttpHeaders headers) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(UTF8_MSG)));

		System.out.println(MSG_ENDPOINT + url + "  method: DELETE IN: " + param);
		
		headers.add(MSG_ACCEPT, MSG_ENCODING);
		HttpEntity entity = new HttpEntity<>(param, headers);
		return restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
	}
	
	/**
	 * Convierte un objeto en String.
	 *
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj) {
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}

	}

	/**
	 * Parse una fecha a un formato determinado
	 *
	 * @param dateIn la fecha que sera parseada
	 * @param format  formato en que viene la fecha
	 *
	 * @return El string con la fecha que fue formateada
	 */
	public static String parseDate(String dateIn, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = "";
		try {
			Date date = formatter.parse(dateIn);
			Format f1 = new SimpleDateFormat("yyyy-MM-dd");
			strDate = f1.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	/**
	 * 
	 * @param dateIn
	 * @param format
	 * @return
	 */
	public static String parseDateDMY(String dateIn, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String strDate = "";
		try {
			Date date = formatter.parse(dateIn);
			Format formatter1 = new SimpleDateFormat("dd/MM/yyyy");
			strDate = formatter1.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return strDate;
	}
	/**
	 * 
	 * @param input
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean isInteger(String input) {
		try {
			Integer i = Integer.parseInt(input);			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Construye un mensaje de excepcion en la
	 * aplicaci�n
	 * 
	 * @param exception
	 * @return errorMsg.
	 */
	public static String exceptionMsgBuilder(Exception e) {
		String errorMsg = "";

		errorMsg += e.getMessage();

		if (e.getCause() != null) {
			errorMsg += " Cause: " + e.getCause().toString();
		}
		return errorMsg;
	}
}
