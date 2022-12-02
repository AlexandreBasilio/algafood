package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRespository<Cozinha, Long> {

    List<Cozinha> findTodasByNome(String nome);
    List<Cozinha> findTodasByNomeContaining (String nome);
   // List<Cozinha> findQualquerCoisaByNome(String nome);
   // List<Cozinha> findTodasByNome(String nome);
   // List<Cozinha> findTodasCozinhasByNome(String nome);
   // Cozinha findByNome(String nome);
   Optional<Cozinha> findByNome(String nome);

   boolean existsByNome (String nome);
   Integer countByNome (String nome);
}
