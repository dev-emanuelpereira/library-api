package io.github.dev_emanuelpereira.libraryapi.controller.dto;

import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AutorDTO(
        Integer id,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho")
        String nome,
        @NotNull(message = "Campo obrigatório")
        @Past(message = "Digite uma data de nascimento válida")
        LocalDate dataNascimento,
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 2, max = 100, message = "Campo fora do tamanho")
        String nacionalidade
){}
