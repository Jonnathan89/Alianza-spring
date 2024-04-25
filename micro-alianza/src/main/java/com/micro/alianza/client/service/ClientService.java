/**
 * 
 */
package com.micro.alianza.client.service;

import java.util.ArrayList;
import java.util.List;

import com.micro.alianza.client.dto.ClientDto;

/**
 * @author psych
 *
 */
public interface ClientService {

	
	public void createClient(ClientDto dto);
	
	public List<ClientDto> getAllClient ();
	
	public ArrayList<ClientDto> getIdClient ( String shared);
}
