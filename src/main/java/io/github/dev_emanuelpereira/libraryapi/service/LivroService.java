package io.github.dev_emanuelpereira.libraryapi.service;

import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroRepository;
import static io.github.dev_emanuelpereira.libraryapi.repository.LivroSpecs.*;
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
        //Specification<Livro> specAula = Specification.where(
        //        isbnEqual(isbn)
        //                .and(tituloLike(titulo))
        //                .and(generoEqual(genero))
        //);


        //conjunction é equivalente a select * from tananan where 0 = 0
        Specification<Livro> spec = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.conjunction()));

        if(isbn != null) {
            spec.and(isbnEqual(isbn));
        }
        if (titulo != null)  {
            spec.and(tituloLike(titulo));
        }
        if (genero != null)  {
            spec.and(generoEqual(genero));
        }
        if (anoPublicacao != null) {
            spec.and(anoPublicacaoEqual(anoPublicacao));
        }
        if (nomeAutor != null) {
            spec.and(nomeAutorLike(nomeAutor));
        }
        //root é a projecao da classe, os dados da query
        return livroRepository.findAll(spec);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("O livro deve existir para poder atualizar");
        }
        livroRepository.save(livro);
    }
}
