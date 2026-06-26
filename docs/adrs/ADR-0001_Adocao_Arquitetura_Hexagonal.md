---
Status: Accepted
Data: 22/06/2026
Aprovador: Luiz Eduardo da Costa
---

# ADR 0001: Adoção de Arquitetura Hexagonal (Ports and Adapters)

## Contexto do Problema

O sistema em desenvolvimento consiste em uma API de Missão Crítica (Core Banking) dividida em duas frentes primordiais: a **ingestão assíncrona** de alta volumetria e a **exposição síncrona** de saldo via endpoints REST (HTTP GET). 

O principal desafio reside em garantir a consistência dos dados, resiliência e alta disponibilidade do saldo bancário. Como a fila SQS utilizada não garante a ordenação estrita na entrega das mensagens, transações financeiras podem ser processadas fora de ordem. 

É necessário definir um padrão arquitetural que blinde as regras de negócio core contra as volatilidades, contratos e tecnologias de infraestrutura.

## Opções Consideradas

1. **Arquitetura em Camadas Tradicional (Layered Architecture):** Divisão clássica em `Controller -> Service -> Repository`. Onde as entidades do banco de dados transitam por todas as camadas.

2. **Arquitetura Hexagonal (Ports and Adapters):** Isolamento do Domínio, comunicando-se com o exterior estritamente através de Portas (Interfaces) de Entrada/Saída e Adaptadores tecnológicos.

3. **Arquitetura Baseada em Eventos (EDA - Event-Driven Architecture):** Fluxo orientado a eventos, onde até mesmo a exposição do saldo dependeria de eventos assíncronos internos.

## Decisão Selecionada

Foi decidido adotar a **Arquitetura Hexagonal (Opção 2)**. 

A arquitetura em camadas tradicional foi rejeitada porque tende a acoplar o modelo de negócio às restrições técnicas e anotações do framework ORM/JPA, tornando mudanças de infraestrutura arriscadas. 

A arquitetura baseada em eventos pura traria uma complexidade desnecessária para o escopo de exposição de uma API REST simples.

Com a Arquitetura Hexagonal, o domínio e casos de uso do sistema ficam agnósticos ao ecossistema Spring Boot, AWS SQS ou banco de dados.

## Consequências

### Pontos Positivos (Prós):
* **Independência de Tecnologia:** Se for necessário migrar de um banco relacional para um banco de dados NoSQL com o objetivo de otimizar a escrita de alta volumetria, apenas o adaptador de persistência será reescrito. O domínio permanecerá intacto.

* **Massa de Testes Eficiente:** Permite cobertura total dos cenários de consistência de saldo via testes unitários, mockando apenas as portas de saída.

* **Separação de Responsabilidades:** Possíveis erros de parseamento de payloads JSON da fila SQS são absorvidos e tratados na camada de Adapter de Entrada, nunca contaminando o banco ou o domínio de saldo.

### Pontos Negativos (Contras):
* **Aumento de Boilerplate:** Exige a criação de múltiplos DTOs e mappers para garantir que as entidades do banco não vazem, o que aumenta levemente a quantidade de classes iniciais do projeto.

* **Curva de Aprendizado:** Requer disciplina estrita do desenvolvedor para não injetar classes do Spring framework ou anotações do Hibernate dentro das entidades do pacote `domain`.

## Referências
*  [Hexagonal architecture pattern](https://docs.aws.amazon.com/prescriptive-guidance/latest/cloud-design-patterns/hexagonal-architecture.html).