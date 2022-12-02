package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="produto")
public class Produto
{
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column (name="nome", nullable = false)
    private String nome;

    @NotBlank
    @Column (name="descricao", nullable = false)
    private String descricao;

    @NotNull
    @DecimalMin(value = "0")
    @Column (name="preco", nullable = false)
    private BigDecimal preco;

    @Column (name="ativo", nullable = false)
    private boolean ativo = Boolean.TRUE;

    @ManyToOne
    @JoinColumn (name="restaurante_id", nullable = false)
    private Restaurante restaurante;
}
