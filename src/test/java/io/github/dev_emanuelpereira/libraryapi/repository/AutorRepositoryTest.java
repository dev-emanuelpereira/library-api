package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){

        Autor autor = new Autor();

        autor.setNome("Emanuel");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(2005, 3, 25));

        repository.save(autor);
    }

    @Test
    public void atualizarTest(){
        Optional<Autor> autor = repository.findById(1);

        if (autor.isPresent()){
            Autor autorEncontrato = autor.get();
            autorEncontrato.setNome("Emanuel Atualizado");
            repository.save(autorEncontrato);
        }
    }

    @Test
    public void countTest(){
        System.out.println("Count de autores: " + repository.count());
    }

    @Test
    public void deleteTest() {
        repository.deleteById(1);
    }

    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();

        autor.setNome("Jose");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1983, 5, 31));

        Livro livro = new Livro();

        livro.setIsbn("90323-3123");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("Magico Milionario");
        livro.setDataPublicacao(LocalDate.of(2025, 04, 20));
        livro.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    void mostrarLivrosPorAutorTest(){
        var autor = repository.findById(402).get();

        //buscar livros do autor

        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);




    }
}
