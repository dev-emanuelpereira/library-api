package io.github.dev_emanuelpereira.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;
<<<<<<< HEAD
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
=======

import java.math.BigDecimal;
import java.time.LocalDate;
>>>>>>> main

@Entity
@Table(name = "TBL_LIVRO", schema = "public")
@Data
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

<<<<<<< HEAD
    @Column(name = "dataPublicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "generoLivro", length = 30, nullable = false)
=======
    @Column(name = "data_publicacao")
    private LocalDate data_publicacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 30, nullable = false)
>>>>>>> main
    private GeneroLivro genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private BigDecimal preco;

    //chave estrangeira
    @ManyToOne(
            fetch = FetchType.LAZY //so vai trazer os dados do livro, e nao do autor
    )
    @JoinColumn(name = "id_autor")
    private Autor autor;

<<<<<<< HEAD
    @Column
    @CreatedDate
    private LocalDateTime dataCadastro;

    @Column
    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    private Integer usuario;

=======
>>>>>>> main
}
