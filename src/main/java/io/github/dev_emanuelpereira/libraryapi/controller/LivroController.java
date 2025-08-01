package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.dev_emanuelpereira.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController {

    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        try {


            return ResponseEntity.ok().build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}
