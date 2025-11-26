package io.github.dev_emanuelpereira.libraryapi.controller.mappers;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.AutorDTO;
import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO autorDTO);

    AutorDTO toDTO(Autor autor);
}
