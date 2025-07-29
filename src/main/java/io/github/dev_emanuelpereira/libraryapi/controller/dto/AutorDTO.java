package io.github.dev_emanuelpereira.libraryapi.controller.dto;

import java.time.LocalDate;

public record AutorDTO(
        String nome,
        LocalDate dataNascimento,
        String nacionalide
) {
}
