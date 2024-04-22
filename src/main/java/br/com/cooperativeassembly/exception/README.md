# Pacote Exception

Este pacote é responsável por conter as classes de exceção personalizadas da aplicação. Essas classes são utilizadas para tratar erros específicos que podem ocorrer durante a execução dos serviços e controladores, permitindo uma resposta mais adequada e informativa para o cliente da API.

## Classes Contidas

- **AgendaCreationException**: Utilizada para tratar erros que ocorrem durante a criação de uma agenda. Extende `ResponseStatusException` e é mapeada para o status HTTP 400 (Bad Request).

- **AgendaNotFoundException**: Utilizada para tratar situações onde uma agenda específica não é encontrada. Extende `ResponseStatusException` e é mapeada para o status HTTP 404 (Not Found).

- **VoteRegistrationException**: Utilizada para tratar erros que ocorrem durante o registro de um voto. Extende `ResponseStatusException` e permite a definição de diferentes status HTTP conforme o erro específico.

- **VotingSessionClosedException**: Utilizada para tratar tentativas de votação em uma sessão já encerrada. Extende `ResponseStatusException` e é mapeada para o status HTTP 400 (Bad Request).

- **VotingSessionCreationException**: Utilizada para tratar erros que ocorrem durante a criação de uma sessão de votação. Extende `ResponseStatusException` e permite a definição de diferentes status HTTP conforme o erro específico.