# 🔍 Uso da API para Consulta de Saldo

Consultar Saldo via API REST
Para validar o saldo de uma conta que está sendo atualizada em tempo real pelo gerador, faça uma requisição HTTP GET:

```bash
curl http://localhost:8080/balances/{accountId}
```