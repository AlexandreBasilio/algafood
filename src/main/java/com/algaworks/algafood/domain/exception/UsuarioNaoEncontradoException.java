package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

   public UsuarioNaoEncontradoException(String msg) {
       super(msg);
   }

    public UsuarioNaoEncontradoException(Long id) {
       this(String.format("Usuario %s nao encontrado", id));
    }
}
