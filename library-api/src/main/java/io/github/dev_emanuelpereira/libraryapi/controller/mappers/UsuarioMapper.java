package io.github.dev_emanuelpereira.libraryapi.controller.mappers;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.UsuarioDTO;
import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);

    UsuarioDTO toDTO(Usuario usuario);
}
