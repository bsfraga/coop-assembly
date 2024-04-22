# Pacote Scheduler

Este pacote é responsável por conter as classes que implementam tarefas agendadas na aplicação. Essas tarefas são executadas em intervalos regulares ou em momentos específicos, automatizando processos que não precisam de intervenção manual.

## Classes Contidas

- **VotingSessionScheduler**: Classe responsável por fechar as sessões de votação que atingiram o seu tempo limite. Ela verifica periodicamente as sessões de votação ativas e atualiza o status para fechado quando necessário.

A classe utiliza a anotação `@Scheduled` do Spring para definir a periodicidade das tarefas agendadas. A execução da tarefa é feita de forma reativa, utilizando o Spring WebFlux para não bloquear o processamento.
