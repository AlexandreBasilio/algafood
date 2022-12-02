package com.algaworks.algafood.api.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // soh inclui na reprentacao os nao null
@Getter
@Builder // padrao de projeto para criar objetos )
public class Problem {

    // padrao RFC
    private Integer status;
    private String type;
    private String title;
    private String detail;

    // Especificacao do padrao
    private String userMessage;
    private OffsetDateTime timestamp;
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {
        private String name;
        private String userMessage;
    }
}