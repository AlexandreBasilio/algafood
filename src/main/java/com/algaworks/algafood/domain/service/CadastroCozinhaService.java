package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_EM_USO = "Cozinha %s em uso";

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Transactional
    public Cozinha save (Cozinha cozinha) {
        /*
          aqui vc coloca as regras de negocio, e nao la no repositorio
         */
        return cozinhaRepository.save(cozinha);
    }

    @Transactional
    public void remove (Long id) {
        try {
            cozinhaRepository.deleteById(id);
            cozinhaRepository.flush();

        } catch (DataIntegrityViolationException e ) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));  // exception de negocio

        } catch (JpaSystemException e2 ) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));  // exception de negocio

        } catch (EmptyResultDataAccessException e1) {
            throw new CozinhaNaoEncontradaException(id);  // exception de negocio
            //throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cozinha %s nao encontrada", id)); // exception de negocio template do SPRING a partir da versao 5
            // classe de dominio nao deve trabalhar com HTTP Status
        }
    }

    public Cozinha buscarOuFalhar (Long id) {
        return cozinhaRepository.findById(id).orElseThrow(()-> new CozinhaNaoEncontradaException(id));
    }

}
