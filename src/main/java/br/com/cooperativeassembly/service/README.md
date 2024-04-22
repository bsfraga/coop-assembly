# Pacote Service

Este pacote é responsável por conter as classes de serviço da aplicação. As classes de serviço são onde a lógica de negócios é implementada, atuando como intermediário entre os controladores (controllers) e os repositórios de acesso a dados. Elas são responsáveis por executar operações de negócios, como criação, busca, atualização e exclusão de entidades, além de realizar validações e transformações de dados.

## Classes Contidas

- **AgendaService**: Gerencia as operações relacionadas às agendas, como criação de agendas, busca por ID e listagem de todas as agendas. Utiliza o `AgendaRepository` para interagir com o banco de dados e converter entidades de domínio em DTOs para transferência de dados.

- **VotingSessionService**: Responsável por gerenciar as sessões de votação, incluindo a abertura de novas sessões de votação e a obtenção de resultados de votações. Interage com `VotingSessionRepository` e `VoteRepository` para manipulação de dados relacionados a sessões de votação.

- **VoteService**: Responsável por gerenciar as operaçes relacionadas aos votos, como registro de votos e obtenção de resultados de votaçes. Interage com `VoteRepository` para manipulação de dados relacionados a votos.

- **FormUIService**: Fornece metadados para a construção dinâmica de UIs de formulário no frontend. Gera objetos `FormDTO` que contêm informaçes sobre os campos do formulário, botes e açes.

- **SelectionUIService**: Similar ao `FormUIService`, mas focado em fornecer metadados para UIs de seleção, como listas de opções. Gera objetos `SelectionDTO` que contêm informações sobre as opções de seleção disponíveis.

Cada serviço é anotado com `@Service`, indicando que são beans gerenciados pelo Spring e podem ser injetados em outras partes da aplicação. Além disso, muitos serviços utilizam `Mono` e `Flux` do Project Reactor para suportar programação reativa, permitindo operações assíncronas e não bloqueantes.