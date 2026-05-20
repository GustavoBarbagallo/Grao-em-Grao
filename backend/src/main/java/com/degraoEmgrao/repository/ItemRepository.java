package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    // Chave primária é String (gtin)

    // Busca por categoria
    List<Item> findByCategoria(String categoria);

    // Busca por nome parcial
    List<Item> findByNomeProdutoContainingIgnoreCase(String nome);
}