package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
public class CadastroCidadeServico {

    public static final String MSG_CIDADE_EM_USO = "Cidade %s em uso";

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    CadastroEstadoService cadastroEstadoService;

    @Transactional
    public Cidade save (Cidade cidade) {
        Estado estado = cadastroEstadoService.buscarOuFalhar(cidade.getEstado().getId());

        // vc pode lancar um NegocioException se vc nao quer ter uma cidade com mesmo nome (se vc nao tiver uma exception especifica para isso)
        // testar aqui se a o estado existe e lancar uma NegocioExceptio n ao eh legal.. vc ta tratando uma reponse http em servico
        // vc esta lancando uma exception pensando no codigo de status HTTP
        // vc tem que lancar a exception pelo que ela representa de fato,e nao pensando no status http que o controller vai passar para a frente

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public void remove (Long id) {
       try {
            cidadeRepository.deleteById(id);
            cidadeRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        } catch ( JpaSystemException e2 ) {
           throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));  // exception de negocio
        } catch (DataIntegrityViolationException e1) {
           throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
       }
    }

    public Cidade buscarOuFalhar (Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow( () -> new CidadeNaoEncontradaException(id));
    }
}
