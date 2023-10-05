package com.thaize.financas.model;

import com.thaize.financas.dto.ContaDtoSave;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column
    private BigDecimal saldo;

    public Conta(Long id) {
        this.setId(id);
    }

    public Conta(ContaDtoSave contaDtoSave) {
        this.setDescricao(contaDtoSave.getDescricao());
    }


}
