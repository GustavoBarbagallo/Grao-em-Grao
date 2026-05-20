package com.degraoEmgrao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Movimentacoes")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Integer idMovimentacao;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "id_estoque")
    private Estoque estoque;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;

    @Column(name = "quantidade_movimentada")
    private BigDecimal quantidadeMovimentada;

    @Column(name = "data_hora_movimentacao")
    private LocalDateTime dataHoraMovimentacao;

    private String observacao;

    public enum TipoMovimentacao { ENTRADA, SAIDA }

    public Integer getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(Integer idMovimentacao) { this.idMovimentacao = idMovimentacao; }

    public Pessoa getPessoa() { return pessoa; }
    public void setPessoa(Pessoa pessoa) { this.pessoa = pessoa; }

    public Estoque getEstoque() { return estoque; }
    public void setEstoque(Estoque estoque) { this.estoque = estoque; }

    public TipoMovimentacao getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }

    public BigDecimal getQuantidadeMovimentada() { return quantidadeMovimentada; }
    public void setQuantidadeMovimentada(BigDecimal quantidadeMovimentada) { this.quantidadeMovimentada = quantidadeMovimentada; }

    public LocalDateTime getDataHoraMovimentacao() { return dataHoraMovimentacao; }
    public void setDataHoraMovimentacao(LocalDateTime dataHoraMovimentacao) { this.dataHoraMovimentacao = dataHoraMovimentacao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}