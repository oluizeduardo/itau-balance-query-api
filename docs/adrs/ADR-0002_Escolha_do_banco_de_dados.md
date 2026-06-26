---
Status: Accepted
Data: 23/06/2026
Aprovador: Luiz Eduardo da Costa
---

# ADR 0002: Escolha do Banco de Dados para persistência de saldo

## Contexto do Problema

O sistema precisa processar uma alta volumetria de movimentações financeiras (2.000 msgs/s) vindas de uma fila SQS e expor o saldo atualizado via API REST. No ecossistema de meios de pagamento, a **consistência dos dados** é o requisito mais crítico. Exibir um saldo incorreto ou permitir duplicidade é inadmissível para o negócio (Core Banking). 

É necessário escolher um banco de dados que garanta que o saldo de uma conta seja atualizado de forma atômica e exata, mesmo com múltiplas threads tentando alterar a mesma conta simultaneamente.

## Opções Consideradas

1. **PostgreSQL:** Banco de dados relacional clássico focado em garantias ACID estritas, integridade e controle de concorrência robusto.

2. **MongoDB / NoSQL:** Banco de dados orientado a documentos focado em alta escalabilidade horizontal.

## Decisão Selecionada

Foi decidido adotar o **PostgreSQL**. 

A justificativa fundamenta-se nos teoremas de sistemas distribuídos:
* **Teorema CAP:** Em caso de partição de rede (P), escolhemos **Consistência (C)** sobre Disponibilidade (A). O PostgreSQL atua como um sistema **CP**, garantindo que dados desatualizados nunca sejam expostos.

* **Teorema PACELC:** O PostgreSQL classifica-se como **PC/EC** (Se houver Partição, prioriza Consistência; Senão, prioriza Consistência sobre Latência). 

Como o ecossistema bancário exige exatidão imediata, bancos NoSQL que utilizam consistência eventual (como MongoDB ou Cassandra) foram rejeitados para evitar cenários onde o cliente vê um saldo antigo após uma transação concluída.

### Diagrama do Teorema PACELC

[![PACELC Theorem](https://fidelissauro.dev/assets/images/system-design/pacelc.drawio.png)](https://fidelissauro.dev/assets/images/system-design/pacelc.drawio.png)

**Fonte:** https://fidelissauro.dev/pacelc/

## Consequências

### Pontos Positivos (Prós):
* **Garantia ACID Completa:** Isolamento transacional nativo que impede *race conditions* ao atualizar o saldo da mesma conta.

* **Bloqueio em Nível de Linha:** Uso eficiente de mecanismos como `SELECT FOR UPDATE` no adaptador de persistência para travar apenas a conta sendo processada, permitindo alta concorrência nas outras linhas.

* **Simplicidade Operacional:** Tecnologia amplamente consolidada no mercado financeiro, reduzindo riscos de modelagem inadequada.

### Pontos Negativos (Contras):
* **Escalabilidade Vertical:** Escalabilidade horizontal (sharding) é mais complexa no Postgres do que em bancos NoSQL nativos.

* **Sobrecarga de Escrita:** A gravação em disco síncrona para garantir o ACID pode impor um limite de vazão se o banco não for bem tunado.

## Referências
- [Wikipedia - CAP theorem](https://en.wikipedia.org/wiki/CAP_theorem)
- [Wikipedia - PACELC Design Principle](https://en.wikipedia.org/wiki/PACELC_design_principle)
- [Blog Matheus Fidelis - System Design - Teorema PACELC](https://fidelissauro.dev/pacelc/)