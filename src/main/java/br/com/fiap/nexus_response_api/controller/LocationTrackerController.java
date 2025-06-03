package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.LocationTracker;
import br.com.fiap.nexus_response_api.model.Usuario;
import br.com.fiap.nexus_response_api.repository.LocationTrackerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/locations")
@Slf4j
public class LocationTrackerController {

    @Autowired
    private LocationTrackerService locationTrackerService;

    // Criar uma nova localização
    @PostMapping
    public ResponseEntity<LocationTracker> criarLocation(@RequestBody LocationTracker location) {
        LocationTracker novaLocation = locationTrackerService.criarLocation(location);
        return ResponseEntity.ok(novaLocation);
    }

    @Cacheable("locations")
    @Operation(description = "Listar todos os Locations Trackers", tags = "location", summary = "Lista dos Locations Tracker")
    @GetMapping
    public ResponseEntity<List<LocationTracker>> listarTodas() {
        List<LocationTracker> locations = locationTrackerService.listarTodas();
        return ResponseEntity.ok(locations);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LocationTracker> buscarPorId(@PathVariable Long id) {
        LocationTracker location = locationTrackerService.buscarPorId(id);
        return ResponseEntity.ok(location);
    }


    @PutMapping("/{id}")
    public ResponseEntity<LocationTracker> atualizarLocation(@PathVariable Long id, @RequestBody LocationTracker locationAtualizada) {
        LocationTracker location = locationTrackerService.atualizarLocation(id, locationAtualizada);
        return ResponseEntity.ok(location);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirLocation(@PathVariable Long id) {
        locationTrackerService.excluirLocation(id);
        return ResponseEntity.noContent().build();
    }
} */
@RestController
@RequestMapping("/locations")
@Slf4j
public class LocationTrackerController {

    @Autowired
    private LocationTrackerRepository repository;

    @GetMapping
    @Cacheable("locations")
    @Operation(description = "Listar todos Location Trackers", tags = "locations", summary = "Lista dos Location Tracker")
    public List<LocationTracker> index(@AuthenticationPrincipal Usuario usuario) {
        log.info("Buscando todos os Location Trackers");
        return repository.findByUsuario(usuario);
    }

    @PostMapping
    @CacheEvict(value = "locations", allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(responses = {
            @ApiResponse(responseCode = "400", description = "Falha na validação")
    })
    public LocationTracker create(@RequestBody @Valid LocationTracker location, @AuthenticationPrincipal Usuario usuario) {
        log.info("Cadastrando localização com latitude: " + location.getLatitude());
        location.setUsuario(usuario);
        return repository.save(location);
    }

    @GetMapping("{id}")
    public LocationTracker get(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        log.info("Buscando localização com ID: " + id);
        checkPermission(id, usuario);
        return getLocation(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        log.info("Apagando localização com ID: " + id);
        checkPermission(id, usuario);
        repository.delete(getLocation(id));
    }

    @PutMapping("{id}")
    public LocationTracker update(@PathVariable Long id, @RequestBody @Valid LocationTracker location, @AuthenticationPrincipal Usuario usuario) {
        log.info("Atualizando localização com ID: " + id + " - Nova latitude: " + location.getLatitude());
        checkPermission(id, usuario);
        location.setIdLocation(id);
        location.setUsuario(usuario);
        return repository.save(location);
    }

    private void checkPermission(Long id, Usuario usuario) {
        var locationOld = getLocation(id);
        if (!locationOld.getUsuario().equals(usuario)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para acessar este recurso.");
        }
    }

    private LocationTracker getLocation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Localização não encontrada"));
    }
}
