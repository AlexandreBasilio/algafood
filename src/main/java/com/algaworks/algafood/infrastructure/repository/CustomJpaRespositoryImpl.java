package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.repository.CustomJpaRespository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.Optional;

public class CustomJpaRespositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
    implements CustomJpaRespository<T, ID> {

    private EntityManager manager;

    public CustomJpaRespositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> findPrimeiro() {
       // manager.createQuery(" from ")
        var jpql = " from " +  getDomainClass().getName();
        T entidade = manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();

        return  Optional.ofNullable(entidade);
    }

    @Override
    public void detach(T entity) {
        manager.detach(entity);
    }
}
