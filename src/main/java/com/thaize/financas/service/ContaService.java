package com.thaize.financas.service;

import com.thaize.financas.dto.ContaDtoSave;
import com.thaize.financas.model.Conta;

import java.util.List;

public interface ContaService {
    List<Conta> findAll();
    Conta findById(Long id);
    Conta create(ContaDtoSave entity);
    Conta update(Long id, ContaDtoSave entity);
    void delete(Long id);
}
