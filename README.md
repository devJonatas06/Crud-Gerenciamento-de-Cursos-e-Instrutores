# Crud-Gerenciamento-de-Cursos-e-Instrutores
Projeto que implementa operações CRUD usando Spring Boot, Hibernate e JPA para gerenciamento de dados acadêmicos, incluindo relacionamentos OneToOne, OneToMany e ManyToMany entre Instrutores, Cursos, Estudantes e Avaliações.


## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Jakarta Persistence API (JPA)
- Banco de dados relacional (MySQL, PostgreSQL, ou H2 para testes)
- Lombok (para reduzir código boilerplate)
- Maven (gerenciador de dependências)
- Git

---

## Descrição do Projeto

Este sistema permite realizar operações básicas de CRUD nas seguintes entidades:

- **Instructor** (Instrutor)
- **InstructorDetail** (Detalhes do Instrutor)
- **Course** (Curso)
- **Student** (Estudante)
- **Review** (Avaliação de Curso)

O projeto demonstra o uso de relacionamentos entre entidades:

- Um Instrutor pode ter vários Cursos (OneToMany).
- Um Curso possui um Instrutor (ManyToOne).
- Curso e Estudante possuem relação ManyToMany.
- Curso pode ter várias Reviews (OneToMany).
- Instrutor tem um detalhe (OneToOne).

Além disso, o projeto implementa operações para carregar entidades com *fetch join* para evitar o problema da LazyInitializationException.

---

## Funcionalidades

- Criar, ler, atualizar e deletar Instrutores, Cursos e Estudantes.
- Buscar Instrutor com seus Cursos e Detalhes em uma única consulta.
- Buscar Curso com suas Reviews ou Estudantes em uma única consulta.
- Adicionar e remover relacionamentos entre Cursos e Estudantes.
- Remover Instrutores e seus detalhes corretamente, preservando integridade.
- Deletar estudantes removendo seu vínculo com os cursos.

---

## Estrutura do Projeto

- **entity/** - Classes JPA que representam as tabelas do banco.
- **dao/** - Interface e implementação para acesso a dados usando EntityManager.
- **AppDao.java** - Interface com métodos CRUD customizados.
- **AppDaoImpl.java** - Implementação da interface AppDao com lógica JPA.

---

## Como Rodar o Projeto

1. Clone este repositório:

```bash

git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio

Configure seu banco de dados no arquivo application.properties (ou application.yml):
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
spring.datasource.username=usuario
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

    Execute a aplicação com:

mvn spring-boot:run