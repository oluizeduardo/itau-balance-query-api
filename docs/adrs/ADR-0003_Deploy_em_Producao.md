---
Status: Accepted
Data: 26/06/2026
Aprovador: Luiz Eduardo da Costa
---

# ADR 0003: Adoção de VPS (Virtual Private Server) para Deploy em Produção

## Contexto do Problema

Com o avanço do desenvolvimento do ecossistema da API (`itau-balance-query-api`), tornou-se necessária a definição da infraestrutura ideal para hospedar a aplicação em ambiente produtivo. 

O objetivo principal é selecionar um modelo de hospedagem que equilibre capacidade de controle, simplicidade operacional, baixo custo inicial e facilidade de deploy.

## Opções Consideradas

* **Opção 1: Provedores de Cloud Nativo (ex: AWS, GCP, Azure):** Utilização de serviços totalmente gerenciados pelo provedor de nuvem. Oferece escalabilidade elástica automatizada e alta disponibilidade, porém adiciona complexidade de configuração e custos imprevisíveis/elevados para o volume de dados esperado pela aplicação.

* **Opção 2: Servidor Virtual Privado (VPS dedicada - ex: Ubuntu Server na Hostinger):** Hospedagem de todo o ecossistema conteinerizado dentro de uma única instância virtual isolada. O tráfego externo é gerenciado por um proxy reverso (Nginex/Traefik) e a comunicação entre os componentes ocorre em rede local interna do Docker.

## Decisão Selecionada

A opção selecionada foi a **Opção 2: Servidor Virtual Privado (VPS Hostinger)** executando Linux Ubuntu Server.

Esta decisão baseia-se no fato de que o projeto já está padronizado para execução em containers através do Docker Compose. 

Uma VPS provê o ambiente ideal para isolar a infraestrutura de maneira enxuta, permitindo controle absoluto sobre os recursos de hardware instalados (CPU e Memória RAM), com segurança e com baixo custo.

## Consequências

### Pontos Positivos (Prós):
* **Simplicidade e Portabilidade:** O processo de deploy replica o comportamento validado localmente, exigindo pouca adaptação no arquivo `docker-compose.yml`.
* **Previsibilidade de Custos:** Valores fixos mensais de infraestrutura, eliminando surpresas com cobranças por tráfego de rede ou uso de disco.

### Pontos Negativos (Contras):
* **Gerenciamento de Infraestrutura Manual:** A equipe torna-se responsável por atualizações de segurança do sistema operacional da VPS e monitoramento preventivo de espaço em disco.
* **Escalabilidade Vertical Limitada:** Para suportar aumentos de carga além do previsto, o upgrade exige o redimensionamento manual da instância, o que pode gerar breves janelas de indisponibilidade (Downtime).
* **Ponto Único de Falha (SPOF):** Por concentrar a aplicação e o banco em um único servidor físico/virtual, falhas de hardware na VPS indisponibilizam todo o fluxo temporariamente.

## Referências
* [Deploy to Hostinger VPS GitHub Action](https://github.com/marketplace/actions/deploy-on-hostinger-vps)
* [Fernanda Kipper - Como usar Docker + Proxy Reverso para hospedar TODOS seus PROJETOS](https://www.youtube.com/watch?v=qtMxOE4vIho)