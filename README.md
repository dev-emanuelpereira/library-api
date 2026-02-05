# Library API
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
![Java](https://img.shields.io/badge/Java-21-yellow?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-Framework-6DB33F?logo=springboot&logoColor=white)
![OAuth2](https://img.shields.io/badge/OAuth2-Security-blue?logo=oauth&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Container-2496ED?logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-black?logo=jsonwebtokens&logoColor=white)
![REST](https://img.shields.io/badge/API-RESTful-lightgrey)

---

## Descrição

Library API é uma aplicação backend desenvolvida em **Java com Spring Boot**, com o objetivo de gerenciar uma biblioteca digital.  
A API permite o cadastro e gerenciamento de **livros**, **autores** e **usuários**, contando com **autenticação e autorização via OAuth2**, utilizando **JWT (Bearer Token)** para proteger os endpoints.

O projeto foi desenvolvido com foco em **boas práticas de desenvolvimento**, **segurança**, **organização em camadas** e **padrões REST**, simulando um cenário real de aplicação corporativa.

---

## Funcionalidades

- Autenticação de usuários utilizando **OAuth2**
- Autorização baseada em **JWT (Bearer Token)**
- Gerenciamento completo de:
  -  Livros
  -  Autores
  -  Usuários
- Proteção de endpoints com Spring Security
- API RESTful seguindo boas práticas de arquitetura
- Validações de dados e regras de negócio

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **OAuth2**
- **JWT**
- **Spring Data JPA**
- **Banco de Dados Relacional**
- **Maven**
- **Docker**

---

## Requisitos

Antes de executar o projeto, é necessário ter instalado:

- **Java 21 ou superior**
- **Maven**
- **Banco de dados relacional** (ex: PostgreSQL ou MySQL)

---

## Como Rodar a Aplicação

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/library-api.git
cd library-api
```

2. Configurar o banco de dados

Para isso, é necessário um banco de dados inicializado na máquina ou em um container virtual.
Configure as variáveis de ambiente ou o arquivo application.properties / application.yml com as credenciais do banco de dados:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=usuario
spring.datasource.password=senha
```

3. Executar a aplicação
```
mvn spring-boot:run
```

A aplicação estará disponível em:
```
http://localhost:8080
```

---

## Autenticação

A API utiliza OAuth2 com JWT para autenticação.

Fluxo básico:
- O usuário realiza login
- A API retorna um Bearer Token (JWT)
- O token deve ser enviado no header das requisições protegidas:
```
Authorization: Bearer <seu_token_jwt>
```

> A validação de credenciais é realizada através da **Security API** localizada na pasta ```resource-server```.
---

## Estrutura do Projeto

```
library-api/ → API backend
│   src/
│   ├── config/
│   ├── controller/
│   ├── exceptions/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── security/
│   ├── validator/
└── Application.java
resource-server/ → Security API
```

## Descrição das camadas

- ```controller:``` Exposição dos endpoints REST
- ```service:``` Regras de negócio
- ```repository:``` Acesso ao banco de dados
- ```model:``` Entidades do sistema
- ```security:``` Configurações de segurança, OAuth2 e JWT
- ```dto:``` Objetos de transferência de dados

---
## Considerações Finais

Este projeto foi desenvolvido com foco em aprendizado e consolidação de conceitos importantes do ecossistema Spring, especialmente segurança, autenticação, arquitetura REST e boas práticas de backend.

---
## Autor
### Emanuel Pereira
- *Github:* https://github.com/dev-emanuelpereira
