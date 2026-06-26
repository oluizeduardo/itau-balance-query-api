## 🐘 Acessando o Banco de Dados pelo pgAdmin (Browser)

O ecossistema do projeto inclui uma interface gráfica do **pgAdmin 4** rodando em container. Siga os passos abaixo para acessar e gerenciar a base de dados do PostgreSQL através do seu navegador.

### 🌐 Passo 1: Acessar a Interface do pgAdmin

Abra o seu navegador de preferência e digite o seguinte endereço:

* **URL:** `http://localhost:8082`

Na tela de login, utilize as credenciais administrativas definidas no ecossistema:
* **Email:** `admin@itau.com`
* **Senha:** `admin`

---

### 🔌 Passo 2: Acesso ao Banco de Dados

Será solicitado a senha de acesso ao banco de dados
* **Senha:** `itausecret123@@`

---

### 📊 Passo 3: Consultar os Dados de Saldo

Com a conexão estabelecida, você pode abrir ferramentas de query para inspecionar os dados injetados pelo gerador:

1. Navegue na árvore lateral: `Servers` > `Postgres Local` > `Databases` > `balance_db` > `Schemas` > `public` > `Tables`.
2. Clique com o botão direito sobre a tabela de saldos e selecione **Query Tool**.
3. Para listar apenas 5 registros de saldo, execute:

```sql
SELECT * FROM account_balances LIMIT 5;
```
4. O valor do campo `account_id` é o que deve ser usado para consultas de saldo.