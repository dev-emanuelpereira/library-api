package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Integer>, JpaSpecificationExecutor<Livro>  {

    boolean existsByAutor(Autor autor);

    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    //JPQL -> Referencia as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo ")
    List<Livro> listarTodos();

    @Query(" select l from Livro as l join l.autor a ")
    List<Autor> listAutoresDosLivros();

    @Query(" select distinct l.titulo from Livro l ")
    List<String> listLivros();

    @Query(" select l from Livro l where l.genero = :genero order by  :paramOrdenacao ")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generoLivro, @Param("paramOrdenacao") String ordenacao);

    @Query(" select l from Livro l where l.genero = ?1 order by ?2 ")
    List<Livro> findByGeneroPositional(GeneroLivro generoLivro, String ordenacao);

    @Modifying //modificar registro dentro da tabela, seja delete ou update
    @Transactional
    @Query(" delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro generoLivro);

}
