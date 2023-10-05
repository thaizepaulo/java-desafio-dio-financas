package com.thaize.financas.dto;

import com.thaize.financas.model.Conta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContaDtoSave {
    private String descricao;

    ContaDtoSave(Conta conta) {
        this.setDescricao(conta.getDescricao());
    }
}
