# Pacote Repository

Este pacote é responsável por conter as interfaces que definem os repositórios da aplicação. Os repositórios são utilizados para abstrair a camada de acesso a dados, permitindo a interação com o banco de dados de forma mais simples e direta. Eles utilizam o Spring Data MongoDB para realizar operações de CRUD (Create, Read, Update, Delete) de forma reativa com o banco de dados MongoDB.

## Classes Contidas

- **AgendaRepository**: Interface que estende `ReactiveMongoRepository` para a entidade `Agenda`, permitindo operações reativas de CRUD específicas para agendas.
  
- **VoteRepository**: Interface que estende `ReactiveMongoRepository` para a entidade `Vote`, permitindo operações reativas de CRUD específicas para votos, incluindo métodos personalizados para buscar votos por sessão de votação e por membro.
  
- **VotingSessionRepository**: Interface que estende `ReactiveMongoRepository` para a entidade `VotingSession`, permitindo operações reativas de CRUD específicas para sessões de votação, incluindo um método personalizado para buscar sessões de votação que precisam ser fechadas baseado na data e hora atual.

Cada repositório é anotado com `@Repository`, indicando que são beans do Spring responsáveis pela persistência de dados.