package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

    // FEFO — disponíveis ordenados por validade
    List<Estoque> findByStatusOrderByDataValidadeAsc(Estoque.Status status);

    // Alertas de vencimento
    List<Estoque> findByDataValidadeBeforeAndStatus(LocalDate data, Estoque.Status status);
}