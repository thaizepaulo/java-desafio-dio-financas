package com.thaize.financas.controller;

import com.thaize.financas.model.Conta;
import com.thaize.financas.model.Transacao;
import com.thaize.financas.service.ContaService;
import com.thaize.financas.service.TransacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ContaService contaService;

    public TransacaoController(TransacaoService transacaoService, ContaService contaService) {
        this.transacaoService = transacaoService;
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> findAll() {
        List<Transacao> transacoes = transacaoService.findAll();
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> findById(@PathVariable Long id) {
        Transacao transacao = transacaoService.findById(id);
        return ResponseEntity.ok(transacao);
    }

    @PostMapping
    public ResponseEntity<Transacao> create(@RequestBody Transacao transacao) {
        if (transacao.getConta() == null || transacao.getConta().getId() == null) {
            throw new IllegalArgumentException("Conta não informada.");
        }

        Conta conta = contaService.findById(transacao.getConta().getId());
        if (conta == null) {
            throw new IllegalArgumentException("Conta não existe.");
        }

        transacao.setConta(conta);

        Transacao transacaoCriada = transacaoService.create(transacao);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoCriada.getId()).toUri();
        return ResponseEntity.created(location).body(transacaoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> update(@PathVariable Long id, @RequestBody Transacao transacao) {
        Transacao transacaoAtualizada = transacaoService.update(id, transacao);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}