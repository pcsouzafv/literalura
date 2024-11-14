



# Literalura![a_digital_library_with_a_central_title](https://github.com/user-attachments/assets/349d933f-d8c2-4361-8199-e396952edc02)


Literalura é uma aplicação web construída com Java, Spring Boot e React para gerenciar informações sobre autores e livros. O projeto visa criar uma plataforma que permita adicionar, editar e visualizar dados de autores e seus respectivos livros, com uma interface intuitiva.

## Tecnologias Utilizadas

- **Backend**: Java 21, Spring Boot (Data JPA, Web)
- **Banco de Dados**: PostgreSQL
- **Frontend**: React com Tailwind CSS
- **Controle de Dependências**: Maven

## Estrutura do Projeto

A estrutura do projeto segue o padrão MVC e está organizada da seguinte forma:

- `src/main/java/com/LiterAlura/literalura`
  - `Controller` - Contém as classes controladoras para gerenciar as requisições de autores e livros.
  - `model` - Contém as classes de modelo (`Autor` e `Livro`) que representam as entidades no banco de dados.
  - `repositorio` - Contém as interfaces de repositório (`AutorRepository` e `LivroRepository`) que estendem o Spring Data JPA.
  - `servicos` - Contém as classes de serviço para aplicar a lógica de negócio, como `AutorService`.
  - `resources` - Contém as pastas `static` e `templates` para arquivos estáticos e templates, se aplicável.

## Configuração do Ambiente

### Pré-requisitos

- Java 21 ou superior
- Maven
- PostgreSQL


### Configuração do Banco de Dados

Certifique-se de que você tenha uma instância do PostgreSQL em execução e crie um banco de dados chamado `testeBD`. 

No arquivo `src/main/resources/application.properties`, configure as propriedades de conexão com o banco de dados:

```properties
spring.application.name=testeBD
spring.datasource.url=jdbc:postgresql://localhost:5432/testeBD
spring.datasource.username=postgres
spring.datasource.password=*******
spring.datasource.driver-class-name=org.postgresql.Driver

