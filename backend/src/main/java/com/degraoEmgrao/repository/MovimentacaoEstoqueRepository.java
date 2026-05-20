package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {

    // Busca por pessoa
    List<MovimentacaoEstoque> findByPessoaIdPessoa(Integer idPessoa);

    // Busca por tipo (ENTRADA ou SAIDA)
    List<MovimentacaoEstoque> findByTipoMovimentacao(MovimentacaoEstoque.TipoMovimentacao tipo);

    // Busca por período
    @Query("SELECT m FROM MovimentacaoEstoque m " +
           "WHERE m.dataHoraMovimentacao BETWEEN :inicio AND :fim " +
           "ORDER BY m.dataHoraMovimentacao DESC")
    List<MovimentacaoEstoque> findByPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);

    // Total distribuído no período (saídas)
    @Query("SELECT COALESCE(SUM(m.quantidadeMovimentada), 0) FROM MovimentacaoEstoque m " +
           "WHERE m.tipoMovimentacao = 'SAIDA' " +
           "AND m.dataHoraMovimentacao BETWEEN :inicio AND :fim")
    Double totalDistribuidoNoPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}