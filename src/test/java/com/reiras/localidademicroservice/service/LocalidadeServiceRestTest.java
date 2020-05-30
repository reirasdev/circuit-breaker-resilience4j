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
	private Service<Localidade> localidadeService;

	@Test
	public void findByStateAndCity_givenValidStateAndCity_thenReturnLocalidade() {
		Localidade obj = localidadeService.findByStateAndCity("RJ", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj.getIdCidade(), 3304557);
	}

	@Test
	public void findByStateAndCity_givenValidStateAndNoExistingCity_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findByStateAndCity("RJ", "NON EXISTENT");
		});
	}

	@Test
	public void findByStateAndCity_givenNoExistingStateAndValidCity_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findByStateAndCity("XX", "Rio de Janeiro");
		});
	}

	@Test
	public void findByStateAndCity_givenNoExistingStateAndCity_thenReturnEmpty() {
		assertThrows(ObjectNotFoundException.class, () -> {
			localidadeService.findByStateAndCity("XX", "NON EXISTENT");
		});
	}

	@Test
	public void findByState_givenValidState_thenResturnListOfCities() {
		List<Localidade> obj = localidadeService.findByState("AM");
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
	}

	@Test
	public void findByState_givenNoExistingState_thenReturnEmptyList() {
		List<Localidade> obj = localidadeService.findByState("XX");
		assertNotNull(obj);
		assertTrue(obj.isEmpty());
	}

	@Test
	public void findByStateParseFileCsv_givenValidState_thenResturnStreamWithListOfCities() throws IOException {
		InputStream csvStream = localidadeService.findByStateParseFile("PB", ParserContentType.CSV);
		assertNotNull(csvStream);

		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertEquals(csvReader.readLine().split(",").length, 6);
		assertEquals(csvReader.readLine().split(",")[1], "PB");
	}

	@Test
	public void findByStateParseFileCsv_givenNoExistingSiglaEstado_thenReturnStreamWithEmptyList() throws IOException {
		InputStream csvStream = localidadeService.findByStateParseFile("XX", ParserContentType.CSV);
		assertNotNull(csvStream);

		BufferedReader csvReader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
		assertNotNull(csvReader);
		assertEquals(csvReader.readLine(), "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado");
		assertNull(csvReader.readLine());
	}

	@Test
	public void findByStateParseFileJson_givenValidState_thenResturnStreamWithListOfCities() throws IOException {
		InputStream jsonStream = localidadeService.findByStateParseFile("MG", ParserContentType.JSON);
		assertNotNull(jsonStream);

		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertTrue(jsonReader.readLine().contains("\"siglaEstado\":\"MG\""));
	}

	@Test
	public void findByStateParseFileJson_givenNoExistingSiglaEstado_thenReturnStreamWithEmptyList() throws IOException {
		InputStream jsonStream = localidadeService.findByStateParseFile("XX", ParserContentType.JSON);
		assertNotNull(jsonStream);

		BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));
		assertNotNull(jsonReader);
		assertEquals(jsonReader.readLine(), "[]");
		assertNull(jsonReader.readLine());
	}

}
