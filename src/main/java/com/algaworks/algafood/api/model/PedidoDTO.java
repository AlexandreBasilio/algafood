package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private BigDecimal taxaFrete;
    private BigDecimal subTotal;
    private BigDecimal valorTotal;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private StatusPedido status;
    private UsuarioDTO cliente;
    private RestauranteResumidoDTO restaurante;
    private FormaPagamentoDTO formaPagamento;
    private EnderecoDTO enderecoEntrega;
    private Set<ItemPedidoDTO> itens;

}
