package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.Multiplo;
import com.algaworks.algafood.core.validation.ValorFreteZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// se o frete for gratis (taxaFrete=0) o nome do restaurante tem que ter a descricao frete gratis
@ValorFreteZeroIncluiDescricao(valorField="taxaFrete", descricaoField="nome", descricaoObrigatoria = "frete gratis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotNull  // nao pode ser nulo
    // @NotEmpty // nao pode ser nulo nem vazio
    //@NotBlank (message = "nome obrigatorio") // nao pode ser nulo nem vazio nem branco
    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @DecimalMin(value = "0") //, message = "{TaxaFrete.invalida}")  // o message aqui eh do Bean Validation( ValidationMessages.properties)
    // @TaxaFrete
    @Multiplo(numero = 5)
    @Column (name="taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @NotNull
    @Column (name = "aberto")
    private Boolean aberto;

    @Column (name = "ativo")
    private Boolean ativo = Boolean.TRUE;

    @CreationTimestamp  // do hibernate.. fica ligado ao hibernate .. qdo a entidade eh salva a primeira vez ele coloca a data hora
    @Column (name="data_cadastro", nullable = false)
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp // do hibernate.. fica ligado ao hibernate .. qdo a entidade ja existe e eh salva a ele coloca a data hora
    @Column (name="data_atualizacao", nullable = false)
    private OffsetDateTime dataAtualizacao;

    @Valid  // forcar para faze a validation em cascata
    @ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn( name="cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded // incoorporando
    private Endereco endereco;

    @ManyToMany (fetch = FetchType.LAZY)  // padrao eh o LAZY (soh carrega quando precisa)
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn (name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable (name = "restaurante_usuario_responsavel",
                joinColumns = @JoinColumn (name= "restaurante_id"),
                inverseJoinColumns = @JoinColumn (name= "usuario_id"))
    private Set<Usuario> usuarios = new HashSet();

    @OneToMany (mappedBy = "restaurante")
    Set<Produto> produtos = new HashSet<>();

    public void ativar() {
        setAtivo(true);
    }

    public void inativar() {
        setAtivo(false);
    }

    public void abrir () {
        setAberto(true);
    }

    public void fechar () {
        setAberto(false);
    }

    public void associarResponsavel (Usuario usuario) {
        getUsuarios().add(usuario);
    }

    public void desassociarResponsavel (Usuario usuario) {
        getUsuarios().remove(usuario);
    }


}
