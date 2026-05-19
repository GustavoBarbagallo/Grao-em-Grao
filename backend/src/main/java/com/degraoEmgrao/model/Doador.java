package com.degraoEmgrao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um doador corporativo ou individual cadastrado na plataforma.
 */
@Entity
@Table(name = "doadores")
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome / Razão Social é obrigatório")
    @Column(nullable = false)
    private String nomeRazaoSocial;

    @Column(unique = true)
    private String cnpjCpf;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String telefone;

    @Column
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDoador tipo = TipoDoador.EMPRESA;

    @Column
    private String responsavel; // Nome do responsável pela doação

    @Column(updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    // -------------------------------------------------------
    // Enum
    // -------------------------------------------------------
    public enum TipoDoador {
        EMPRESA,
        PESSOA_FISICA,
        SUPERMERCADO,
        INDUSTRIA_ALIMENTICIA,
        RESTAURANTE,
        ONG_PARCEIRA
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeRazaoSocial() { return nomeRazaoSocial; }
    public void setNomeRazaoSocial(String nomeRazaoSocial) { this.nomeRazaoSocial = nomeRazaoSocial; }

    public String getCnpjCpf() { return cnpjCpf; }
    public void setCnpjCpf(String cnpjCpf) { this.cnpjCpf = cnpjCpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public TipoDoador getTipo() { return tipo; }
    public void setTipo(TipoDoador tipo) { this.tipo = tipo; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
}
