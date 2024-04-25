/**
 * 
 */
package com.micro.alianza.client.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/**
 * @author psych
 *
 */
@Entity
@Table(name = "clients")
@Data
public class Client {

	@Id
	@Column(name = "shared_key")
	private String sharedKey;
	
	@Column(unique = true , name = "bussines_id")
	private String bussinesId;
	
	@Column(unique = true)
	private String email;
	
	private long phone ;
	
	@Column(name = "date_added")
	private Date dateAdded;
		
		
	
	
}
