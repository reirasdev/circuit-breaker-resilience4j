package com.reiras.localidademicroservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reiras.localidademicroservice.dao.Dao;
import com.reiras.localidademicroservice.service.LocalidadeService;

@SpringBootTest
class LocalidadeMicroserviceApplicationTests {

	@Autowired
	private Dao jsonDao;

	@Autowired
	private LocalidadeService localidadeService;

	@Test
	void contextLoads() {
		assertNotNull(jsonDao);
		assertNotNull(localidadeService);
	}

}
