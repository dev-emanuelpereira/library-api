package io.github.dev_emanuelpereira.libraryapi.service;

import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private LivroRepository livroRepository;

    public Livro salvar(Livro livro){
        return livroRepository.save(livro);
    }

}
