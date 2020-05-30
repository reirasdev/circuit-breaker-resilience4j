package com.reiras.localidademicroservice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reiras.localidademicroservice.domain.Localidade;

@SpringBootTest
public class LocalidadeRestDaoTest {

	@Autowired
	private Dao<Localidade> localidadeRestDao;

	@Test
	public void findByStateAndCity_givenValidStateAndCity_thenReturnLocalidade() {
		Optional<Localidade> obj = localidadeRestDao.findByStateAndCity("RJ", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj.get().getSiglaEstado(), "RJ");
		assertEquals(obj.get().getNomeCidade(), "Rio de Janeiro");
	}

	@Test
	public void findByStateAndCity_givenValidStateAndNoExistingCity_thenReturnEmpty() {
		Optional<Localidade> obj = localidadeRestDao.findByStateAndCity("RJ", "NON EXISTENT");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findByStateAndCity_givenNoExistingStateAndValidCity_thenReturnEmpty() {
		Optional<Localidade> obj = localidadeRestDao.findByStateAndCity("XX", "Rio de Janeiro");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findByStateAndCity_givenNoExistingStateAndCity_thenReturnEmpty() {
		Optional<Localidade> obj = localidadeRestDao.findByStateAndCity("XX", "NON EXISTENT");
		assertNotNull(obj);
		assertEquals(obj, Optional.empty());
	}

	@Test
	public void findByState_givenValidState_thenResturnListOfCities() {
		List<Localidade> obj = localidadeRestDao.findByState("SC");
		assertNotNull(obj);
		assertFalse(obj.isEmpty());
	}

	@Test
	public void findByState_givenNoExistingState_thenReturnEmptyList() {
		List<Localidade> obj = localidadeRestDao.findByState("XX");
		assertNotNull(obj);
		assertTrue(obj.isEmpty());
	}

}
