package com.degraoEmgrao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Estoque")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Integer idEstoque;

    @ManyToOne
    @JoinColumn(name = "gtin")
    private Item item;

    @Column(name = "quantidade_atual")
    private BigDecimal quantidadeAtual;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "data_entrada")
    private LocalDateTime dataEntrada;

    private String localizacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status { DISPONIVEL, VENCIDO, DESTINADO }

    public Integer getIdEstoque() { return idEstoque; }
    public void setIdEstoque(Integer idEstoque) { this.idEstoque = idEstoque; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public BigDecimal getQuantidadeAtual() { return quantidadeAtual; }
    public void setQuantidadeAtual(BigDecimal quantidadeAtual) { this.quantidadeAtual = quantidadeAtual; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}