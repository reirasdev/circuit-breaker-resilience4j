package com.reiras.localidademicroservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpServerErrorException;

import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.exception.ObjectNotFoundException;

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

}
