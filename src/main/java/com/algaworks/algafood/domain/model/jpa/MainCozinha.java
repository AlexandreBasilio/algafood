package com.algaworks.algafood.domain.model.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;
import java.util.Optional;

public class MainCozinha {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        CozinhaRepository cozinhas = applicationContext.getBean(CozinhaRepository.class);

       //ConsultaAll(cozinhas);
       //Insercao(cozinhas);
       //Alteracao(cozinhas);
       //Remocao(cozinhas);
       //ConsultaById(cozinhas);
    }

    private static void ConsultaById(CozinhaRepository cozinhas) {
        Optional<Cozinha> c1 = cozinhas.findById(2L);
        System.out.printf("%d - %s\n", c1.get().getId(), c1.get().getNome());
    }

    private static void Remocao(CozinhaRepository cozinhas) {
        cozinhas.deleteById(1L);
    }

    private static void Alteracao(CozinhaRepository cozinhas) {
        Cozinha c1 = new Cozinha();
        c1.setId(3L);
        c1.setNome("Brasileira211");

        cozinhas.save(c1);
    }

    private static void Insercao(CozinhaRepository cozinhas) {
        Cozinha c1 = new Cozinha();
        c1.setNome("Brasileira");

        cozinhas.save(c1);
    }

    private static void ConsultaAll(CozinhaRepository cozinhas) {
        List<Cozinha> todasCozinhas = cozinhas.findAll();

        for (Cozinha cozinha: todasCozinhas) {
            //System.out.println(cozinha.getnome());
            System.out.printf("%d - %s\n", cozinha.getId(), cozinha.getNome());
        }
    }
}
