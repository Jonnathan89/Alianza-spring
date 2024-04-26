/**
 * 
 */
package com.micro.alianza.client.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.micro.alianza.client.dto.ClientDto;
import com.micro.alianza.client.service.ClientServiceImp;
import com.micro.alianza.client.util.Constants;

/**
 * @author psych
 *
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClientControllerTests {

	private static final String HOST = "http://localhost:";
	private static final String SERVICE = "/client/v1/";

	@LocalServerPort
	private int port;

	private String url;

	@Autowired
	private TestRestTemplate restTemplate;

	private ArrayList<ClientDto> lst = new ArrayList<>();

	private List<ClientDto> lstC = new ArrayList<>();

	private ClientDto cli = new ClientDto();

	@MockBean
	private ClientServiceImp service;

	@MockBean
	private ModelMapper mapperMock;

	@BeforeEach
	void setTestData() {

		cli.setSharedKey("jalvarez");
		cli.setBussinesId("Juan Alvarez");
		cli.setEmail("juan.@gmail.com");
		cli.setPhone(new Long("54353453455"));
		cli.setDateAdded(new Date("2024/04/07"));
		lst.add(cli);
		lstC.add(cli);
	}

	@Test
	@DisplayName("Test get all client Success")
	void testGetAllCourses() throws Exception {
		this.url = new URL(HOST + port + SERVICE + "get-all-client").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		doReturn(this.lstC).when(service).getAllClient();

		ResponseEntity<List<ClientDto>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ClientDto>>() {
				});

		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
		Assertions.assertEquals("jalvarez", response.getBody().get(0).getSharedKey());
		Assertions.assertEquals("juan.@gmail.com", response.getBody().get(0).getEmail());
	}

	@Test
	@DisplayName("Test get all Client not found")
	void testGetAllCoursesNotFound() throws Exception {
		this.url = new URL(HOST + port + SERVICE + "get-all-client").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseStatusException exception = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				Constants.ERROR_GENERAL.getValueString());
		;

		doThrow(exception).when(this.service).getAllClient();

		

		Assertions.assertThrows(HttpMessageConversionException.class, () -> {
			restTemplate.getForEntity(builder.toUriString(),
					ResponseStatusException.class);
			});	
	}

	@Test
	@DisplayName("Test get id Client Success")
	void testGetClient() throws Exception {
		this.url = new URL(HOST + port + SERVICE + "client-id").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("shared","jalvarez");

	
		doReturn(this.lst).when(this.service).getIdClient("jalvarez");
		String urlFD = builder.toUriString();

		ResponseEntity<List<ClientDto>> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<ClientDto>>(){});

		Assertions.assertEquals("jalvarez", response.getBody().get(0).getSharedKey());
		Assertions.assertEquals("juan.@gmail.com", response.getBody().get(0).getEmail());
		
	}

	@Test
	@DisplayName("Test get id Client found")
	void testGetClientError() throws Exception {
		this.url = new URL(HOST + port + SERVICE + "shared").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("client-id", "c895cf1d");

		ResponseStatusException exception = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				Constants.ERROR_GENERAL.getValueString());		;

		doThrow(exception).when(this.service).getIdClient("c895cf1d");

		
		 Assertions.assertThrows(HttpMessageConversionException.class, () -> {
			 restTemplate.getForEntity(builder.toUriString(),
						ResponseStatusException.class);
			});	
		
	}

	@Test
	@DisplayName("Test create client  Success")
	void testCreateClient() throws Exception {		
	   
	    ClientDto clientDto = new ClientDto();
        clientDto.setSharedKey("ggggggggggggg");
        clientDto.setBussinesId("ggggggggggggggggggggg");
        clientDto.setEmail("example@example.com");
        clientDto.setPhone(1234567890);
        clientDto.setDateAdded(new Date()); 
		
		
		this.url = new URL(HOST + port + SERVICE + "create-client").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		doNothing().when(service).createClient(any(ClientDto.class));
		
		
		  ResponseEntity<?> responseEntity = restTemplate.exchange(
				    builder.toUriString(),
		            HttpMethod.POST,
		            new HttpEntity<>(clientDto),
		            Void.class // Since the server doesn't return any response body, use Void.class
		        );
		
		  Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

	}

	@Test
	@DisplayName("Test create client general error")
	void testCreateClientError() throws Exception {
		this.url = new URL(HOST + port + SERVICE + "create-client").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseStatusException exception = new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
				Constants.ERROR_GENERAL.getValueString());
		;

		doThrow(exception).when(this.service).createClient(any(ClientDto.class));
		
		
		 Assertions.assertThrows(HttpMessageConversionException.class, () -> {
			 this.restTemplate.postForEntity(builder.toUriString(), exception, 
						ResponseStatusException.class);
			});	
		
	}

	@Test
	@DisplayName("Test create client general error 2")
	void testCreateClientErrorDu() throws HttpMessageConversionException, MalformedURLException {
		this.url = new URL(HOST + port + SERVICE + "create-client").toString();

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		ResponseStatusException exception = new ResponseStatusException(HttpStatus.CONFLICT,
				Constants.ERROR_GENERAL.getValueString());
		;

		doThrow(exception).when(this.service).createClient(any(ClientDto.class));

	
		Assertions.assertThrows(HttpMessageConversionException.class, () -> {
			restTemplate.getForEntity(builder.toUriString(),
					ResponseStatusException.class);
			});	
	}
}
