package com.timetrack.location.Repository;

import com.timetrack.location.Model.Geocerca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocercaRepository extends JpaRepository<Geocerca, Long> {
    Geocerca findBySucursalId(Long sucursalId);
}