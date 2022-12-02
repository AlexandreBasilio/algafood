package com.algaworks.algafood.domain.model.jpa;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class CadastroCozinhaX {

    @PersistenceContext
    private EntityManager manager;

    public List<Cozinha> all() {
        TypedQuery query = manager.createQuery("FROM Cozinha", Cozinha.class);
        return query.getResultList();
    }

    @Transactional
    public Cozinha save(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Transactional
    public void remove(Cozinha cozinha) {
        manager.remove(byId(cozinha.getId()));
    }

    public Cozinha byId(Long id) {
        return manager.find(Cozinha.class, id);
    }

}
