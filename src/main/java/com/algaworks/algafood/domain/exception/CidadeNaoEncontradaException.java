package com.algaworks.algafood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

   public CidadeNaoEncontradaException(String msg) {
       super(msg);
   }

    public CidadeNaoEncontradaException(Long id) {
       this(String.format("Cidade %s nao encontrada", id));
    }
}
