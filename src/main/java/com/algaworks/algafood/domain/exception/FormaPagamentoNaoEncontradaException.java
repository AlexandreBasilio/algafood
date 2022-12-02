package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

   public FormaPagamentoNaoEncontradaException(String msg) {
       super(msg);
   }

    public FormaPagamentoNaoEncontradaException(Long id) {
       this(String.format("Forma de Pagamento %s nao encontrada", id));
    }
}
