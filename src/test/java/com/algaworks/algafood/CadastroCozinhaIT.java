package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
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

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class) // suporte para carrega um contexto. carrega o contexto do spring e vc pode usar os recursos do framework (ex: DI)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // fornece as funcionalidades do spring nos testes
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

    public static final int COZINHA_ID_INEXISTENTE = 100;
    public static final int COZINHA_ID_EXISTENTE = 1;

    @LocalServerPort
    private int port;

    private List<String> nomeCozinhas = Arrays.asList(new String[]{"Chinesa", "Argentina"});

    @Autowired
    DataBaseCleaner dataBaseCleaner;

    @Autowired
    CozinhaRepository cozinhaRepository;

    private String jsonCorretoCozinhaBrasileira;

    @Before
    public void setup() {
        // salvar um log
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        jsonCorretoCozinhaBrasileira = ResourceUtils.getContentFromResource("/json/correto/cozinha-brasileira.json");

        dataBaseCleaner.deleteAllTables();
        prepararDados();
    }

    @Test
    public void shouldRetornarStatus200_whenConsultarCozinhas() {

        // faz chamada api
        RestAssured.given()
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldConterQteCorretaCozinhas_QuandoConsultarCozinhas() {
        RestAssured.given()
                .accept(ContentType.JSON)
            .when()
              .get()
            .then()
              .body("", Matchers.hasSize(this.nomeCozinhas.size()))
              .body("nome", Matchers.hasItems(this.nomeCozinhas.toArray()));
    }

    @Test
    public void shouldRetornar201_whenCadastroNovaCozinha() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(jsonCorretoCozinhaBrasileira)
                .accept(ContentType.JSON)
             .when()
                .post()
              .then()
                 .statusCode(HttpStatus.CREATED.value());
    }

    // GET /cozinhas/1
    @Test
    public void shouldRetornarRespostaEStatusCorretos_whenConsultarCozinhaExistente() {
        RestAssured
                .given()
                .pathParam("cozinhaId", COZINHA_ID_EXISTENTE)
                  .accept(ContentType.JSON)
               .when()
                  .get("/{cozinhaId}")
                .then()
                  .body("nome", Matchers.equalTo(nomeCozinhas.get(0)))
                  .statusCode(HttpStatus.OK.value());
    }

    // GET /cozinhas/100
    @Test
    public void shouldRetornarStatus404_whenConsultarCozinhaInexistente() {
        RestAssured
                .given()
                  .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                  .accept(ContentType.JSON)
                .when()
                  .get("/{cozinhaId}")
                .then()
                .  statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados () {
        for (String nomeCozinha: this.nomeCozinhas) {
            Cozinha c = new Cozinha();
            c.setNome(nomeCozinha);
            cozinhaRepository.save(c);
        }
    }

}
