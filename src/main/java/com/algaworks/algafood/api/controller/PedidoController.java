package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoDisponivelParaRestauranteException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    CadastroPedidoService cadastroPedidoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    PedidoDTOAssembler pedidoDTOAssembler;

    @Autowired
    PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @Autowired
    PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;


    @GetMapping
    public List<PedidoResumoDTO> listAll () {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidoResumoDTOAssembler.toCollectDTO(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO search (@PathVariable Long pedidoId) {
        Pedido pedido = cadastroPedidoService.serarchOrFail(pedidoId);

        return pedidoDTOAssembler.toDTO(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO add (@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
            //TODO - set client (sera autenticado antes)
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            pedido = cadastroPedidoService.emitir(pedido);

            return pedidoDTOAssembler.toDTO(cadastroPedidoService.emitir(pedido));
        } catch (EntidadeNaoEncontradaException e) {
            throw  new NegocioException(e.getMessage(), e.getCause());
        }
    }

}
