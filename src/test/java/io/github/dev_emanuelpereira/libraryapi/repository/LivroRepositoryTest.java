package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){
        Livro livro = new Livro();

        livro.setDataPublicacao(LocalDate.of(2025, 04, 20));
        livro.setGenero(GeneroLivro.BIOGRAFIA);
        livro.setIsbn("21412-1234");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("UFO");

        Autor autor = new Autor();

        autor.setNome("Autor 1");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1978, 03, 01));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    @Test
    void atualizarAutorByLivroTest(){
        var livroParaAtualizar = repository.findById(1).orElse(null);;
        Autor autor = autorRepository.findById(2).orElse(null);

        livroParaAtualizar.setAutor(autor);

        repository.save(livroParaAtualizar);
    }

    @Test
    void deletarTest(){
        var livro = repository.findById(1).orElse(null);;
        repository.delete(livro);
    }

    @Test
    @Transactional
    void buscarLivroTest(){
        Livro livro = repository.findById(1).orElse(null);

        System.out.println("Livro: " + livro.getTitulo());

        System.out.println("Autor: " + livro.getAutor().getNome());


    }

    @Test
    void pesquisarPorTitulo(){
        List<Livro> livros = repository.findByTitulo("Roubo da casa assombrada");
        livros.forEach(System.out::println);
    }

    @Test
    void listarLivrosJPQLTest(){
        var resultado = repository.listarTodos();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroTest(){
        var resultado = repository.findByGenero(GeneroLivro.BIOGRAFIA, "dataPublicacao");
        resultado.forEach(System.out::println);
    }
}