package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByLogin(String login);

    Usuario findByEmail(String email);
}
