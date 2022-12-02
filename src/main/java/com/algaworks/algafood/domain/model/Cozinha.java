package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/*
 modelo de dominio e
 modelo de representacao REST
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Cozinha")
@JsonRootName("cozinha") // muda o root do xml (para o singleton somente.. uma lista tem que fazer outra coisa (outra classe WRAPPER para fazer isso).. json nao tem efeito
public  class Cozinha {

    @NotNull (groups = Groups.CozinhaId.class)  // mas ele eh auto increment... funciona em cascata.. mas para cadastrar a cozinha direto nao funciona. Como fazer ? criar grupo de validacao
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank  // aqui serve para cozinha mas nao serve quando vc cria um restaurante (ele vai dizer que o nome eh branco).  Como fazer ? criar grupo de validacao
    @Column(nullable = false, length = 60)
    private String nome;

    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList<>(); // evitar um nullPointerException
}
