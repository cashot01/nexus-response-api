package br.com.fiap.nexus_response_api.repository;

import br.com.fiap.nexus_response_api.model.LocationTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTrackerRepository extends JpaRepository<LocationTracker, Long> {

}
