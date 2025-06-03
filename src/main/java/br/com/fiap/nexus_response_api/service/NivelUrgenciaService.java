package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.NivelUrgencia;
import br.com.fiap.nexus_response_api.repository.NivelUrgenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NivelUrgenciaService {

    @Autowired
    private NivelUrgenciaRepository nivelUrgenciaRepository;

    // Criar um novo nível de urgência
    public NivelUrgencia criarNivelUrgencia(NivelUrgencia nivelUrgencia) {
        return nivelUrgenciaRepository.save(nivelUrgencia);
    }

    // Buscar todos os níveis de urgência
    public List<NivelUrgencia> listarTodos() {
        return nivelUrgenciaRepository.findAll();
    }

    // Buscar nível de urgência por ID
    public NivelUrgencia buscarPorId(Long id) {
        return nivelUrgenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nível de Urgência não encontrado com ID: " + id));
    }

    // Atualizar nível de urgência por ID
    public NivelUrgencia atualizarNivelUrgencia(Long id, NivelUrgencia nivelAtualizado) {
        NivelUrgencia nivel = buscarPorId(id);
        nivel.setDescricaoNivel(nivelAtualizado.getDescricaoNivel());
        return nivelUrgenciaRepository.save(nivel);
    }

    // Excluir nível de urgência por ID
    public void excluirNivelUrgencia(Long id) {
        NivelUrgencia nivel = buscarPorId(id);
        nivelUrgenciaRepository.delete(nivel);
    }
}
