package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

    @JsonIgnore  //nao serializa este atributo no JSON para evitar a serializacao CIRCULAR
    private List<Restaurante> restaurantes = new ArrayList<>(); // evitar um nullPointerException
}
