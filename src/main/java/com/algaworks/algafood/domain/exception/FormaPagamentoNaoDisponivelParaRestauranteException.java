package com.algaworks.algafood.domain.exception;

public class FormaPagamentoNaoDisponivelParaRestauranteException extends EntidadeNaoEncontradaException {

   public FormaPagamentoNaoDisponivelParaRestauranteException(String msg) {
       super(msg);
   }

    public FormaPagamentoNaoDisponivelParaRestauranteException(Long formaPagamentoId, Long restauranteId) {
       this(String.format("Forma de pagamento %s nao disponivel para o Restaurante %s",
               formaPagamentoId, restauranteId));
    }
}
