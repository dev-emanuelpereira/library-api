package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.AutorDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
<<<<<<< HEAD
import io.github.dev_emanuelpereira.libraryapi.controller.mappers.AutorMapper;
import io.github.dev_emanuelpereira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.model.Usuario;
import io.github.dev_emanuelpereira.libraryapi.service.AutorService;
import io.github.dev_emanuelpereira.libraryapi.service.SecurityService;
=======
import io.github.dev_emanuelpereira.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.dev_emanuelpereira.libraryapi.model.Autor;
import io.github.dev_emanuelpereira.libraryapi.service.AutorService;
>>>>>>> main
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
=======
>>>>>>> main
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
<<<<<<< HEAD
public class AutorController implements GenericController {

    private final AutorService autorService;
    private final AutorMapper autorMapper;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {

        Autor autor = autorMapper.toEntity(dto);
        autorService.salvar(autor);

        var location = gerarHeaderLocation(autor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id") Integer id) {
        return autorService
                .obterPorId(id)
                .map(autor -> {

                AutorDTO dto = autorMapper.toDTO(autor);
                return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") Integer id) {
        Optional<Autor> autorOptional = autorService.obterPorId(id);
=======
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        try {
        var autorEntidade = autorDTO.mapearParaAutor();
        autorService.salvar(autorEntidade);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autorEntidade.getId()).toUri();

        ErroResposta erro = ErroResposta.conflito("Autor ja cadastrado");
        return ResponseEntity.created(location).build();
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> obterDetalhes (@PathVariable("id") Integer id) {
        Optional<Autor> autorOptional = autorService.obterPorId(id);
        if (autorOptional.isPresent()) {
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getId(),
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") Integer id) {
        try {
        Optional<Autor> autorOptional = autorService.obterPorId(id);

>>>>>>> main
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
<<<<<<< HEAD
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
=======
        } catch (OperacaoNaoPermitidaException e) {
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
>>>>>>> main
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
<<<<<<< HEAD
                .map(autorMapper::toDTO
=======
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
>>>>>>> main
                ).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping
<<<<<<< HEAD
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> atualizarAutor(
            @PathVariable Integer id,
            @RequestBody AutorDTO dto) {
=======
    public ResponseEntity<Object> atualizarAutor(
            @PathVariable Integer id,
            @RequestBody AutorDTO dto) {
        try {

>>>>>>> main
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
<<<<<<< HEAD
=======
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
>>>>>>> main
    }
}

