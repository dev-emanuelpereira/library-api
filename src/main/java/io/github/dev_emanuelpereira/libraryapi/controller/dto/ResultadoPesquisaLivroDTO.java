package io.github.dev_emanuelpereira.libraryapi.controller.dto;

import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ResultadoPesquisaLivroDTO(
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        GeneroLivro generoLivro,
        BigDecimal preco,
        AutorDTO autor
) {
}
