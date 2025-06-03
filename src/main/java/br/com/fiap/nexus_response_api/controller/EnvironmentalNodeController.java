package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.EnvironmentalNode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/environmental")
@Slf4j
public class EnvironmentalNodeController {

    @Autowired
    private EnvironmentalNodeService environmentalNodeService;


    @PostMapping
    public ResponseEntity<EnvironmentalNode> criarEnvironmentalNode(@RequestBody EnvironmentalNode node) {
        EnvironmentalNode novoNode = environmentalNodeService.criarEnvironmentalNode(node);
        return ResponseEntity.ok(novoNode);
    }

    @Cacheable("environmental")
    @Operation(description = "Listar todos os Environmental Nodes", tags = "environmental", summary = "Lista dos Environmental Nodes")
    @GetMapping
    public ResponseEntity<List<EnvironmentalNode>> listarTodos() {
        List<EnvironmentalNode> nodes = environmentalNodeService.listarTodos();
        return ResponseEntity.ok(nodes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EnvironmentalNode> buscarPorId(@PathVariable Long id) {
        EnvironmentalNode node = environmentalNodeService.buscarPorId(id);
        return ResponseEntity.ok(node);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EnvironmentalNode> atualizarEnvironmentalNode(@PathVariable Long id, @RequestBody EnvironmentalNode nodeAtualizado) {
        EnvironmentalNode node = environmentalNodeService.atualizarEnvironmentalNode(id, nodeAtualizado);
        return ResponseEntity.ok(node);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEnvironmentalNode(@PathVariable Long id) {
        environmentalNodeService.excluirEnvironmentalNode(id);
        return ResponseEntity.noContent().build();
    }
}
