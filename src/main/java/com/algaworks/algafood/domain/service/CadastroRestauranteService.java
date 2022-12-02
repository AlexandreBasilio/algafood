package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CadastroRestauranteService {

    public static final String MSG_RESTAURANTE_EM_USO = "Restaurante %s em uso";

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    CadastroCozinhaService cadastroCozinhaService;

    @Autowired
    CadastroCidadeServico cadastroCidadeServico;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Transactional
    public Restaurante save(Restaurante restaurante) {
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        Cidade cidade = cadastroCidadeServico.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());
        restaurante.getEndereco().setCidade(cidade);

       return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void remove (Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(id);
        } catch ( JpaSystemException e2 ) {
                throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));  // exception de negocio
        } //catch (DataIntegrityViolationException e1) {
         //   throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
        //}
    }

    public Restaurante buscarOuFalhar (Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow( () -> new RestauranteNaoEncontradoException(id));
    }


   @Transactional
    public void ativar (Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        //restaurante.setAtivo(true);
       restaurante.ativar();
        //nao preciso chamar o save pois o JPA quando busca o restaurante no repositorio, a instancia do restaurante fica sendo gerenciado pelo contexto de persistencia do JPA
       // qualquer modif feita na instancia serah persitida no banco de dados
   }

   @Transactional
   public void ativar (List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
   }

    @Transactional
    public void inativar (Long id) {
        Restaurante restaurante = buscarOuFalhar(id);

        //restaurante.setAtivo(false);
        restaurante.inativar();
        //nao preciso chamar o save pois o JPA quando busca o restaurante no repositorio, a instancia do restaurante fica sendo gerenciado pelo contexto de persistencia do JPA
        // qualquer modif feita na instancia serah persitida no banco de dados
    }

    @Transactional
    public void inativar (List<Long> restauranteIds) {
        restauranteIds.forEach(this::inativar);
    }

    @Transactional
    public void desassociarFormaPagamento (Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.searchOuFail(formaPagamentoId);

        restaurante.getFormasPagamento().remove(formaPagamento);
    }

    @Transactional
    public void associarFormaPagamento (Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        FormaPagamento formaPagamento = formaPagamentoService.searchOuFail(formaPagamentoId);

        restaurante.getFormasPagamento().add(formaPagamento);
    }

    @Transactional
    public void abre(Long restaurantId) {
        Restaurante restaurante = buscarOuFalhar(restaurantId);
        restaurante.abrir();
    }

    @Transactional
    public void fecha(Long restaurantId) {
        Restaurante restaurante = buscarOuFalhar(restaurantId);
        restaurante.fechar();
    }

    @Transactional
    public void associarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.searcrOrFail(usuarioId);

        restaurante.associarResponsavel(usuario);
    }

    @Transactional
    public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        Usuario usuario = cadastroUsuarioService.searcrOrFail(usuarioId);

        restaurante.desassociarResponsavel(usuario);
    }
}