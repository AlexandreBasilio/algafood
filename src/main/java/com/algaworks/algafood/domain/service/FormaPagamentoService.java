package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class FormaPagamentoService {

    public static final String FORMA_DE_PAGAMENTO_EM_USO = "Forma de pagamento em uso";

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento save (FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento searchOuFail (Long id) {
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(id)
                .orElseThrow(()-> new FormaPagamentoNaoEncontradaException(id));

        return formaPagamento;
    }

    @Transactional
    public void remove (Long id) {
        try {
            FormaPagamento formaPagamento = searchOuFail(id);
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();

        } catch (JpaSystemException e) {
            throw new EntidadeEmUsoException(FORMA_DE_PAGAMENTO_EM_USO);
        }  catch (DataIntegrityViolationException e1) {
            throw new EntidadeEmUsoException(FORMA_DE_PAGAMENTO_EM_USO);
        }

    }
}
