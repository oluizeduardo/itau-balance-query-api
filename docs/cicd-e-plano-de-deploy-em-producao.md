## 🚀 CI/CD e Plano de Deploy em Produção (VPS)

Este guia orienta o deploy simplificado e seguro de todo o ecossistema da aplicação (API Spring Boot, PostgreSQL e Mensageria) dentro de uma única Máquina Virtual Privada (VPS), utilizando Docker, Nginx como Proxy Reverso e automação contínua via GitHub Actions.

---

### Passo 1: Conteinerização da Aplicação (Local ou CI/CD)
* **Tecnologia:** Docker (Multi-stage Build).
* **O que fazer:** * Criar um arquivo `Dockerfile` na raiz do projeto Java para compilar o código fonte e gerar uma imagem finalda aplicação.

---

### Passo 2: Provisionamento e Segurança da VPS
* **Tecnologias:** Linux (Ubuntu Server LTS).
* **O que fazer:**
  * Configurar o acesso à VPS exclusivamente via chave SSH criptografada, desativando o login por senha.
  * Ativar o firewall (UFW) para bloquear todas as portas residuais e abrir **apenas** as portas públicas essenciais: `22` (SSH), `80` (HTTP) e `443` (HTTPS). Portas como a do banco de dados (5432) ficam protegidas internamente.

---

### Passo 3: Preparação do Ambiente e Orquestração
* **Tecnologias:** Docker Engine e Docker Compose.
* **O que fazer:**
  * Instalar o Docker na VPS.
  * Criar a estrutura de diretórios no servidor e clonar o arquivo `docker-compose.yml` de produção.
  * Atualizar o `docker-compose.yml` para apontar o serviço da API para a imagem oficial publicada no Docker Hub.

---

### Passo 4: Automação do Deploy com GitHub Actions (CI/CD)
* **Tecnologias:** GitHub Actions, Docker Hub Secrets e SSH Action.
* **O que fazer:**
  * Configurar o gatilho da pipeline para disparar apenas quando uma nova TAG for gerada na branch `main`.
  * **Build:** A esteira compila a aplicação, gera a imagem Docker marcando-a com o número da TAG e faz o `push` para o Docker Hub.
  * **Deploy:** Utilizar uma Action de SSH para se conectar com segurança na VPS, baixar a nova versão da imagem (`docker compose pull balance-api`) e reiniciar o container de forma isolada (`docker compose up -d balance-api`), garantindo a atualização automática e sem interrupções nos outros serviços.

---

### Passo 5: Configuração do Proxy Reverso e SSL
* **Tecnologias:** Nginx.
* **O que fazer:**
  * Instalar o Nginx diretamente no sistema operacional da VPS para interceptar as requisições web.
  * Configurar o Nginx para escutar o seu domínio na porta 80/443 e repassar o tráfego de forma limpa para a API Spring Boot (rodando na porta interna 8080 do Docker).

---

### Passo 6: Inicialização e Validação do Ambiente
* **Tecnologias:** Docker Compose e Spring Boot Actuator.
* **O que fazer:**
  * Criar um arquivo `.env` na VPS protegendo as senhas reais de produção do banco PostgreSQL.
  * Iniciar a infraestrutura em background com o comando:
    ```bash
    docker compose up -d
    ```
  * Monitorar a subida e a saúde do ecossistema através dos logs e consumindo o endpoint `/actuator/health` da API para garantir que a conexão com a fila e com o banco de dados está estável.

  ## Referências
* [Deploy to Hostinger VPS GitHub Action](https://github.com/marketplace/actions/deploy-on-hostinger-vps)
* [Fernanda Kipper - Como usar Docker + Proxy Reverso para hospedar TODOS seus PROJETOS](https://www.youtube.com/watch?v=qtMxOE4vIho)