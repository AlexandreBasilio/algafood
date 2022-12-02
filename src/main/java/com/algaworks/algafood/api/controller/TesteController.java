package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comNomeSemelhante;


@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

//    @GetMapping("/cozinhas/por-nome/{nome}")
//    public List<Cozinha> listByNome(@PathVariable String nome) {
//        return cozinhaRepository.listByNome(nome);
//    }
//
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> listByNome2 (@RequestParam("nome")  String nome) {
        return cozinhaRepository.findTodasByNome(nome);
    }

    @GetMapping("/cozinhas/unico-por-nome")
    public Optional<Cozinha> listByNome3 (@RequestParam("nome")  String nome) {
        return cozinhaRepository.findByNome(nome);
    }

    @GetMapping("/cozinhas/contain-nome")
    public List<Cozinha> listByNome4 (@RequestParam("nome")  String nome) {
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/por-taxafrete")
    public List<Restaurante> restaurantesPorTaxaFrete (@RequestParam BigDecimal taxaInicial,
                                                       @RequestParam BigDecimal taxaFinal) {
        return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/restaurantes/por-nome-taxafrete")
    public List<Restaurante> restaurantesPorNomeTaxaFrete (@RequestParam(required = false) String nome,
                                                       @RequestParam(required = false) BigDecimal taxaInicial,
                                                       @RequestParam(required = false) BigDecimal taxaFinal) {
        System.out.println(nome + taxaInicial + " " + taxaFinal );
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    @PostMapping("/restaurantes/por-cozinha")
    public List<Restaurante> restaurantesPorCozinha (@RequestBody Cozinha cozinha) {
        return restauranteRepository.queryByCozinha(cozinha);
    }

    @GetMapping("/restaurantes/por-nome-restaurante_id_cozinha")
    public List<Restaurante> restaurantesPorNomeIdCozinha (@RequestParam String nome, @RequestParam Long idCozinha) {
        //return restauranteRepository.findByNomeContainingAndCozinhaId(nome, idCozinha);
        return restauranteRepository.consultarPorNome(nome, idCozinha);
    }

    @GetMapping("/restaurantes/primeiro-por-nome")
    public Optional<Restaurante> restaurantePrimeiroPorNome (@RequestParam String nome) {
        return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
    }

    @GetMapping("/restaurantes/top2-por-nome")
    public List<Restaurante> restaurantesPorNomeTop2 (@RequestParam String nome) {
        return restauranteRepository.findTop2RestauranteByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/exists")
    public boolean cozinhasExists (@RequestParam String nome) {
        return cozinhaRepository.existsByNome(nome);
    }

    @GetMapping("/cozinhas/count")
    public Integer cozinhasCount (@RequestParam String nome) {
        return cozinhaRepository.countByNome(nome);
    }

    @GetMapping("/restaurantes/count-por-id-cozinha")
    public Integer restaurantesCountByIdCozinha (@RequestParam Long id) {
        return restauranteRepository.countByCozinhaId(id);
    }

    @PostMapping("/restaurantes/count-por-cozinha")
    public Integer restaurantesCountByCozinha (@RequestBody Cozinha cozinha) {
        return restauranteRepository.countByCozinha(cozinha);
    }

    @GetMapping("/restaurantes/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis (@RequestParam(required = false) String nome) {
        //var comFreteGratis = new RestauranteComFreteGratisEspec();
        //var comNomeSemelhante = new RestauranteComNomeSemelhanteEspec(nome);

        //return restauranteRepository.findAll(comFreteGratis()
        //        .and(comNomeSemelhante(nome)));
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantesPrimeiro () {
        return restauranteRepository.findPrimeiro();
    }

    @GetMapping("/cozinha/primeira")
    public Optional<Cozinha> cozinhaPrimeira () {
        return cozinhaRepository.findPrimeiro();
    }

}
