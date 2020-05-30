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

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.ObjectNotFoundException;
import com.reiras.localidademicroservice.service.parser.ParserContentType;

@SpringBootTest
public class LocalidadeServiceRestTest {

	@Autowired
	private LocalidadeService localidadeServiceRest;

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndSiglaEstado_thenReturnIdCidade() {
		Localidade obj = localidadeServiceRest.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj.getIdCidade(), 3304557);
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndValidSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeServiceRest.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "NON EXISTENT");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndNoExistingSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeServiceRest.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "Rio de Janeiro");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndSiglaEstado_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeServiceRest.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "NON EXISTENT");
		});
	}

	@Test
	public void findLocalidadesBySiglaEstado_givenValidSiglaEstado_thenResturnListOfCities() {
		List<Localidade> obj = localidadeServiceRest.findLocalidadeBySiglaEstado("SC");
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
	}

	@Test
	public void findLocalidadesBySiglaEstado_givenNoExistingSiglaEstado_thenReturnEmptyList() {
		List<Localidade> obj = localidadeServiceRest.findLocalidadeBySiglaEstado("XX");
		assertNotNull(obj);
		assertTrue(obj.isEmpty());
	}
	
	@Test
	public void findLocalidadeBySiglaEstadoParseFileCsv_givenValidSiglaEstado_thenResturnListOfCities() throws IOException {
		InputStream csvStream = localidadeServiceRest.findLocalidadeBySiglaEstadoParseFile("PB", ParserContentType.CSV);
		assertNotNull(csvStream);
		
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertEquals(csvReader.readLine().split(",").length, 6);
		assertEquals(csvReader.readLine().split(",")[1], "PB");
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileCsv_givenNoExistingSiglaEstado_thenReturnEmptyList() throws IOException {
		InputStream csvStream = localidadeServiceRest.findLocalidadeBySiglaEstadoParseFile("XX", ParserContentType.CSV);
		assertNotNull(csvStream);
		
		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertNull(csvReader.readLine());
	}
	
	@Test
	public void findLocalidadeBySiglaEstadoParseFileJson_givenValidSiglaEstado_thenResturnListOfCities() throws IOException {
		InputStream jsonStream = localidadeServiceRest.findLocalidadeBySiglaEstadoParseFile("MG", ParserContentType.JSON);
		assertNotNull(jsonStream);
		
		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertTrue(jsonReader.readLine().contains("\"siglaEstado\":\"MG\""));
	}

	@Test
	public void findLocalidadeBySiglaEstadoParseFileJson_givenNoExistingSiglaEstado_thenReturnEmptyList() throws IOException {
		InputStream jsonStream = localidadeServiceRest.findLocalidadeBySiglaEstadoParseFile("XX", ParserContentType.JSON);
		assertNotNull(jsonStream);
		
		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertEquals(jsonReader.readLine(), "[]");
		assertNull(jsonReader.readLine());
	}

}
