package com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

   public EntidadeNaoEncontradaException(String msg) {
       super(msg);
    }

}
