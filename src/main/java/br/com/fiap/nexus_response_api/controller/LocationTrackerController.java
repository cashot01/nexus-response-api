package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.LocationTracker;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
}
