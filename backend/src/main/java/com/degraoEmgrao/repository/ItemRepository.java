package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // Busca itens por categoria
    List<Item> findByCategoriaAndStatus(Item.CategoriaItem categoria, Item.StatusItem status);

    // Busca itens por status
    List<Item> findByStatus(Item.StatusItem status);

    // FEFO: retorna itens disponíveis ordenados por validade (mais próximo primeiro)
    List<Item> findByStatusOrderByDataValidadeAsc(Item.StatusItem status);

    // Alerta de vencimento: itens com validade até X dias
    @Query("SELECT i FROM Item i WHERE i.status = 'DISPONIVEL' AND i.dataValidade <= :dataLimite ORDER BY i.dataValidade ASC")
    List<Item> findItensProximosDoVencimento(@Param("dataLimite") LocalDate dataLimite);

    // Itens já vencidos
    @Query("SELECT i FROM Item i WHERE i.status = 'DISPONIVEL' AND i.dataValidade < :hoje")
    List<Item> findItensVencidos(@Param("hoje") LocalDate hoje);

    // Contagem por categoria (para dashboard)
    @Query("SELECT i.categoria, SUM(i.quantidade) FROM Item i WHERE i.status = 'DISPONIVEL' GROUP BY i.categoria")
    List<Object[]> contarQuantidadePorCategoria();

    // Total de itens disponíveis
    @Query("SELECT COUNT(i) FROM Item i WHERE i.status = 'DISPONIVEL'")
    Long contarItensDisponiveis();

    // Busca por nome do produto (pesquisa parcial)
    List<Item> findByNomeProdutoContainingIgnoreCaseAndStatus(String nome, Item.StatusItem status);

    // Itens de um doador específico
    List<Item> findByDoadorId(Long doadorId);
}
