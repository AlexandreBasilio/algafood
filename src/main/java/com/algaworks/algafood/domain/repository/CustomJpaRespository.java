package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

@NoRepositoryBean  // indica que o Spring nao vai instanciar a implementacao para esta classe
public interface CustomJpaRespository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findPrimeiro();

    void detach(T Entity);
}
