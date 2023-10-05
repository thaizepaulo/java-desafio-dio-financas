package com.thaize.financas.controller;

import com.thaize.financas.dto.ContaDtoSave;
import com.thaize.financas.model.Conta;
import com.thaize.financas.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<Conta>> findAll() {
        List<Conta> contas = contaService.findAll();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conta> findById(@PathVariable Long id) {
        Conta conta = contaService.findById(id);
        return ResponseEntity.ok(conta);
    }

    @PostMapping
    public ResponseEntity<Conta> create(@RequestBody ContaDtoSave contaDtoSave) {
        Conta contaCriada = contaService.create(contaDtoSave);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(contaCriada.getId()).toUri();
        return ResponseEntity.created(location).body(contaCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conta> update(@PathVariable Long id, @RequestBody ContaDtoSave contaDtoSave) {
        Conta contaAtualizada = contaService.update(id, contaDtoSave);
        return ResponseEntity.ok(contaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}