package com.reiras.localidademicroservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.reiras.localidademicroservice.dao.Dao;

@SpringBootTest
class LocalidadeMicroserviceApplicationTests {

	@Autowired
	private Dao jsonDao;

	@Test
	void contextLoads() {
		assertNotNull(jsonDao);
	}

}
