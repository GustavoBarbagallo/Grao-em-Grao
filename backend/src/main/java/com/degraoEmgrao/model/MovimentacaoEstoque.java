package com.degraoEmgrao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Registra cada entrada ou saída de itens no estoque.
 * Garante rastreabilidade completa do ciclo de vida de cada doação.
 */
@Entity
@Table(name = "movimentacoes_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Item é obrigatório")
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentacao tipo;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser positiva")
    @Column(nullable = false)
    private Double quantidade;

    @Column
    private Long doadorId;        // Preenchido em entradas por doação

    @Column
    private Long familiaId;       // Preenchido em saídas para famílias

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column
    private String operadorResponsavel; // Usuário que registrou

    @Column(updatable = false, nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();

    // -------------------------------------------------------
    // Enum
    // -------------------------------------------------------
    public enum TipoMovimentacao {
        ENTRADA_DOACAO,
        ENTRADA_COMPRA,
        SAIDA_DISTRIBUICAO,
        SAIDA_DESCARTE,
        SAIDA_VENCIMENTO,
        AJUSTE_INVENTARIO
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public Long getDoadorId() { return doadorId; }
    public void setDoadorId(Long doadorId) { this.doadorId = doadorId; }

    public Long getFamiliaId() { return familiaId; }
    public void setFamiliaId(Long familiaId) { this.familiaId = familiaId; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getOperadorResponsavel() { return operadorResponsavel; }
    public void setOperadorResponsavel(String operadorResponsavel) { this.operadorResponsavel = operadorResponsavel; }

    public LocalDateTime getDataHora() { return dataHora; }

    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
}
