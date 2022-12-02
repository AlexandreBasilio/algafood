package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PemissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroPermissaoService {

    @Autowired
    PermissaoRepository permissaoRepository;

    public Permissao searchOrFail (Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PemissaoNaoEncontradaException(permissaoId));
    }

}
