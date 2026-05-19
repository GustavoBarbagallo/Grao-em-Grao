package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {

    List<MovimentacaoEstoque> findByItemIdOrderByDataHoraDesc(Long itemId);
    List<MovimentacaoEstoque> findByTipoOrderByDataHoraDesc(MovimentacaoEstoque.TipoMovimentacao tipo);
    List<MovimentacaoEstoque> findByDoadorIdOrderByDataHoraDesc(Long doadorId);
    List<MovimentacaoEstoque> findByFamiliaIdOrderByDataHoraDesc(Long familiaId);

    @Query("SELECT m FROM MovimentacaoEstoque m WHERE m.dataHora BETWEEN :inicio AND :fim ORDER BY m.dataHora DESC")
    List<MovimentacaoEstoque> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    // Total distribuído no período
    @Query("SELECT COALESCE(SUM(m.quantidade), 0) FROM MovimentacaoEstoque m WHERE m.tipo = 'SAIDA_DISTRIBUICAO' AND m.dataHora BETWEEN :inicio AND :fim")
    Double totalDistribuidoNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
