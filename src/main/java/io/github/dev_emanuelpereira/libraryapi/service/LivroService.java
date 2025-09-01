package io.github.dev_emanuelpereira.libraryapi.service;

import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroRepository;
import static io.github.dev_emanuelpereira.libraryapi.repository.LivroSpecs.*;

import io.github.dev_emanuelpereira.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final SecurityService securityService;
    private final LivroValidator livroValidator;

    public Livro salvar(Livro livro){
        livroValidator.validar(livro);
        Usuario usuario = securityService.obterUsuarioLogado();
        livro.setUsuario(usuario);
        return livroRepository.save(livro);

    }

    public Optional<Livro> obterPorId(Integer id) {
        return livroRepository.findById(id);
    }

    public void deletar (Livro livro) {
        livroRepository.delete(livro);
    }

    public Page<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao, Integer pagina, Integer tamanhoPagina) {
        //Specification<Livro> spec = Specification.where(
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

        Pageable pagerRequest = PageRequest.of(pagina, tamanhoPagina);
        //root é a projecao da classe, os dados da query
        return livroRepository.findAll(spec, pagerRequest);
    }

    public void atualizar(Livro livro) {
        if(livro.getId() == null) {
            throw new IllegalArgumentException("O livro deve existir para poder atualizar");
        }
        livroValidator.validar(livro);
        livroRepository.save(livro);
    }
}
