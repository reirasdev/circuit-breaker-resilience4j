package com.reiras.localidademicroservice.dao;

import java.util.List;
import java.util.Optional;

import com.reiras.localidademicroservice.domain.Localidade;

public interface Dao {

	Optional<Localidade> findLocalidadeBySiglaEstadoAndNomeCidade(String siglaEstado, String name);

	List<Localidade> findLocalidadeBySiglaEstado(String siglaEstado);

}
