package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.NivelUrgencia;
import br.com.fiap.nexus_response_api.repository.NivelUrgenciaRepository;
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
} */
@RestController
@RequestMapping("/urgency-levels")
@Slf4j
public class NivelUrgenciaController {

    @Autowired
    private NivelUrgenciaRepository repository;

    @GetMapping
    @Cacheable("urgencyLevels")
    @Operation(description = "Listar todos os níveis de urgência", tags = "urgency-levels", summary = "Lista de níveis de urgência")
    public List<NivelUrgencia> index() {
        log.info("Buscando todos os níveis de urgência");
        return repository.findAll();
    }

    @PostMapping
    @CacheEvict(value = "urgencyLevels", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    })
    public NivelUrgencia create(@RequestBody @Valid NivelUrgencia nivel) {
        log.info("Cadastrando nível de urgência: " + nivel.getDescricaoNivel());
        return repository.save(nivel);
    }

    @GetMapping("{id}")
    public NivelUrgencia get(@PathVariable Long id) {
        log.info("Buscando nível de urgência com ID: " + id);
        return getUrgencyLevel(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("Apagando nível de urgência com ID: " + id);
        repository.delete(getUrgencyLevel(id));
    }

    @PutMapping("{id}")
    public NivelUrgencia update(@PathVariable Long id, @RequestBody @Valid NivelUrgencia nivel) {
        log.info("Atualizando nível de urgência com ID: " + id + " - Nova descrição: " + nivel.getDescricaoNivel());
        nivel.setIdNivelUrgencia(id);
        return repository.save(nivel);
    }

    private NivelUrgencia getUrgencyLevel(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nível de urgência não encontrado"));
    }
}
