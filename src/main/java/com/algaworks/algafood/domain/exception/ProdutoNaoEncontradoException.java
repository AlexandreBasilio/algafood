package com.algaworks.algafood.domain.exception;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

   public ProdutoNaoEncontradoException(String msg) {
       super(msg);
   }

    public ProdutoNaoEncontradoException(Long id) {
       this(String.format("Produto %s nao encontrado", id));
    }
}
