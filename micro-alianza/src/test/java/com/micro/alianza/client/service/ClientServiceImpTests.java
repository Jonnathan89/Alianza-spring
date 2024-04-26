/**
 * 
 */
package com.micro.alianza.client.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.server.ResponseStatusException;

import com.micro.alianza.client.dto.ClientDto;
import com.micro.alianza.client.entity.Client;
import com.micro.alianza.client.repository.ClientRepository;

/**
 * @author psych
 *
 */
@SpringBootTest
public class ClientServiceImpTests {

	@MockBean
	private ClientRepository repository;

	@MockBean
	private ModelMapper mapper;

	@Autowired
	private ClientServiceImp service;

	private ArrayList<ClientDto> lst = new ArrayList<>();
	private List<Client> lstN = new ArrayList<>();

	private ClientDto cli = new ClientDto();

	private Client cliE = new Client();

	private Optional<Client> opt;

	@BeforeEach
	void setTestData() {

		cli.setSharedKey("jalvarez");
		cli.setBussinesId("Juan Alvarez");
		cli.setEmail("juan.@gmail.com");
		cli.setPhone(new Long("54353453455"));
		cli.setDateAdded(new Date("2024/04/07"));
		lst.add(cli);

		cliE.setSharedKey("jalvarez");
		cliE.setBussinesId("Juan Alvarez");
		cliE.setEmail("juan.@gmail.com");
		cliE.setPhone(new Long("54353453455"));
		cliE.setDateAdded(new Date("2024/04/07"));
		lstN.add(cliE);

		opt = Optional.of(cliE);

	}

	@Test
	@DisplayName("Test get all clients Success")
	void testGetAllClient() throws Exception {
		when(repository.findAll()).thenReturn(this.lstN);
		lst = (ArrayList<ClientDto>) this.service.getAllClient();
		assertNotNull(lstN);
		assertEquals("jalvarez", cli.getSharedKey());
	}

	@Test
	@DisplayName("Test get all clients Exception")
	void testGetAllClientError() throws Exception {
		Exception exception = new RuntimeException("Internal error");
		when(repository.findAll()).thenThrow(exception);
		Assertions.assertThrows(Exception.class, () -> {
			this.service.getAllClient();
		});
	}

	@Test
	@DisplayName("Test get all clients Exception")
	void testGetAllClientErrorDu() throws DuplicateKeyException {
		DuplicateKeyException exception = new DuplicateKeyException("Internal error");
		when(repository.findAll()).thenThrow(exception);
		Assertions.assertThrows(Exception.class, () -> {
			this.service.getAllClient();
		});
	}

	@Test
	@DisplayName("Test create clients Success")
	void testCreateClient() throws Exception {
		when(repository.save(this.cliE)).thenReturn(cliE);
		this.service.createClient(cli);
		;
		assertNotNull(cliE);
		assertEquals("jalvarez", cliE.getSharedKey());
	}

	@Test
	@DisplayName("Test create clients Failed")
	void testCreateClientFailer() throws Exception {
		Exception exception = new RuntimeException("Internal error");
		when(mapper.map(cli, Client.class)).thenThrow(exception);
		Assertions.assertThrows(Exception.class, () -> {
			this.service.createClient(cli);
			;
		});
	}

	@Test
	@DisplayName("Test create clients Failed Duplicate")
	void testCreateClientFailerD() throws DuplicateKeyException {
		when(repository.findById(anyString())).thenReturn(Optional.of(new Client()));
		assertThrows(ResponseStatusException.class, () -> this.service.createClient(cli));

	}

	@Test
	@DisplayName("Test get id clients Success")
	void testGetIdClient() throws Exception {
		when(repository.findById(anyString())).thenReturn(Optional.of(new Client()));
		ClientDto dto = mapper.map(this.opt.get(), ClientDto.class);
		this.service.getIdClient(anyString());
		assertNotNull(this.lst);
		assertEquals("jalvarez", cli.getSharedKey());
	}

	@Test
	@DisplayName("Test get id clients Exception")
	void testGetIdClientError() throws Exception {

		when(repository.findById(anyString())).thenThrow(new RuntimeException());
		assertThrows(ResponseStatusException.class, () -> {
			service.getIdClient("anyKey");
		});
	}

	@Test
	@DisplayName("Test get id clients not found")
	void testGetIdClientNull() throws Exception {
		when(this.repository.findById(anyString())).thenReturn(Optional.empty());
		assertThrows(ResponseStatusException.class, () -> {
			service.getIdClient("anyKey");
		});

	}

	@Test
	@DisplayName("Test getIdClient - DTO not null")
	void testGetIdClient_DtoNotNull() {

		ClientDto dto = new ClientDto();
		dto.setSharedKey("sharedKey");

		Client dtoC = new Client();
		dto.setSharedKey("sharedKey");

		when(this.repository.findById("sharedKey")).thenReturn(Optional.of(new Client()));

		when(this.mapper.map(dtoC,(ClientDto.class))).thenReturn(dto);

		ArrayList<ClientDto> result = this.service.getIdClient("sharedKey");

		assertNotNull(result);

		assertEquals(1, result.size());

		assertEquals(dto, result.get(0));
	}

}
