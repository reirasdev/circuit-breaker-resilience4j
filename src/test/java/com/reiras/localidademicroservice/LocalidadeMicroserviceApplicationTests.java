package com.reiras.localidademicroservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reiras.localidademicroservice.controller.LocalidadeController;
import com.reiras.localidademicroservice.dao.Dao;
import com.reiras.localidademicroservice.domain.Localidade;
import com.reiras.localidademicroservice.service.LocalidadeService;

@SpringBootTest
class LocalidadeMicroserviceApplicationTests {

	@Autowired
	private Dao<Localidade> localidadeRestDao;

	@Autowired
	private LocalidadeService localidadeServiceRest;

	@Autowired
	private LocalidadeController localidadeController;

	@Test
	void contextLoads() {
		assertNotNull(localidadeRestDao);
		assertNotNull(localidadeServiceRest);
		assertNotNull(localidadeController);
	}

}
