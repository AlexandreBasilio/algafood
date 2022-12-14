package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

    @Autowired
    FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar (@PathVariable Long pedidoId) {
        fluxoPedidoService.confirmar(pedidoId);
    }

    @PutMapping("/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entregar (@PathVariable Long pedidoId) {
        fluxoPedidoService.entregar(pedidoId);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar (@PathVariable Long pedidoId) {
        fluxoPedidoService.cancelar(pedidoId);
    }
}
