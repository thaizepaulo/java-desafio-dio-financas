package com.thaize.financas.service;

import com.thaize.financas.dto.TransacaoDtoSave;
import com.thaize.financas.model.Transacao;

import java.util.List;

public interface TransacaoService {
    List<Transacao> findAll();
    Transacao findById(Long id);
    Transacao create(TransacaoDtoSave transacaoDtoSave);
    Transacao update(Long id, TransacaoDtoSave entityTransacaoDtoSave);
    void delete(Long id);
}
