package io.github.dev_emanuelpereira.libraryapi.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {

    default URI gerarHeaderLocation(Integer id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
