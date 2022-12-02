package com.algaworks.algafood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

   public CozinhaNaoEncontradaException(String msg) {
       super(msg);
   }

    public CozinhaNaoEncontradaException(Long id) {
       this(String.format("Cozinha %s nao encontrado", id));
    }
}
