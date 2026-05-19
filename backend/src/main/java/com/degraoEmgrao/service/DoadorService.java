package com.degraoEmgrao.service;

import com.degraoEmgrao.model.Doador;
import com.degraoEmgrao.repository.DoadorRepository;
import com.degraoEmgrao.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DoadorService {

    private final DoadorRepository doadorRepository;
    private final ItemRepository itemRepository;

    public DoadorService(DoadorRepository doadorRepository, ItemRepository itemRepository) {
        this.doadorRepository = doadorRepository;
        this.itemRepository = itemRepository;
    }

    public List<Doador> listarTodos() {
        return doadorRepository.findAll();
    }

    public Optional<Doador> buscarPorId(Long id) {
        return doadorRepository.findById(id);
    }

    public Doador salvar(Doador doador) {
        return doadorRepository.save(doador);
    }

    public Doador atualizar(Long id, Doador atualizado) {
        return doadorRepository.findById(id).map(d -> {
            d.setNomeRazaoSocial(atualizado.getNomeRazaoSocial());
            d.setCnpjCpf(atualizado.getCnpjCpf());
            d.setEmail(atualizado.getEmail());
            d.setTelefone(atualizado.getTelefone());
            d.setEndereco(atualizado.getEndereco());
            d.setTipo(atualizado.getTipo());
            d.setResponsavel(atualizado.getResponsavel());
            return doadorRepository.save(d);
        }).orElseThrow(() -> new RuntimeException("Doador não encontrado: " + id));
    }

    public void deletar(Long id) {
        doadorRepository.deleteById(id);
    }

    /**
     * Gera relatório de rastreabilidade ESG para um doador.
     */
    public Map<String, Object> gerarRelatorioDoador(Long doadorId) {
        Doador doador = doadorRepository.findById(doadorId)
                .orElseThrow(() -> new RuntimeException("Doador não encontrado: " + doadorId));

        var itens = itemRepository.findByDoadorId(doadorId);
        double totalDoado = itens.stream().mapToDouble(i -> i.getQuantidade()).sum();

        Map<String, Object> relatorio = new HashMap<>();
        relatorio.put("doador", doador);
        relatorio.put("totalItensDoados", itens.size());
        relatorio.put("quantidadeTotalDoada", totalDoado);
        relatorio.put("itens", itens);
        return relatorio;
    }
}
