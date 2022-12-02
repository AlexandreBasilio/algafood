package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

   public EstadoNaoEncontradoException(String msg) {
       super(msg);
   }

    public EstadoNaoEncontradoException(Long id) {
       this(String.format("Estado %s nao encontrado", id));
    }
}
