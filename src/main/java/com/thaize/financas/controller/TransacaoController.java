package com.thaize.financas.controller;

import com.thaize.financas.dto.TransacaoDtoSave;
import com.thaize.financas.model.Transacao;
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

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
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
    public ResponseEntity<Transacao> create(@RequestBody TransacaoDtoSave transacaoDtoSave) {
        Transacao transacaoCriada = transacaoService.create(transacaoDtoSave);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(transacaoCriada.getId()).toUri();
        return ResponseEntity.created(location).body(transacaoCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transacao> update(@PathVariable Long id, @RequestBody TransacaoDtoSave transacaoDtoSave) {
        Transacao transacaoAtualizada = transacaoService.update(id, transacaoDtoSave);
        return ResponseEntity.ok(transacaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}