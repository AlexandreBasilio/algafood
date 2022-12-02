package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.StatusPedido;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class PedidoResumoDTO {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal subTotal;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private StatusPedido status;
    private UsuarioDTO cliente;
    private RestauranteResumidoDTO restaurante;
}
