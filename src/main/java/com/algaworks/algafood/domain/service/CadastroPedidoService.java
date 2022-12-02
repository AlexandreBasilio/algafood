package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoDisponivelParaRestauranteException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    CadastroProdutoService cadastroProdutoService;

    @Autowired
    CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    CadastroCidadeServico cadastroCidadeServico;

    @Autowired
    RestauranteRepository restauranteRepository;

    public Pedido serarchOrFail (Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validaPedido(pedido);
        validaItensPedido(pedido);
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }


    public void validaPedido (Pedido pedido) {

        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
        Usuario cliente = cadastroUsuarioService.searcrOrFail(pedido.getCliente().getId());
        FormaPagamento formaPagamento = formaPagamentoService.searchOuFail(pedido.getFormaPagamento().getId());
        Cidade cidade = cadastroCidadeServico.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());

        Restaurante restaurante1 = restauranteRepository.
                findByIdAndFormasPagamento(restaurante.getId(), formaPagamento);
        if (restaurante1 == null) {
            throw new FormaPagamentoNaoDisponivelParaRestauranteException(pedido.getFormaPagamento().getId(),
                    restaurante.getId());
        }

        pedido.setRestaurante(restaurante);
        pedido.setTaxaFrete(restaurante.getTaxaFrete());
        pedido.setFormaPagamento(formaPagamento);
        pedido.setCliente(cliente);
        pedido.getEnderecoEntrega().setCidade(cidade);
    }

    private void validaItensPedido(Pedido pedido) {
        pedido.getItens().forEach(itemPedido -> {
            Produto produto = cadastroProdutoService.searchOrFail(
                    itemPedido.getProduto().getId(), pedido.getRestaurante().getId());

            itemPedido.setProduto(produto);
            itemPedido.setPedido(pedido);
            itemPedido.setPrecoUnitario(produto.getPreco());
        });
    }

}
