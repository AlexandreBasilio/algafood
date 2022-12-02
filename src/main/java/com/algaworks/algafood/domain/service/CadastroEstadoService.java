package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Estado %s em uso";

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Estado save (Estado estado) {
        return estadoRepository.save(estado);
    }

    @Transactional
    public void remove (Long id) {
        try {
            estadoRepository.deleteById(id);
            estadoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);

        } catch (DataIntegrityViolationException e1) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));

        } catch ( JpaSystemException e2 ) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));  // exception de negocio
        }
    }

    public Estado buscarOuFalhar (Long id) {
        return estadoRepository.findById(id)
                .orElseThrow( () -> new EstadoNaoEncontradoException(id));
    }
}
