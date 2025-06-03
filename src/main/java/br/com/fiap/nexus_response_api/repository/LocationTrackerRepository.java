package br.com.fiap.nexus_response_api.repository;

import br.com.fiap.nexus_response_api.model.LocationTracker;
import br.com.fiap.nexus_response_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationTrackerRepository extends JpaRepository<LocationTracker, Long> {

    List<LocationTracker> findByUsuario(Usuario usuario);

}
