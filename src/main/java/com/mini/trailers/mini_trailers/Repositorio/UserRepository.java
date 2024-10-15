package com.mini.trailers.mini_trailers.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mini.trailers.mini_trailers.Entidades.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
