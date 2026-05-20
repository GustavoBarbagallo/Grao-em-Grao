package com.degraoEmgrao.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Itens")
public class Item {

    @Id
    @Column(name = "gtin", length = 20)
    private String gtin;

    @Column(name = "nome_produto")
    private String nomeProduto;

    private String categoria;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

    public String getGtin() { return gtin; }
    public void setGtin(String gtin) { this.gtin = gtin; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }
}