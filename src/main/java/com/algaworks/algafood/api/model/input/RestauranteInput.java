package com.algaworks.algafood.api.model.input;

import com.algaworks.algafood.core.validation.Multiplo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteInput {

    @NotBlank
    private String nome;

    @NotNull
    @DecimalMin(value = "0")
    @Multiplo(numero = 5)
    private BigDecimal taxaFrete;

    @NotNull
    private Boolean aberto;

    @Valid  // forcar para faze a validation em cascata
    @NotNull
    private CozinhaIdInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;

}
