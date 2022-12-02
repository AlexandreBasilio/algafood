package com.algaworks.algafood;

import com.algaworks.algafood.infrastructure.repository.CustomJpaRespositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EnableJpaRepositories( repositoryBaseClass = CustomJpaRespositoryImpl.class) //habilitar/ativar nova implementacao customizada para o spring data JPA que a gente tem outro repositorio customizado
 // remplace a implementacao de base simpleJPaRepositoty pela nova customizada
public class AlgafoodApiApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(AlgafoodApiApplication.class, args);
    }

}
