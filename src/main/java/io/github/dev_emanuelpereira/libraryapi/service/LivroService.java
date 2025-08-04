package io.github.dev_emanuelpereira.libraryapi.service;

import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroRepository;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

    public Optional<Livro> obterPorId(Integer id) {
        return livroRepository.findById(id);
    }

    public void deletar (Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {
        Specification<Livro> specAula = Specification.where(
                LivroSpecs.isbnEqual(isbn)
                .and(LivroSpecs.tituloLike(titulo))
                .and(LivroSpecs.generoEqual(genero))
        );


        //conjunction é equivalente a select * from tananan where 0 = 0
        Specification<Livro> spec = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));

        if(isbn != null) {
            spec.and(LivroSpecs.isbnEqual(isbn));
        }
        if (titulo != null)  {
            spec.and(LivroSpecs.tituloLike(titulo));
        }
        if (genero != null)  {
            spec.and(LivroSpecs.generoEqual(genero));
        }
        //root é a projecao da classe, os dados da query
        return livroRepository.findAll(spec);
    }

}
