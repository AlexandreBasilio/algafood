package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    CadastroPedidoService cadastroPedidoService;

    @Transactional
    public void confirmar (Long pedidoId) {
        Pedido pedido = cadastroPedidoService.serarchOrFail(pedidoId);
        pedido.confirmar();
    }

    @Transactional
    public void entregar (Long pedidoId) {
        Pedido pedido = cadastroPedidoService.serarchOrFail(pedidoId);
        pedido.entregar();
    }

    @Transactional
    public void cancelar (Long pedidoId) {
        Pedido pedido = cadastroPedidoService.serarchOrFail(pedidoId);
        pedido.cancelar();
    }
}
