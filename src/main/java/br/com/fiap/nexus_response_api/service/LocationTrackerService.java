package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.LocationTracker;
import br.com.fiap.nexus_response_api.repository.LocationTrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationTrackerService {

    @Autowired
    private LocationTrackerRepository locationTrackerRepository;

    // Criar uma nova localização
    public LocationTracker criarLocation(LocationTracker location) {
        return locationTrackerRepository.save(location);
    }

    // Buscar todas as localizações
    public List<LocationTracker> listarTodas() {
        return locationTrackerRepository.findAll();
    }

    // Buscar localização por ID
    public LocationTracker buscarPorId(Long id) {
        return locationTrackerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Localização não encontrada com ID: " + id));
    }

    // Atualizar localização por ID
    public LocationTracker atualizarLocation(Long id, LocationTracker locationAtualizada) {
        LocationTracker location = buscarPorId(id);
        location.setLatitude(locationAtualizada.getLatitude());
        location.setLongitude(locationAtualizada.getLongitude());
        location.setData(locationAtualizada.getData());
        return locationTrackerRepository.save(location);
    }

    // Excluir localização por ID
    public void excluirLocation(Long id) {
        LocationTracker location = buscarPorId(id);
        locationTrackerRepository.delete(location);
    }
}
