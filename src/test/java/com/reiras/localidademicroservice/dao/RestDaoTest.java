package com.reiras.localidademicroservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpServerErrorException;

import com.reiras.localidademicroservice.domain.Localidade;

@SpringBootTest
public class RestDaoTest {

	@Autowired
	private Dao restDao;

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndSiglaEstado_thenReturnCit() {
		Optional<Localidade> obj = restDao.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj.get().getSiglaEstado(), "RJ");
		assertEquals(obj.get().getNomeCidade(), "Rio de Janeiro");
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndValidSiglaEstado_thenReturnEmpty() {
		Optional<Localidade> obj = restDao.findLocalidadeBySiglaEstadoAndNomeCidade("RJ", "NON EXISTENT");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndNoExistingSiglaEstado_thenReturnEmpty() {
		Optional<Localidade> obj = restDao.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenNoExistingNomeCidadeAndSiglaEstado_thenReturnEmpty() {
		Optional<Localidade> obj = restDao.findLocalidadeBySiglaEstadoAndNomeCidade("XX", "NON EXISTENT");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findLocalidadeBySiglaEstadoAndNomeCidade_givenValidNomeCidadeAndInvalidSiglaEstado_thenThrowIllegalSiglaEstadoException() {
		assertThrows(HttpServerErrorException.class, () -> {
			restDao.findLocalidadeBySiglaEstadoAndNomeCidade("--", "Rio de Janeiro");
		});
	}

	@Test
	public void findLocalidadeBySiglaEstado_givenValidSiglaEstado_thenResturnListOfCities() {
		List<Localidade> obj = restDao.findLocalidadeBySiglaEstado("SC");
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
	}

	@Test
	public void findLocalidadeBySiglaEstado_givenNoExistingSiglaEstado_thenReturnEmptyList() {
		List<Localidade> obj = restDao.findLocalidadeBySiglaEstado("XX");
		assertNotNull(obj);
		assertTrue(obj.isEmpty());
	}

	@Test
	public void findLocalidadeBySiglaEstado_givenInvalidSiglaEstado_thenThrowIllegalSiglaEstadoException() {
		assertThrows(HttpServerErrorException.class, () -> {
			restDao.findLocalidadeBySiglaEstado("--");
		});
	}

}
