package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

   public GrupoNaoEncontradoException(String msg) {
       super(msg);
   }

    public GrupoNaoEncontradoException(Long id) {
       this(String.format("Grupo %s nao encontrado", id));
    }
}
