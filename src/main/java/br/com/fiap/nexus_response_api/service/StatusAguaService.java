package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.StatusAgua;
import br.com.fiap.nexus_response_api.repository.StatusAguaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusAguaService {

    @Autowired
    private StatusAguaRepository statusAguaRepository;

    // Criar um novo status de água
    public StatusAgua criarStatusAgua(StatusAgua statusAgua) {
        return statusAguaRepository.save(statusAgua);
    }

    // Buscar todos os status de água
    public List<StatusAgua> listarTodos() {
        return statusAguaRepository.findAll();
    }

    // Buscar status de água por ID
    public StatusAgua buscarPorId(Long id) {
        return statusAguaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status de Água não encontrado com ID: " + id));
    }

    // Atualizar status de água por ID
    public StatusAgua atualizarStatusAgua(Long id, StatusAgua statusAtualizado) {
        StatusAgua status = buscarPorId(id);
        status.setDescricaoAgua(statusAtualizado.getDescricaoAgua());
        return statusAguaRepository.save(status);
    }

    // Excluir status de água por ID
    public void excluirStatusAgua(Long id) {
        StatusAgua status = buscarPorId(id);
        statusAguaRepository.delete(status);
    }
}
