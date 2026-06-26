# 🚀 Como Rodar o Projeto

Todo o ecossistema de infraestrutura necessário para rodar e auditar a aplicação está centralizado e automatizado via Docker.

### 1. Subindo a Infraestrutura (Fila, Banco e Gerador de Transações)
Certifique-se de que o Docker está em execução na sua máquina. Na raiz do projeto, execute o comando abaixo para iniciar o Localstack (AWS SQS), o PostgreSQL e a interface de gerenciamento do banco (pgAdmin):

```bash
docker compose up -d
```

💡 O que acontece agora? O container `message-generator` aguardará o banco e a fila estarem em execução. Em seguida, ele começará a postar automaticamente até 300.000 transações simuladas na fila do SQS local.

### 2. Compilando o Projeto Java (Maven)
Abra um novo terminal na raiz do projeto e utilize o wrapper do Maven para compilar o código e rodar a suíte de testes unitários automatizados:

```bash
./mvnw clean package
```

### 3. Executando a Aplicação Localmente
Com a infraestrutura de pé e o projeto compilado, inicialize a aplicação Spring Boot:

```bash
./mvnw spring-boot:run
```

A API estará de pé e pronta para receber requisições na porta padrão 8080.