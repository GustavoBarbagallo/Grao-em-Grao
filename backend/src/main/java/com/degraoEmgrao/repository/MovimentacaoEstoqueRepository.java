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

    List<MovimentacaoEstoque> findByPessoaIdPessoa(Integer idPessoa);

    List<MovimentacaoEstoque> findByTipoMovimentacao(MovimentacaoEstoque.TipoMovimentacao tipo);

    @Query("SELECT m FROM MovimentacaoEstoque m " +
           "WHERE m.dataHoraMovimentacao BETWEEN :inicio AND :fim " +
           "ORDER BY m.dataHoraMovimentacao DESC")
    List<MovimentacaoEstoque> findByPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(m.quantidadeMovimentada), 0) FROM MovimentacaoEstoque m " +
           "WHERE m.tipoMovimentacao = com.degraoEmgrao.model.MovimentacaoEstoque.TipoMovimentacao.SAIDA " +
           "AND m.dataHoraMovimentacao BETWEEN :inicio AND :fim")
    Double totalDistribuidoNoPeriodo(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim);
}
