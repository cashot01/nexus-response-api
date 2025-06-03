package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.StatusAgua;
import br.com.fiap.nexus_response_api.repository.StatusAguaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*@RestController
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
} */
@RestController
@RequestMapping("/water-status")
@Slf4j
public class StatusAguaController {

    @Autowired
    private StatusAguaRepository repository;

    @GetMapping
    @Cacheable("statusAgua")
    @Operation(description = "Listar todos os status da água", tags = "statusAgua", summary = "Lista de status da água")
    public List<StatusAgua> index() {
        log.info("Buscando todos os status da água");
        return repository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "statusAgua", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    })
    public StatusAgua create(@RequestBody @Valid StatusAgua status) {
        log.info("Cadastrando status da água: " + status.getDescricaoAgua());
        return repository.save(status);
    }

    @GetMapping("{id}")
    public StatusAgua get(@PathVariable Long id) {
        log.info("Buscando status da água com ID: " + id);
        return getWaterStatus(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando status da água com ID: " + id);
        repository.delete(getWaterStatus(id));
    }

    @PutMapping("{id}")
    public StatusAgua update(@PathVariable Long id, @RequestBody @Valid StatusAgua status) {
        log.info("Atualizando status da água com ID: " + id + " - Nova descrição: " + status.getDescricaoAgua());
        status.setIdStatusAgua(id);
        return repository.save(status);
    }

    private StatusAgua getWaterStatus(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status da água não encontrado"));
    }
}
