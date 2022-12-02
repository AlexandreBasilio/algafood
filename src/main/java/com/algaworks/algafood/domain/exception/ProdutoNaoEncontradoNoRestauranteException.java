package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoNoRestauranteException extends EntidadeNaoEncontradaException {

   public ProdutoNaoEncontradoNoRestauranteException(String msg) {
       super(msg);
   }

    public ProdutoNaoEncontradoNoRestauranteException(Long produtoId, Long restauranteId) {
       this(String.format("Produto %s nao encontrado para o Restaurante %s", produtoId, restauranteId));
    }
}
