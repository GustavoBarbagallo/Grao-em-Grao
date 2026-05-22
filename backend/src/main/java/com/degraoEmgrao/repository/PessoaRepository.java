package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    Optional<Pessoa> findByDocumentoLogin(String documentoLogin);

    @Query("SELECT p FROM Pessoa p WHERE p.papel LIKE %:papel%")
    List<Pessoa> findByPapel(@Param("papel") String papel);

    List<Pessoa> findByNivelAcesso(Pessoa.NivelAcesso nivelAcesso);
}
