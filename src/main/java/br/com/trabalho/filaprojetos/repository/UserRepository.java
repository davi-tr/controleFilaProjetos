package br.com.trabalho.filaprojetos.repository;

import br.com.trabalho.filaprojetos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);

    @Query(
            "SELECT u from User u where u.email = :email"
    )
    User findByEmailUser(@Param("email") String email);



}
