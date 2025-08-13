package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.AutorDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
import io.github.dev_emanuelpereira.libraryapi.controller.mappers.AutorMapper;
import io.github.dev_emanuelpereira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("autores")
@RequiredArgsConstructor
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        autorService.salvar(autor);

        var location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id") Integer id) {
        return autorService
                .obterPorId(id)
                .map(autor -> {

                AutorDTO dto = autorMapper.toDTO(autor);
                return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
        Optional<Autor> autorOptional = autorService.obterPorId(id);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(autorMapper::toDTO
                ).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping
    public ResponseEntity<Void> atualizarAutor(
            @PathVariable Integer id,
            @RequestBody AutorDTO dto) {
        Optional<Autor> autorOptional = autorService.obterPorId(id);

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();

        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }
}

