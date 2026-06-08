package com.timetrack.manag.Repository;

import com.timetrack.manag.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByRolId(Long rolId);

    boolean existsByEmail(String email);
    boolean existsByRut(String rut);
}