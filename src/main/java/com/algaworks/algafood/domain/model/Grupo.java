package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="grupo")
public class
Grupo {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column (name="nome", nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable (name = "grupo_permissao",
            joinColumns = @JoinColumn(name="grupo_id"),
            inverseJoinColumns = @JoinColumn(name="permissao_id"))
    private Set<Permissao> permissoes = new HashSet<>();

    public void adicionarPermissao (Permissao permissao) {
        getPermissoes().add(permissao);
    }

    public void removerPermissao (Permissao permissao) {
        getPermissoes().remove(permissao);
    }
}
