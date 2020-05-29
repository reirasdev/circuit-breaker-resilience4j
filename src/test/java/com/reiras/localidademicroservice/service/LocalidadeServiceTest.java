package com.reiras.localidademicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpServerErrorException;

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.ObjectNotFoundException;
import com.reiras.localidademicroservice.service.parser.ParserContentType;

@SpringBootTest
public class LocalidadeServiceTest {

	@Autowired
	private LocalidadeService localidadeService;

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndSiglaEstado_thenReturnIdCidade() {
		Localidade obj = localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj.getIdCidade(), 3304557);
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndValidSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "NON EXISTENT");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndNoExistingSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "Rio de Janeiro");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "NON EXISTENT");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndInvalidSiglaEstado_thenThrowHttpServerErrorException() {
		assertThrows(HttpServerErrorException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoAndNomeCidade("--", "Rio de Janeiro");
		});
	}

	@Test
	public void findLocalidadesBySiglaEstado_givenValidSiglaEstado_thenResturnListOfCities() {
		List<Localidade> obj = localidadeService.findLocalidadeBySiglaEstado("SC");
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
	}

	@Test
	public void findLocalidadesBySiglaEstado_givenNoExistingSiglaEstado_thenReturnEmptyList() {
		List<Localidade> obj = localidadeService.findLocalidadeBySiglaEstado("XX");
		assertNotNull(obj);
		assertTrue(obj.isEmpty());
	}

	@Test
	public void findLocalidadesBySiglaEstado_givenInvalidSiglaEstado_thenThrowHttpServerErrorException() {
		assertThrows(HttpServerErrorException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstado("--");
		});
	}
	
	@Test
	public void findLocalidadeBySiglaEstadoParseFileCsv_givenValidSiglaEstado_thenResturnListOfCities() throws IOException {
		InputStream csvStream = localidadeService.findLocalidadeBySiglaEstadoParseFile("PB", ParserContentType.CSV);
		assertNotNull(csvStream);
		
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertEquals(csvReader.readLine().split(",").length, 6);
		assertEquals(csvReader.readLine().split(",")[1], "PB");
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileCsv_givenNoExistingSiglaEstado_thenReturnEmptyList() throws IOException {
		InputStream csvStream = localidadeService.findLocalidadeBySiglaEstadoParseFile("XX", ParserContentType.CSV);
		assertNotNull(csvStream);
		
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertNull(csvReader.readLine());
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileCsv_givenInvalidSiglaEstado_thenThrowHttpServerErrorException() {
		assertThrows(HttpServerErrorException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoParseFile("--", ParserContentType.CSV);
		});
	}
	
	@Test
	public void findLocalidadeBySiglaEstadoParseFileJson_givenValidSiglaEstado_thenResturnListOfCities() throws IOException {
		InputStream jsonStream = localidadeService.findLocalidadeBySiglaEstadoParseFile("MG", ParserContentType.JSON);
		assertNotNull(jsonStream);
		
		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertTrue(jsonReader.readLine().contains("\"siglaEstado\":\"MG\""));
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileJson_givenNoExistingSiglaEstado_thenReturnEmptyList() throws IOException {
		InputStream jsonStream = localidadeService.findLocalidadeBySiglaEstadoParseFile("XX", ParserContentType.JSON);
		assertNotNull(jsonStream);
		
		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertEquals(jsonReader.readLine(), "[]");
		assertNull(jsonReader.readLine());
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileJson_givenInvalidSiglaEstado_thenThrowHttpServerErrorException() {
		assertThrows(HttpServerErrorException.class, () -> {
			localidadeService.findLocalidadeBySiglaEstadoParseFile("--", ParserContentType.JSON);
		});
	}

}
