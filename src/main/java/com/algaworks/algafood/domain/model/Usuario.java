package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="usuario")
public class Usuario {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column (name="nome", nullable = false)
    private String nome;

    @NotBlank
    @Email
    @Column (name="email", nullable = false)
    private String email;

    @NotBlank
    @Column (name="senha", nullable = false)
    private String senha;

    @CreationTimestamp
    @Column (name="data_cadastro", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @ManyToMany
    @JoinTable (name = "usuario_grupo",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="grupo_id")
    )
    private Set<Grupo> grupos = new HashSet<>();

    public Boolean senhaCoincideCom (String senha) {
        return getSenha().equalsIgnoreCase(senha);
    }

    public Boolean senhaNaoCoincideCom (String senha) {
        return !senhaCoincideCom(senha);
    }

    public void adicionaGrupo (Grupo grupo) {
        getGrupos().add(grupo);
    }

    public void removeGrupo (Grupo grupo) {
        getGrupos().remove(grupo);
    }
}
