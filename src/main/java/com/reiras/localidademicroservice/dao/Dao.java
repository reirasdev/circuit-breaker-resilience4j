package com.reiras.localidademicroservice.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

	public Optional<T> findByStateAndCity(String state, String city);

	public List<T> findByState(String state);

}
