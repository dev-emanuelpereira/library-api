package io.github.dev_emanuelpereira.libraryapi.repository;

import io.github.dev_emanuelpereira.libraryapi.model.GeneroLivro;
import io.github.dev_emanuelpereira.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual (String isbn) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%"+titulo.toUpperCase()+"%"));
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), generoLivro));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        //funcao para transformar o dado do tipo Date em ano...
        //criteiraBuilder.function serve para dizer qual funcao do SQL qwuer usar
        //String.class serve para dizer qual o tipo do retorno
        //root.get("dataPublicacao") o valor no banco
        //criteriaBuilder.literal("YYYY") e o valor literal que quer passar na consulta
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("to_char", String.class, root.get("dataPublicacao"), criteriaBuilder.literal("YYYY")), anoPublicacao.toString()));
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
         return ((root, query, criteriaBuilder) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return criteriaBuilder.like(joinAutor.get("nome"), "%"+nome.toUpperCase()+"%");

            //return criteriaBuilder.like(criteriaBuilder.upper(root.get("autor").get("nome")), "%"+nome.toUpperCase()+"%");
        });
    }
}
