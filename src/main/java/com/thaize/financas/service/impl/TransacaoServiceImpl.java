package com.thaize.financas.service.impl;

import com.thaize.financas.dto.TransacaoDtoSave;
import com.thaize.financas.model.TipoTransacao;
import com.thaize.financas.model.Transacao;
import com.thaize.financas.repository.ContaRepository;
import com.thaize.financas.repository.TransacaoRepository;
import com.thaize.financas.service.TransacaoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, ContaRepository contaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.contaRepository = contaRepository;
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
    public Transacao create(TransacaoDtoSave transacaoDtoSave) {

        if (transacaoDtoSave.getIdConta() == null) {
            throw new IllegalArgumentException("ID da conta não informado.");
        }

        Transacao transacao = new Transacao(transacaoDtoSave);
        transacao.setConta(contaRepository.findById(transacaoDtoSave.getIdConta())
                .orElseThrow(() -> new IllegalArgumentException("Conta não existe.")));

        if (transacao.getConta().getSaldo() == null) {
            transacao.getConta().setSaldo(BigDecimal.ZERO);
        }
        if (transacao.getTipoTransacao().equals(TipoTransacao.CREDITO)) {
            transacao.getConta().setSaldo(transacao.getConta().getSaldo().add(transacao.getValor()));
        } else {
            transacao.getConta().setSaldo(transacao.getConta().getSaldo().subtract(transacao.getValor()));
        }
        transacao.setConta(contaRepository.save(transacao.getConta()));

        return transacaoRepository.save(transacao);
    }

    @Override
    @Transactional
    public Transacao update(Long id, TransacaoDtoSave transacaoDtoSave) {
        if (id == null) {
            throw new IllegalArgumentException("ID não informado.");
        }

        Transacao transacaoParaSalvar = new Transacao(transacaoDtoSave);
        transacaoParaSalvar.setId(id);

        Transacao transacaoBD = transacaoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não existe."));

        if (transacaoBD.getTipoTransacao().equals(TipoTransacao.CREDITO)) {
            transacaoBD.getConta().setSaldo(transacaoBD.getConta().getSaldo().subtract(transacaoBD.getValor()));
        } else {
            transacaoBD.getConta().setSaldo(transacaoBD.getConta().getSaldo().add(transacaoBD.getValor()));
        }

        if (transacaoParaSalvar.getTipoTransacao().equals(TipoTransacao.CREDITO)) {
            transacaoBD.getConta().setSaldo(transacaoBD.getConta().getSaldo().add(transacaoParaSalvar.getValor()));
        } else {
            transacaoBD.getConta().setSaldo(transacaoBD.getConta().getSaldo().subtract(transacaoParaSalvar.getValor()));
        }

        transacaoBD.setConta(contaRepository.save(transacaoBD.getConta()));

        return transacaoRepository.save(transacaoParaSalvar);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Id não Informado.");
        }
        Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Transação não existe."));
        if (transacao.getTipoTransacao().equals(TipoTransacao.CREDITO)) {
            transacao.getConta().setSaldo(transacao.getConta().getSaldo().subtract(transacao.getValor()));
        } else {
            transacao.getConta().setSaldo(transacao.getConta().getSaldo().add(transacao.getValor()));
        }
        transacao.setConta(contaRepository.save(transacao.getConta()));

        transacaoRepository.delete(transacao);
    }
}