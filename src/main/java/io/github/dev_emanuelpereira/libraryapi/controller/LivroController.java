package io.github.dev_emanuelpereira.libraryapi.controller;

import io.github.dev_emanuelpereira.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ErroResposta;
import io.github.dev_emanuelpereira.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.dev_emanuelpereira.libraryapi.controller.mappers.LivroMapper;
import io.github.dev_emanuelpereira.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import io.github.dev_emanuelpereira.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livroService.salvar(livro);

        var location = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable("id") Integer id) {
        return livroService.obterPorId(id).map(
                livro -> {
                    var dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }
        ).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") Integer id) {
        return livroService.obterPorId(id).map(
                livro -> {
                    livroService.deletar(livro);
                    return ResponseEntity.noContent().build();
                }
        ).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", required = false, defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanhoPagina", required = false, defaultValue = "10")
            Integer tamanhoPagina
    ) {
        var paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao,pagina ,tamanhoPagina);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        //var lista = paginaResultado
        //        .stream()
        //        .map(livroMapper::toDTO)
        //        .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") Integer id, @RequestBody @Valid CadastroLivroDTO dto
    ) {
        return livroService.obterPorId(id).map(
                livro -> {
                    Livro entidade = livroMapper.toEntity(dto);
                    livro.setDataPublicacao(entidade.getDataPublicacao());
                    livro.setIsbn(entidade.getIsbn());
                    livro.setPreco(entidade.getPreco());
                    livro.setTitulo(entidade.getTitulo());
                    livro.setAutor(entidade.getAutor());
                    livro.setGenero(entidade.getGenero());

                    livroService.atualizar(livro);

                    return ResponseEntity.noContent().build();
                }
        ).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
