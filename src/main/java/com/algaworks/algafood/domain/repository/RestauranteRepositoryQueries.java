package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    // o nome do metodo nao tem relevancia
    List<Restaurante> find(String nome,
                           BigDecimal taxaFreteInicial,
                           BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
