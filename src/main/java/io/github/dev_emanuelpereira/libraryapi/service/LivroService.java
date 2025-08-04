package io.github.dev_emanuelpereira.libraryapi.service;

import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
