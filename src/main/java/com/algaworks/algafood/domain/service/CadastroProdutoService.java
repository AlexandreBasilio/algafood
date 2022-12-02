
package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoNoRestauranteException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CadastroProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    public Produto searchOrFail (Long produtoId, Long restauranteId) {
        return produtoRepository.findById(restauranteId, produtoId)
                .orElseThrow(()->  new ProdutoNaoEncontradoNoRestauranteException(produtoId, restauranteId));
    }

    public Produto searchOrFail (Long produtoId, Restaurante restaurante) {
        Optional<Produto> produto = Optional.ofNullable(produtoRepository.findByIdAndRestaurante(produtoId, restaurante));
        if (produto.isEmpty()) {
            throw new ProdutoNaoEncontradoNoRestauranteException(produtoId, restaurante.getId());
        }

        return produto.get();
    }

    public Produto searchOrFail (Long produtoId) {
        return produtoRepository.findById(produtoId)
                .orElseThrow( () -> new ProdutoNaoEncontradoException(produtoId));
    }

    @Transactional
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    @Transactional
    public void remove (Long produtoId, Long restauranteId) {
         Produto produto = searchOrFail(produtoId, restauranteId);
         produtoRepository.delete(produto);

    }
}
