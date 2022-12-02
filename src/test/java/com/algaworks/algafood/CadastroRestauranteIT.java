package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DataBaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class) // suporte para carrega um contexto. carrega o contexto do spring e vc pode usar os recursos do framework (ex: DI)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // fornece as funcionalidades do spring nos testes
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

    public static final int RESTAURANTE_ID_INEXISTENTE = 100;
    public static final int RESTAURANTE_ID_EXISTENTE = 1;
    public static final String DADO_INVALIDO = "Dado invalido";
    public static final String VIOLACAO_REGRA_NEGOCIO = "Violação de regra de negócio";
    public static final String ERRO_SINTAXE_MSG_IMCOMPREENSIVEL = "Mensagem imcomprenssivel";

    @LocalServerPort
    private int port;

    private List<String> nomeCozinhas = Arrays.asList(new String[]{"Chinesa", "Argentina"});

    @Autowired
    DataBaseCleaner dataBaseCleaner;

    @Autowired
    CozinhaRepository cozinhaRepository;

    @Autowired
    RestauranteRepository restauranteRepository;

    private String jsonCorretoNovoRestaurante;
    private String jsonRestauranteSemNome;
    private String jsonRestauranteSemTaxaFrete;
    private String jsonRestauranteSemFlagAberto;
    private String jsonRestauranteSemCozinha;
    private String jsonRestauranteComCozinhaInexistente;
    private String jsonRestauranteComErroDeSintaxeJSON;

    @Before
    public void setup() {
        // salvar um log
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        jsonCorretoNovoRestaurante = ResourceUtils.getContentFromResource("/json/correto/restaurante-novo-correto.json");
        jsonRestauranteSemNome = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-nome.json");
        jsonRestauranteSemTaxaFrete = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-taxa-frete.json");
        jsonRestauranteSemFlagAberto = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-flag-aberto.json");
        jsonRestauranteSemCozinha = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-sem-cozinha.json");
        jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-com-cozinha-inexistente.json");
        jsonRestauranteComErroDeSintaxeJSON = ResourceUtils.getContentFromResource("/json/incorreto/restaurante-com-erro-de-sintaxe.json");

        dataBaseCleaner.deleteAllTables();
        prepararDados();
    }

    private void prepararDados () {
        var contRestaurante = 0;

        // cozinhas
        for (String nomeCozinha: this.nomeCozinhas) {
            Cozinha c = new Cozinha();
            c.setNome(nomeCozinha);
            cozinhaRepository.save(c);

            // restaurantes
            Restaurante restaurante1 = new Restaurante();
            restaurante1.setNome("Restaurante " + contRestaurante++);
            restaurante1.setTaxaFrete(new BigDecimal(10));
            restaurante1.setAberto(true);
            restaurante1.setCozinha(c);
            restauranteRepository.save(restaurante1);

            Restaurante restaurante2 = new Restaurante();
            restaurante2.setNome("Restaurante " + contRestaurante++);
            restaurante2.setTaxaFrete(new BigDecimal(15));
            restaurante2.setAberto(true);
            restaurante2.setCozinha(c);
            restauranteRepository.save(restaurante2);
        }
    }

    @Test
    public void shouldRetornarStatus201_WhenCadastroRestauranteCorreto () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonCorretoNovoRestaurante)
            .when()
              .post()
            .then()
              .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteSemNome () {
        RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(jsonRestauranteSemNome)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(DADO_INVALIDO));
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteSemTsxaFrete () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonRestauranteSemTaxaFrete)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                 .body("title", Matchers.equalTo(DADO_INVALIDO));
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteSemFlagAberto () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonRestauranteSemFlagAberto)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(DADO_INVALIDO));
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteSemCozinha () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonRestauranteSemCozinha)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(DADO_INVALIDO));
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteComCozinhaInexistente () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonRestauranteComCozinhaInexistente)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
        .body("title", Matchers.equalTo(VIOLACAO_REGRA_NEGOCIO));
    }

    @Test
    public void shouldRetornarStatus400_WhenCadastroRestauranteComErroDeSintaxeJSON () {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonRestauranteComErroDeSintaxeJSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("title", Matchers.equalTo(ERRO_SINTAXE_MSG_IMCOMPREENSIVEL));
    }


    @Test
    public void shouldRetornarRespostaCorretaEStatus200_WhenRestauranteExiste() {
        RestAssured.given()
                  .pathParam("restauranteId", RESTAURANTE_ID_EXISTENTE)
                  .accept(ContentType.JSON)
                .when()
                  .get("/{restauranteId}")
                .then()
                  .body("nome", Matchers.equalTo("Restaurante 0"))
                  .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldRetornarStatus404_WhenRestauranteNaoExiste() {
        RestAssured
                .given()
                  .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                  .accept(ContentType.JSON)
                .when()
                  .get("/{restauranteId}")
                .then()
                   .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void shouldRetornarRestaurantesEtStatus200_WhenConsultarRestaurantes() {
        RestAssured
                .given()
                  .accept(ContentType.JSON)
                .when()
                  .get()
                .then()
                  .statusCode(HttpStatus.OK.value());
    }

}
