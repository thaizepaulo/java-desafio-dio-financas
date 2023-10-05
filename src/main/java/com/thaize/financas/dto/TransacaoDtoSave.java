package com.thaize.financas.dto;

import com.thaize.financas.model.TipoTransacao;
import com.thaize.financas.model.Transacao;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransacaoDtoSave {
    private Long idConta;
    private String descricao;
    private LocalDateTime data;
    private TipoTransacao tipoTransacao;
    private BigDecimal valor;

    TransacaoDtoSave(Transacao transacao) {
        this.setIdConta(transacao.getConta().getId());
        this.setDescricao(transacao.getDescricao());
        this.setData(transacao.getData());
        this.setTipoTransacao(transacao.getTipoTransacao());
        this.setValor(transacao.getValor());
    }
}
