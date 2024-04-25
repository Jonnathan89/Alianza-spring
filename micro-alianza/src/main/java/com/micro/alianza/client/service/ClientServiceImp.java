/**
 * 
 */
package com.micro.alianza.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.micro.alianza.client.dto.ClientDto;
import com.micro.alianza.client.entity.Client;
import com.micro.alianza.client.repository.ClientRepository;
import com.micro.alianza.client.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * @author psych
 *
 */
@Service
@Slf4j
public class ClientServiceImp implements ClientService {

	@Autowired
	private ClientRepository repository;

	@Autowired
	private ModelMapper mapper;

	/**
	 * Creates the client.
	 *
	 * @param dto the dto
	 */
	@Override
	public void createClient(ClientDto dto) {
		// TODO Auto-generated method stub
		try {
			Client clit = mapper.map(dto, Client.class);
			Optional<Client> client = repository.findById(dto.getSharedKey());
			if (client.isPresent()) {
				throw new DuplicateKeyException(Constants.EXIST_CLIENT.getValueString());
			}
			repository.save(clit);
		} catch (DuplicateKeyException e) {
			log.error("El sharedKey ya se encuentra registrado en la base de datos    "+ dto.getSharedKey(), e.getCause());
			throw new ResponseStatusException(HttpStatus.CONFLICT, Constants.EXIST_CLIENT.getValueString());
		} catch (Exception e) {
			log.error("      ", e.getCause());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.ERROR_GENERAL.getValueString());
		}

	}

	@Override
	public List<ClientDto> getAllClient() {
		// TODO Auto-generated method stub
		try {
			List<Client> lstCli = repository.findAll();			
			return  lstCli != null ?  lstCli.stream().map(clit -> mapper.map(clit, ClientDto.class)).collect(Collectors.toList()) : new ArrayList<>();
		} catch (Exception e) {
			log.error("      ", e.getCause());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.ERROR_GENERAL.getValueString());
		}
		
	}

	@Override
	public ArrayList<ClientDto> getIdClient( String shared) {
		// TODO Auto-generated method stub
		try {
			ArrayList<ClientDto> lst = new ArrayList<>() ;
			Optional<Client> cli= repository.findById(shared);
		    ClientDto dto = mapper.map(cli.get(), ClientDto.class);
		    if (dto != null) {
				lst.add(dto);
				return lst;
			}
			
		} catch (Exception e) {
			log.error("      ", e.getCause());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					Constants.ERROR_GENERAL.getValueString());
		}
		return null;
	}

}
