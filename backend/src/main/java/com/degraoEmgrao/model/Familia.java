package com.degraoEmgrao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa uma família beneficiária cadastrada no banco de alimentos.
 */
@Entity
@Table(name = "familias")
public class Familia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do responsável é obrigatório")
    @Column(nullable = false)
    private String nomeResponsavel;

    @Column(unique = true)
    private String cpfResponsavel;

    @Column
    private String telefone;

    @Column
    private String endereco;

    @Column
    private String bairro;

    @Column
    private String cidade;

    @NotNull(message = "Número de membros é obrigatório")
    @Min(value = 1, message = "Família deve ter ao menos 1 membro")
    @Column(nullable = false)
    private Integer numeroMembros;

    @Column
    private Integer criancas;        // Menores de 12 anos

    @Column
    private Integer adolescentes;    // 12–17 anos

    @Column
    private Integer idosos;          // 60+ anos

    @Column
    private Integer pessoasComDeficiencia;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelVulnerabilidade nivelVulnerabilidade = NivelVulnerabilidade.MEDIO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFamilia status = StatusFamilia.ATIVA;

    @Column
    private LocalDate dataCadastro = LocalDate.now();

    @Column
    private LocalDate dataUltimoAtendimento;

    @Column(updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    // -------------------------------------------------------
    // Enums
    // -------------------------------------------------------
    public enum NivelVulnerabilidade {
        BAIXO,
        MEDIO,
        ALTO,
        CRITICO
    }

    public enum StatusFamilia {
        ATIVA,
        INATIVA,
        EM_ANALISE,
        SUSPENSA
    }

    // -------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }

    public String getCpfResponsavel() { return cpfResponsavel; }
    public void setCpfResponsavel(String cpfResponsavel) { this.cpfResponsavel = cpfResponsavel; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public Integer getNumeroMembros() { return numeroMembros; }
    public void setNumeroMembros(Integer numeroMembros) { this.numeroMembros = numeroMembros; }

    public Integer getCriancas() { return criancas; }
    public void setCriancas(Integer criancas) { this.criancas = criancas; }

    public Integer getAdolescentes() { return adolescentes; }
    public void setAdolescentes(Integer adolescentes) { this.adolescentes = adolescentes; }

    public Integer getIdosos() { return idosos; }
    public void setIdosos(Integer idosos) { this.idosos = idosos; }

    public Integer getPessoasComDeficiencia() { return pessoasComDeficiencia; }
    public void setPessoasComDeficiencia(Integer pessoasComDeficiencia) { this.pessoasComDeficiencia = pessoasComDeficiencia; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public NivelVulnerabilidade getNivelVulnerabilidade() { return nivelVulnerabilidade; }
    public void setNivelVulnerabilidade(NivelVulnerabilidade nivelVulnerabilidade) { this.nivelVulnerabilidade = nivelVulnerabilidade; }

    public StatusFamilia getStatus() { return status; }
    public void setStatus(StatusFamilia status) { this.status = status; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public LocalDate getDataUltimoAtendimento() { return dataUltimoAtendimento; }
    public void setDataUltimoAtendimento(LocalDate dataUltimoAtendimento) { this.dataUltimoAtendimento = dataUltimoAtendimento; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
}
