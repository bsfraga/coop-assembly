# Pacote Controller

Este pacote é responsável por conter as classes que atuam como controladores na aplicação, intermediando a comunicação entre a camada de serviço e a interface de usuário ou chamadas de API externas. Os controladores recebem as requisições, delegam a execução das operações para os serviços e retornam as respostas para o cliente.

## Classes Contidas

- **AgendaController**: Gerencia as operações relacionadas às agendas, como criação e listagem de agendas.
  
- **FormController**: Responsável por fornecer os metadados da UI de formulários, facilitando a renderização dinâmica de formulários no frontend.
  
- **SelectionController**: Similar ao FormController, mas focado em fornecer metadados para UIs de seleção, como listas de opções.
  
- **VotingSessionController**: Controla as sessões de votação, incluindo a criação de sessões e a obtenção de resultados de votações.

Cada controlador utiliza anotações do Spring WebFlux para definir rotas, tipos de requisições e respostas esperadas, além de integrar documentação da API via Swagger/OpenAPI.