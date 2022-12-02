package com.algaworks.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado"),
    ERRO_DE_NEGOCIO ("/erro-negocio", "Violação de regra de negócio"),
    ENTIDADE_EM_USO ("/entidade-em-uso", "Entidade em uso"),
    MSG_IMCOMPREENSIVEL ("/mensagem-imcomprenssivel", "Mensagem imcomprenssivel"),
    PARAMETRO_INVALIDO ("/parametro-invalido", "Parametro invalido"),
    DADO_INVALIDO ("/dado-invalido", "Dado invalido"),
    ERRO_SISTEMA ("/erro-sistema", "Erro de Sistema");;

    private String url;
    private String title;

    ProblemType(String path, String title) {
        this.url = "https://algafood.com.br" + path;
        this.title = title;
    }
}
