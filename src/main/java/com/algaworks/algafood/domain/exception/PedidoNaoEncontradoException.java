package com.algaworks.algafood.domain.exception;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

   public PedidoNaoEncontradoException(String msg) {
       super(msg);
   }

    public PedidoNaoEncontradoException(Long id) {
       this(String.format("Pedido %s nao encontrado", id));
    }
}
