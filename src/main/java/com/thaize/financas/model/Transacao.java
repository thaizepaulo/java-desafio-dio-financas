package com.thaize.financas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta conta;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime data;

    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    @Column(precision = 13, scale = 2, nullable = false)
    private BigDecimal valor;

}