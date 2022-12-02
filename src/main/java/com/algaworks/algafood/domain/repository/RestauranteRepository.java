package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository
        extends CustomJpaRespository<Restaurante,Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    // join ManyToOne o fetch eh automatico
    // join manyTomany o fetch nao eh automatico
    // faca o join et e faca o fetch tambem... tudo uma unica consulta sql
    // para o jpql o disctinct eh sobre o objeto pai Restaurante.. entao funciona.
    // ja no select para o banco relacional o distint nao faz nada
    //@Query("select distinct r from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento ")
    @Query("select distinct r from Restaurante r join fetch r.cozinha ")
    List<Restaurante> findAll();

    Restaurante findByIdAndFormasPagamento(Long restauranteId, FormaPagamento formaPagamento);

    List<Restaurante> queryByCozinha (Cozinha cozinha);

    List<Restaurante> findByTaxaFreteBetween (BigDecimal taxaInicial, BigDecimal taxaFinal);
    List<Restaurante> findByNomeContainingAndCozinhaId (String Nome, Long id);

    // @Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinhaId" )
    List<Restaurante> consultarPorNome (String nome, @Param("cozinhaId") Long id);

    // soh quero o primeiro restaurante
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
    List<Restaurante> findTop2RestauranteByNomeContaining (String nome);

    Integer countByCozinha (Cozinha cozinha);
    Integer countByCozinhaId (Long id);

}
