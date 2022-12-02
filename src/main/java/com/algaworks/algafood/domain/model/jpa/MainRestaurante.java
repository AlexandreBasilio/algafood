package com.algaworks.algafood.domain.model.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

public class MainRestaurante {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurantes = applicationContext.getBean(RestauranteRepository.class);

       ConsultaAll(restaurantes);
       //Insercao(restaurantes);
       //Alteracao(restaurantes);
       //Remocao(restaurantes);
       //ConsultaById(restaurantes);
    }

    private static void ConsultaById(RestauranteRepository restaurantes) {
        Optional<Restaurante> c1 = restaurantes.findById(2L);
        System.out.printf("%d - %s\n", c1.get().getId(), c1.get().getNome());
    }

    private static void Remocao(RestauranteRepository restaurantes) {
        restaurantes.deleteById(1L);
    }

    private static void Alteracao(RestauranteRepository restaurantes) {
        Restaurante c1 = new Restaurante();
        c1.setId(3L);
        c1.setNome("Brasileira211");

        restaurantes.save(c1);
    }

    private static void Insercao(RestauranteRepository restaurantes) {
        Restaurante c1 = new Restaurante();
        c1.setNome("Brasileira");

        restaurantes.save(c1);
    }

    private static void ConsultaAll(RestauranteRepository restaurantes) {
        List<Restaurante> todasRestaurantes = restaurantes.findAll();

        for (Restaurante restaurante: todasRestaurantes) {
            //System.out.println(cozinha.getnome());
            System.out.printf("%d - %s - %f - %s \n", restaurante.getId(), restaurante.getNome(), restaurante.getTaxaFrete(), restaurante.getCozinha().getNome());
        }
    }
}
