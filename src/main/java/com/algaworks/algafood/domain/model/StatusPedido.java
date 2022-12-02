package com.algaworks.algafood.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusPedido {
    CRIADO ("Criado"),
    CONFIRMADAO ("Confirmado", CRIADO),
    ENTREGUE ("Entregue", CONFIRMADAO),
    CANCELADO ("Cancelado", CRIADO, CONFIRMADAO);

    private String descricao;
    private List<StatusPedido> statusAnterioresPossives;

    StatusPedido(String descricao, StatusPedido... statusAnterioresPossives) {
        this.descricao = descricao;
        this.statusAnterioresPossives = Arrays.asList(statusAnterioresPossives);
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean naoPodeAlterarPara (StatusPedido novoStatus) {
        return !novoStatus.statusAnterioresPossives.contains(this);
    }
}
