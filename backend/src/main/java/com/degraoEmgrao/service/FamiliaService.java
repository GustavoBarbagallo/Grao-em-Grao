package com.degraoEmgrao.service;

import com.degraoEmgrao.model.Familia;
import com.degraoEmgrao.repository.FamiliaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FamiliaService {

    private final FamiliaRepository familiaRepository;

    public FamiliaService(FamiliaRepository familiaRepository) {
        this.familiaRepository = familiaRepository;
    }

    public List<Familia> listarTodas() {
        return familiaRepository.findAll();
    }

    public List<Familia> listarAtivas() {
        return familiaRepository.findByStatus(Familia.StatusFamilia.ATIVA);
    }

    public Optional<Familia> buscarPorId(Long id) {
        return familiaRepository.findById(id);
    }

    public Familia salvar(Familia familia) {
        return familiaRepository.save(familia);
    }

    public Familia atualizar(Long id, Familia atualizada) {
        return familiaRepository.findById(id).map(f -> {
            f.setNomeResponsavel(atualizada.getNomeResponsavel());
            f.setCpfResponsavel(atualizada.getCpfResponsavel());
            f.setTelefone(atualizada.getTelefone());
            f.setEndereco(atualizada.getEndereco());
            f.setBairro(atualizada.getBairro());
            f.setCidade(atualizada.getCidade());
            f.setNumeroMembros(atualizada.getNumeroMembros());
            f.setCriancas(atualizada.getCriancas());
            f.setAdolescentes(atualizada.getAdolescentes());
            f.setIdosos(atualizada.getIdosos());
            f.setPessoasComDeficiencia(atualizada.getPessoasComDeficiencia());
            f.setObservacoes(atualizada.getObservacoes());
            f.setNivelVulnerabilidade(atualizada.getNivelVulnerabilidade());
            f.setStatus(atualizada.getStatus());
            return familiaRepository.save(f);
        }).orElseThrow(() -> new RuntimeException("Família não encontrada: " + id));
    }

    public void registrarAtendimento(Long id) {
        familiaRepository.findById(id).ifPresent(f -> {
            f.setDataUltimoAtendimento(LocalDate.now());
            familiaRepository.save(f);
        });
    }

    public void deletar(Long id) {
        familiaRepository.deleteById(id);
    }

    public Map<String, Object> obterIndicadores() {
        Map<String, Object> dados = new HashMap<>();
        dados.put("totalFamiliasAtivas", familiaRepository.findByStatus(Familia.StatusFamilia.ATIVA).size());
        dados.put("totalPessoasAtendidas", familiaRepository.totalPessoasAtendidas());

        List<Object[]> porVulnerabilidade = familiaRepository.contarPorVulnerabilidade();
        Map<String, Long> vulMap = new HashMap<>();
        for (Object[] row : porVulnerabilidade) {
            vulMap.put(row[0].toString(), ((Number) row[1]).longValue());
        }
        dados.put("porVulnerabilidade", vulMap);
        return dados;
    }
}
