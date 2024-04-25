/**
 * 
 */
package com.micro.alianza.client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.alianza.client.dto.ClientDto;
import com.micro.alianza.client.service.ClientService;
import com.micro.alianza.client.util.Constants;

import jakarta.validation.Valid;

/**
 * @author psych
 *
 */
@CrossOrigin
@RestController
@RequestMapping("client/v1")
public class ClientController {
	
	@Autowired
	private ClientService service;
	
	@PostMapping(value = "/create-client", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@Valid @RequestBody ClientDto client) {
		service.createClient(client);
		return new ResponseEntity<>(Constants.CLIENT_SUCCESSFUL.getValueString() ,HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/get-all-client", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAllClient() {
		List<ClientDto> lst = service.getAllClient();
		return new ResponseEntity<>(lst ,HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/client-id", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> clientId(@RequestParam String shared) {
		List<ClientDto> lst = service.getIdClient(shared);
		return new ResponseEntity<>(lst ,HttpStatus.CREATED);
	}

	
}
