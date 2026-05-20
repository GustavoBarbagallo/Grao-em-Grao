package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    // Login pelo documento (CPF ou CNPJ)
    Optional<Pessoa> findByDocumentoLogin(String documentoLogin);

    // Buscar por papel: DOADOR, RECEPTOR, VOLUNTARIO
    @Query("SELECT p FROM Pessoa p WHERE p.papel LIKE %:papel%")
    List<Pessoa> findByPapel(String papel);

    // Buscar por nível de acesso
    List<Pessoa> findByNivelAcesso(Pessoa.NivelAcesso nivelAcesso);
}