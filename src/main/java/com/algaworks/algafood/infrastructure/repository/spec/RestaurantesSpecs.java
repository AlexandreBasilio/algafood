package com.algaworks.algafood.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/*
  Factory de Specs
 */
public class RestaurantesSpecs {

    // retorna com uma expressao lambda e vc pode apagar as classes que criamos
    // a gente cria uma classe anonima usando expressoes Lambda
    public static Specification<Restaurante> comFreteGratis() {
        // return new RestauranteComFreteGratisEspec();
        // posso botar o nome que eu quiser. tem que passar os parametros para criar a Specification, que no caso sao 3 parametros
        //return (raiz, select, criador) ->
        //        criador.equal(raiz.get("taxaFrete"), BigDecimal.ZERO);
        return (root, query, builder) ->
                builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
    }

    public static Specification<Restaurante> comNomeSemelhante (String nome) {
        //return new RestauranteComNomeSemelhanteEspec(nome);
        return (root, query, builder) ->
                builder.like(root.get("nome"), "%" + nome + "%");
    }
}
