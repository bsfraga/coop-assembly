# Projeto Cooperative Assembly
[![Java CI with Gradle](https://github.com/bsfraga/coop-assembly/actions/workflows/gradle.yml/badge.svg)](https://github.com/bsfraga/coop-assembly/actions/workflows/gradle.yml)
## Descrição do Projeto

Este projeto é uma aplicação Spring Boot desenvolvida para gerenciar votações em uma cooperativa. Permite aos membros da cooperativa criar sessões de votação, votar e acompanhar os resultados das votações em tempo real.

#### Observaçoes

Para manter o objetivo de validar o cpf dos membros da cooperativa, foi implementado o consumo de um serviço externo que valida o CPF, porém o serviço consumido não é o mesmo sugerido no desafio tendo em vista que o serviço encontra-se fora do ar.

## Funcionalidades

- **Criação de Sessões de Votação**: Permite aos usuários criar sessões de votação para diferentes pautas.
- **Votação**: Os membros podem votar nas sessões de votação ativas, escolhendo entre 'Sim' ou 'Não'.
- **Contabilização dos Votos**: Ao final de cada sessão, os votos são contabilizados e o resultado é disponibilizado.

## Requisitos do Projeto

- **Java**: Versão 17.
- **Dependências**:
  - Spring WebFlux (para programação reativa)
  - Lombok
  - MongoDB (para persistência de dados)
  - Swagger (para documentação da API)

## Como Executar (Sem Docker)

1. **Pré-requisitos**: Certifique-se de ter o Java 17 e o MongoDB instalados e configurados em sua máquina.
2. **Clone o projeto**: `git clone URL_DO_PROJETO`
3. **Navegue até a pasta do projeto**: `cd caminho_para_o_projeto`
4. **Construa o projeto**: `./gradlew build`
5. **Execute a aplicação**: `java -jar build/libs/nome_do_arquivo.jar`

## Como Executar (Com Docker)

1. **Pré-requisitos**: Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina.
2. **Construa a imagem Docker do projeto** (opcional, se você não modificou o código): `docker build -t nome-da-imagem .`
3. **Execute usando o Docker Compose**:
   - Crie um arquivo `docker-compose.yml` conforme instruções anteriores.
   - Execute `docker-compose up` para iniciar os containers.

Lembre-se de substituir `URL_DO_PROJETO`, `caminho_para_o_projeto`, `nome_do_arquivo.jar` e `nome-da-imagem` pelos valores correspondentes ao seu projeto.

## Documentação da API

A documentação da API está disponível através do Swagger UI, acessível em `/v1/api/swagger-ui/index.html` após iniciar a aplicação.

## Testes

Os testes unitários e de integração foram implementados utilizando o Spring Boot Test e o Spring Data Test.

Para executar os testes, utilize o comando `./gradlew test`.

## Observaçes

Este projeto foi desenvolvido com base nos princípios do SOLID e do DDD, e segue o padrão de projeto CQRS (Command Query Responsibility Segregation).


