package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.ProdutoDTOAssemvbler;
import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ProdutoDTOAssemvbler produtoDTOAssemvbler;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @GetMapping
    public List<ProdutoDTO> listAll () {
        return produtoDTOAssemvbler.toCollectDTO(produtoRepository.findAll());
    }


    @GetMapping("/{id}")
    public ProdutoDTO search (@PathVariable Long id) {
        Produto produto = cadastroProdutoService.searchOrFail(id);
        return produtoDTOAssemvbler.toDTO(produto);
    }
}
