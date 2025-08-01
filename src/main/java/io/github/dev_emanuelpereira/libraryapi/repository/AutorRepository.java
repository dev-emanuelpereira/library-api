package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

    List<Autor> findByNome(String nome);

    List<Autor> findByNacionalidade(String nacionalidade);

    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade);

    /** boolean existsByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate dataNascimento, String nacionalidade); **/



}
