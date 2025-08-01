package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.AutorDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
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

        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {
        List<Autor> resultado = autorService.pesquisa(nome, nacionalidade);
        List<AutorDTO> lista = resultado.stream()
                .map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).toList();
        return ResponseEntity.ok(lista);
    }

    @PutMapping
    public ResponseEntity<Object> atualizarAutor(
            @PathVariable Integer id,
            @RequestBody AutorDTO dto) {
        try {

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
        } catch (RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }
}

