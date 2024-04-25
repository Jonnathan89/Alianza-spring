/**
 * 
 */
package com.micro.alianza.client.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author psych
 *
 */

@Data
public class ClientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712826143157978895L;
	
	@JsonProperty("shared_key")
	@NotNull(message = "")
	private String sharedKey;
	
	
	@JsonProperty("bussines_id")
	private String bussinesId;
	
	@Email
	private String email;
	
	
	private long phone ;
	
	@JsonFormat(pattern="yyyy/MM/dd")
	@JsonProperty("date_added")
	private Date dateAdded;

}
