package com.degraoEmgrao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa um item no inventário do banco de alimentos.
 * Cada registro corresponde a um lote de um produto específico.
 */
@Entity
@Table(name = "itens_inventario")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false)
    private String nomeProduto;

    @NotNull(message = "Categoria é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaItem categoria;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    @Column(nullable = false)
    private Double quantidade;

    @NotBlank(message = "Unidade de medida é obrigatória")
    @Column(nullable = false)
    private String unidadeMedida; // kg, unidade, litro, caixa, etc.

    @NotNull(message = "Data de validade é obrigatória")
    @Column(nullable = false)
    private LocalDate dataValidade;

    @Column
    private LocalDate dataDoacao;

    @Column
    private String lote;

    // Referência ao doador — FK para tabela de doadores (a ser configurada)
    @Column(name = "doador_id")
    private Long doadorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusItem status = StatusItem.DISPONIVEL;

    @Column(updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    @Column
    private LocalDateTime atualizadoEm = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    // -------------------------------------------------------
    // Enums internos
    // -------------------------------------------------------
    public enum CategoriaItem {
        GRAOS_CEREAIS,
        LATICINIOS,
        CARNES_PROTEINAS,
        FRUTAS_VERDURAS,
        ENLATADOS_CONSERVAS,
        OLEOS_GORDURAS,
        BEBIDAS,
        PAES_MASSAS,
        TEMPEROS_CONDIMENTOS,
        OUTROS
    }

    public enum StatusItem {
        DISPONIVEL,
        RESERVADO,
        DISTRIBUIDO,
        DESCARTADO,
        VENCIDO
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public CategoriaItem getCategoria() { return categoria; }
    public void setCategoria(CategoriaItem categoria) { this.categoria = categoria; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public LocalDate getDataDoacao() { return dataDoacao; }
    public void setDataDoacao(LocalDate dataDoacao) { this.dataDoacao = dataDoacao; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public Long getDoadorId() { return doadorId; }
    public void setDoadorId(Long doadorId) { this.doadorId = doadorId; }

    public StatusItem getStatus() { return status; }
    public void setStatus(StatusItem status) { this.status = status; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
}
