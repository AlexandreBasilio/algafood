package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

   public RestauranteNaoEncontradoException(String msg) {
       super(msg);
   }

    public RestauranteNaoEncontradoException(Long id) {
       this(String.format("Restaurante %s nao encontrado", id));
    }
}
