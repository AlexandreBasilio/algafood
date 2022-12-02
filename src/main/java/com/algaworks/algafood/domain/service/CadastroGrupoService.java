package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroGrupoService {

    public static final String MSG_GRUPO_EM_USO = "Grupo %s usado. Nao pode ser removido";

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    CadastroPermissaoService cadastroPermissaoService;

    @Transactional
    public Grupo save (Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public Grupo searchOrFail (Long id) {
        return grupoRepository.findById(id).orElseThrow(()-> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public void remove (Long id) {
        searchOrFail(id);
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (JpaSystemException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        } catch (DataIntegrityViolationException e1) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = searchOrFail(grupoId);
        Permissao permissao = cadastroPermissaoService.searchOrFail(permissaoId);
        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = searchOrFail(grupoId);
        Permissao permissao = cadastroPermissaoService.searchOrFail(permissaoId);
        grupo.removerPermissao(permissao);
    }
}
