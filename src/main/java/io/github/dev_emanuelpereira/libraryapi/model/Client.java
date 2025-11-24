package io.github.dev_emanuelpereira.libraryapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String clientId;

    @Column
    private String clientSecret;

    @Column
    private String redirectUri;

    @Column
    private String scope;
}
