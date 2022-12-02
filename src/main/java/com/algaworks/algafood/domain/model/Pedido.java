package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="pedido")
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false)
    private OffsetDateTime dataCriacao;

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "data_entrega")
    private OffsetDateTime dataEntrega;

    @Column(name = "data_cancelemento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "status")
    @Enumerated (EnumType.STRING)  // para converter a String no banco de dados para Enumeration
    private StatusPedido status = StatusPedido.CRIADO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @Embedded
    private Endereco enderecoEntrega;

    @OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL)  // cascade = CascadeType.ALL  (salvar em cascata...0vc salva o pedido e aih o itemPedido vai em cascata
    private List<ItemPedido> itens = new ArrayList<>();

    public void calcularValorTotal() {
        //BigDecimal subTotalPedido = BigDecimal.ZERO;
        BigDecimal taxaFrete  = getTaxaFrete() != null ? getTaxaFrete() : BigDecimal.ZERO;

        this.getItens().forEach(itemPedido -> {
            itemPedido.calcularPrecoTotal();
        });

        this.subTotal = getItens().stream()
                .map(item -> item.getPrecoTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        setValorTotal(this.subTotal.add(taxaFrete));
    }

    public void confirmar () {
        setStatus(StatusPedido.CONFIRMADAO);
        setDataConfirmacao(OffsetDateTime.now());
    }

    public void entregar () {
        setStatus(StatusPedido.ENTREGUE);
        setDataEntrega(OffsetDateTime.now());
    }

    public void cancelar () {
        setStatus(StatusPedido.CANCELADO);
        setDataCancelamento(OffsetDateTime.now());
    }

    private void setStatus (StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format("Status do Pedido %s nao pode ser alterado de %s para %s",
                    getId(), getStatus().getDescricao(), novoStatus.getDescricao())
            );
        }
        this.status = novoStatus;
    }

 }
