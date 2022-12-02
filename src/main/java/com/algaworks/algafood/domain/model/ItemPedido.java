package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode (onlyExplicitlyIncluded = true)
@Entity
@Table(name="item_pedido")
public class ItemPedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name="quantidade", nullable = false)
    private Integer quantidade;

    @Column (name="preco_unitario", nullable = false)
    private BigDecimal precoUnitario;

    @Column (name = "preco_total", nullable = false)
    private BigDecimal precoTotal;

    @Column (name = "observacao")
    private String observacao;

    @ManyToOne
    @JoinColumn(name="produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name="pedido_id", nullable = false)
    private Pedido pedido;

    public void calcularPrecoTotal () {
        if (this.quantidade == null) {
            this.quantidade = 0;
        }

        if (this.precoUnitario == null) {
            this.precoUnitario= BigDecimal.ZERO;
        }

        this.setPrecoTotal(this.precoUnitario.multiply(new BigDecimal(this.quantidade)));
    }


}
