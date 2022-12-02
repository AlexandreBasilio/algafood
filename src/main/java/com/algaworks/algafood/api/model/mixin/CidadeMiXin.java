package com.algaworks.algafood.api.model.mixin;

import com.algaworks.algafood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMiXin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
