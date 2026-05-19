package com.degraoEmgrao.repository;

import com.degraoEmgrao.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {

    Optional<Doador> findByEmail(String email);
    Optional<Doador> findByCnpjCpf(String cnpjCpf);
    List<Doador> findByTipo(Doador.TipoDoador tipo);
    List<Doador> findByNomeRazaoSocialContainingIgnoreCase(String nome);
}
