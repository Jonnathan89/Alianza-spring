/**
 * 
 */
package com.micro.alianza.client.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author psych
 *
 */
public class AlianzaException extends ResponseStatusException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8009432558730348526L;

	public AlianzaException(HttpStatusCode status) {
		super(status);
		// TODO Auto-generated constructor stub
	}

	public AlianzaException(HttpStatusCode status, String reason, Throwable cause) {
		super(status, reason, cause);
		// TODO Auto-generated constructor stub
	}

	protected AlianzaException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode,
			Object[] messageDetailArguments) {
		super(status, reason, cause, messageDetailCode, messageDetailArguments);
		// TODO Auto-generated constructor stub
	}

	public AlianzaException(HttpStatusCode status, String reason) {
		super(status, reason);
		// TODO Auto-generated constructor stub
	}

	public AlianzaException(int rawStatusCode, String reason, Throwable cause) {
		super(rawStatusCode, reason, cause);
		// TODO Auto-generated constructor stub
	}

}
