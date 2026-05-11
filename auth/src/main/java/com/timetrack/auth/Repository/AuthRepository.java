package com.timetrack.auth.Repository;
import com.timetrack.auth.Model.UsuarioAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<UsuarioAuth, Long> {
    UsuarioAuth findByEmail(String email);
}