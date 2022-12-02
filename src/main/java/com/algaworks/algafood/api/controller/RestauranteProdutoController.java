package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssemvbler;
import com.algaworks.algafood.api.assembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoDTOAssemvbler produtoDTOAssemvbler;

    @Autowired
    ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoDTO> listAll (@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        List<Produto> produtos = produtoRepository.findByRestaurante(restaurante);
        return produtoDTOAssemvbler.toCollectDTO(produtos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO search (@PathVariable Long restauranteId, @PathVariable Long produtoId) {
//        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
//        Produto produto = cadastroProdutoService.searchOrFail(produtoId, restaurante);
        Produto produto = cadastroProdutoService.searchOrFail(produtoId, restauranteId);

        return produtoDTOAssemvbler.toDTO(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO add (@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        return produtoDTOAssemvbler.toDTO(cadastroProdutoService.save(produto));
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove (@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        cadastroProdutoService.remove(produtoId, restauranteId);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO update (@PathVariable Long  produtoId,
                              @PathVariable Long restauranteId,
                              @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = cadastroProdutoService.searchOrFail(produtoId, restauranteId);
        produtoInputDisassembler.copyToDomainModel(produtoInput, produto);

        return produtoDTOAssemvbler.toDTO(cadastroProdutoService.save(produto));
    }
}
