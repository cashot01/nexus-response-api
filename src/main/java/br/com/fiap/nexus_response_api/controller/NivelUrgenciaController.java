package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.NivelUrgencia;
import br.com.fiap.nexus_response_api.service.NivelUrgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/niveis-urgencia")
@Slf4j
public class NivelUrgenciaController {

    @Autowired
    private NivelUrgenciaService nivelUrgenciaService;

    // Criar um novo nível de urgência
    @PostMapping
    public ResponseEntity<NivelUrgencia> criarNivelUrgencia(@RequestBody NivelUrgencia nivelUrgencia) {
        NivelUrgencia novoNivel = nivelUrgenciaService.criarNivelUrgencia(nivelUrgencia);
        return ResponseEntity.ok(novoNivel);
    }

    @Cacheable("niveis-urgencia")
    @Operation(description = "Listar todos os Niveis Urgencia", tags = "niveis_urgencia", summary = "Lista dos Niveis Urgencias")
    @GetMapping
    public ResponseEntity<List<NivelUrgencia>> listarTodos() {
        List<NivelUrgencia> niveis = nivelUrgenciaService.listarTodos();
        return ResponseEntity.ok(niveis);
    }

    // Buscar nível de urgência por ID
    @GetMapping("/{id}")
    public ResponseEntity<NivelUrgencia> buscarPorId(@PathVariable Long id) {
        NivelUrgencia nivel = nivelUrgenciaService.buscarPorId(id);
        return ResponseEntity.ok(nivel);
    }

    // Atualizar nível de urgência por ID
    @PutMapping("/{id}")
    public ResponseEntity<NivelUrgencia> atualizarNivelUrgencia(@PathVariable Long id, @RequestBody NivelUrgencia nivelAtualizado) {
        NivelUrgencia nivel = nivelUrgenciaService.atualizarNivelUrgencia(id, nivelAtualizado);
        return ResponseEntity.ok(nivel);
    }

    // Excluir nível de urgência por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirNivelUrgencia(@PathVariable Long id) {
        nivelUrgenciaService.excluirNivelUrgencia(id);
        return ResponseEntity.noContent().build();
    }
}
