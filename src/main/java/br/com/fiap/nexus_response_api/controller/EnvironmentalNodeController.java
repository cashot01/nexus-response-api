package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.EnvironmentalNode;
import br.com.fiap.nexus_response_api.model.Usuario;
import br.com.fiap.nexus_response_api.repository.EnvironmentalNodeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*@RestController
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
} */
@RestController
@RequestMapping("/environmental-nodes")
@Slf4j
public class EnvironmentalNodeController {

    @Autowired
    private EnvironmentalNodeRepository repository;

    @GetMapping
    @Cacheable("environmentalNodes")
    @Operation(description = "Listar todos os Environmental Node", tags = "environmental-nodes", summary = "Lista dos Environmentals Node")
    public List<EnvironmentalNode> index(@AuthenticationPrincipal Usuario usuario) {
        log.info("Buscando todos os nós ambientais");
        return repository.findByUsuario(usuario);
    }

    @PostMapping
    @CacheEvict(value = "environmentalNodes", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    })
    public EnvironmentalNode create(@RequestBody @Valid EnvironmentalNode node, @AuthenticationPrincipal Usuario usuario) {
        log.info("Cadastrando nó ambiental com temperatura média: " + node.getTempMedia());
        node.setUsuario(usuario);
        return repository.save(node);
    }

    @GetMapping("{id}")
    public EnvironmentalNode get(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        log.info("Buscando nó ambiental com ID: " + id);
        checkPermission(id, usuario);
        return getNode(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        log.info("Apagando nó ambiental com ID: " + id);
        checkPermission(id, usuario);
        repository.delete(getNode(id));
    }

    @PutMapping("{id}")
    public EnvironmentalNode update(@PathVariable Long id, @RequestBody @Valid EnvironmentalNode node, @AuthenticationPrincipal Usuario usuario) {
        log.info("Atualizando nó ambiental com ID: " + id + " - Nova temperatura média: " + node.getTempMedia());
        checkPermission(id, usuario);
        node.setIdEnvironmentalNode(id);
        node.setUsuario(usuario);
        return repository.save(node);
    }

    private void checkPermission(Long id, Usuario usuario) {
        var nodeOld = getNode(id);
        if (!nodeOld.getUsuario().equals(usuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso.");
        }
    }

    private EnvironmentalNode getNode(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nó ambiental não encontrado"));
    }
}
