package com.thaize.financas.service.impl;

import com.thaize.financas.model.Transacao;
import com.thaize.financas.repository.TransacaoRepository;
import com.thaize.financas.service.TransacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Transacao findById(Long id) {
        return transacaoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public Transacao create(Transacao transacao) {
        if (transacao.getId() != null && transacaoRepository.existsById(transacao.getId())) {
            throw new IllegalArgumentException("Transação já existe.");
        }

        return transacaoRepository.save(transacao);
    }

    @Override
    @Transactional
    public Transacao update(Long id, Transacao transacao) {
        if (id == null || !transacaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Transação não existe.");
        }

        transacao.setId(id);
        return transacaoRepository.save(transacao);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id não Informado.");
        }
        Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transação não existe."));
        transacaoRepository.delete(transacao);
    }
}