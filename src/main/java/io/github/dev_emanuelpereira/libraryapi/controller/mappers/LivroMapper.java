package io.github.dev_emanuelpereira.libraryapi.controller.mappers;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    @Mapping(target = "genero", source = "generoLivro")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(target = "genero", source = "generoLivro")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
