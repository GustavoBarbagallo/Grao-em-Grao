package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Familia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

    Optional<Familia> findByCpfResponsavel(String cpf);
    List<Familia> findByStatus(Familia.StatusFamilia status);
    List<Familia> findByNivelVulnerabilidade(Familia.NivelVulnerabilidade nivel);
    List<Familia> findByNomeResponsavelContainingIgnoreCase(String nome);

    // Total de pessoas atendidas (soma de membros de famílias ativas)
    @Query("SELECT COALESCE(SUM(f.numeroMembros), 0) FROM Familia f WHERE f.status = 'ATIVA'")
    Long totalPessoasAtendidas();

    // Contagem por nível de vulnerabilidade
    @Query("SELECT f.nivelVulnerabilidade, COUNT(f) FROM Familia f WHERE f.status = 'ATIVA' GROUP BY f.nivelVulnerabilidade")
    List<Object[]> contarPorVulnerabilidade();
}
