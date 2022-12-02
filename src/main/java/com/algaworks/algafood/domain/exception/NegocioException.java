package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

    public NegocioException(String msg) {
       super(msg);
    }

    public NegocioException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
