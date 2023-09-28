package com.thaize.financas.service.impl;

import com.thaize.financas.model.Conta;
import com.thaize.financas.repository.ContaRepository;
import com.thaize.financas.service.ContaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    public ContaServiceImpl(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    @Transactional (readOnly = true)
    public Conta findById(Long id) {
        return contaRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional
    public Conta create(Conta conta) {
        if (conta.getId() != null && contaRepository.existsById(conta.getId())) {
            throw new IllegalArgumentException("Conta já existe.");
        }

        return contaRepository.save(conta);
    }

    @Override
    @Transactional
    public Conta update(Long id, Conta conta) {
        if (id == null || !contaRepository.existsById(id)) {
            throw new IllegalArgumentException("Conta não existe.");
        }

        conta.setId(id);
        return contaRepository.save(conta);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Conta conta = null;

        if (id == null) {
            throw new IllegalArgumentException("Id não Informado.");
        }
        conta = contaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Conta não existe."));
        contaRepository.delete(conta);
    }
}