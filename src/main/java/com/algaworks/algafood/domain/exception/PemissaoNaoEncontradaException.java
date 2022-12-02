package com.algaworks.algafood.domain.exception;

public class PemissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

   public PemissaoNaoEncontradaException(String msg) {
       super(msg);
   }

    public PemissaoNaoEncontradaException(Long id) {
       this(String.format("Permissqao %s nao encontrada", id));
    }
}
