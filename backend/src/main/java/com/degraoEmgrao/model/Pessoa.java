package com.degraoEmgrao.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pessoas")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pessoa")
    private Integer idPessoa;

    private String nome;
    private String email;
    private String telefone;

    @Column(name = "documento_login")
    private String documentoLogin;

    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    private String papel;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_acesso")
    private NivelAcesso nivelAcesso;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Enumerated(EnumType.STRING)
    private Recorrencia recorrencia;

    @Column(name = "limite_mensal_kg")
    private BigDecimal limiteMensalKg;

    @Column(name = "prazo_atendimento")
    private LocalDate prazoAtendimento;

    @Column(name = "ultima_entrega")
    private LocalDate ultimaEntrega;

    public enum TipoPessoa { PF, PJ }
    public enum NivelAcesso { ADMIN, OTHER }
    public enum Recorrencia { MENSAL, QUINZENAL, EVENTUAL }

    public Integer getIdPessoa() { return idPessoa; }
    public void setIdPessoa(Integer idPessoa) { this.idPessoa = idPessoa; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getDocumentoLogin() { return documentoLogin; }
    public void setDocumentoLogin(String documentoLogin) { this.documentoLogin = documentoLogin; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public TipoPessoa getTipoPessoa() { return tipoPessoa; }
    public void setTipoPessoa(TipoPessoa tipoPessoa) { this.tipoPessoa = tipoPessoa; }

    public String getPapel() { return papel; }
    public void setPapel(String papel) { this.papel = papel; }

    public NivelAcesso getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(NivelAcesso nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public Recorrencia getRecorrencia() { return recorrencia; }
    public void setRecorrencia(Recorrencia recorrencia) { this.recorrencia = recorrencia; }

    public BigDecimal getLimiteMensalKg() { return limiteMensalKg; }
    public void setLimiteMensalKg(BigDecimal limiteMensalKg) { this.limiteMensalKg = limiteMensalKg; }

    public LocalDate getPrazoAtendimento() { return prazoAtendimento; }
    public void setPrazoAtendimento(LocalDate prazoAtendimento) { this.prazoAtendimento = prazoAtendimento; }

    public LocalDate getUltimaEntrega() { return ultimaEntrega; }
    public void setUltimaEntrega(LocalDate ultimaEntrega) { this.ultimaEntrega = ultimaEntrega; }
}