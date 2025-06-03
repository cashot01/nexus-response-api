package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.StatusAgua;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status-agua")
@Slf4j
public class StatusAguaController {

    @Autowired
    private StatusAguaService statusAguaService;

    // Criar um novo status de água
    @PostMapping
    public ResponseEntity<StatusAgua> criarStatusAgua(@RequestBody StatusAgua statusAgua) {
        StatusAgua novoStatus = statusAguaService.criarStatusAgua(statusAgua);
        return ResponseEntity.ok(novoStatus);
    }

    @Cacheable("status-agua")
    @Operation(description = "Listar todos os Status Agua", tags = "status-agua", summary = "Lista dos Status Agua")
    @GetMapping
    public ResponseEntity<List<StatusAgua>> listarTodos() {
        List<StatusAgua> statusList = statusAguaService.listarTodos();
        return ResponseEntity.ok(statusList);
    }

    // Buscar status de água por ID
    @GetMapping("/{id}")
    public ResponseEntity<StatusAgua> buscarPorId(@PathVariable Long id) {
        StatusAgua status = statusAguaService.buscarPorId(id);
        return ResponseEntity.ok(status);
    }

    // Atualizar status de água por ID
    @PutMapping("/{id}")
    public ResponseEntity<StatusAgua> atualizarStatusAgua(@PathVariable Long id, @RequestBody StatusAgua statusAtualizado) {
        StatusAgua status = statusAguaService.atualizarStatusAgua(id, statusAtualizado);
        return ResponseEntity.ok(status);
    }

    // Excluir status de água por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirStatusAgua(@PathVariable Long id) {
        statusAguaService.excluirStatusAgua(id);
        return ResponseEntity.noContent().build();
    }
}
