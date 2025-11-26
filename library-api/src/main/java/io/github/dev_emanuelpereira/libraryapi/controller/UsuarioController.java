package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.UsuarioDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.mappers.UsuarioMapper;
import io.github.dev_emanuelpereira.libraryapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<HttpStatus> salvar(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        var usuario = mapper.toEntity(usuarioDTO);
        service.salvar(usuario);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
