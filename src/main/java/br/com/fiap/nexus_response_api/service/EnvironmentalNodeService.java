package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.EnvironmentalNode;
import br.com.fiap.nexus_response_api.repository.EnvironmentalNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalNodeService {

    @Autowired
    private EnvironmentalNodeRepository environmentalNodeRepository;

    // Criar um novo registro
    public EnvironmentalNode criarEnvironmentalNode(EnvironmentalNode node) {
        return environmentalNodeRepository.save(node);
    }

    // Buscar todos os registros
    public List<EnvironmentalNode> listarTodos() {
        return environmentalNodeRepository.findAll();
    }

    // Buscar registro por ID
    public EnvironmentalNode buscarPorId(Long id) {
        return environmentalNodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado com ID: " + id));
    }

    // Atualizar registro por ID
    public EnvironmentalNode atualizarEnvironmentalNode(Long id, EnvironmentalNode nodeAtualizado) {
        EnvironmentalNode node = buscarPorId(id);
        node.setTempMedia(nodeAtualizado.getTempMedia());
        node.setTempDispositivo(nodeAtualizado.getTempDispositivo());
        node.setUmidade(nodeAtualizado.getUmidade());
        node.setNivelAgua(nodeAtualizado.getNivelAgua());
        node.setUsuario(nodeAtualizado.getUsuario());
        node.setLocationTracker(nodeAtualizado.getLocationTracker());
        node.setNivelUrgencia(nodeAtualizado.getNivelUrgencia());
        node.setStatusAgua(nodeAtualizado.getStatusAgua());
        return environmentalNodeRepository.save(node);
    }

    // Excluir registro por ID
    public void excluirEnvironmentalNode(Long id) {
        EnvironmentalNode node = buscarPorId(id);
        environmentalNodeRepository.delete(node);
    }
}
