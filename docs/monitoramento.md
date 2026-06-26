## 📊 Monitoramento com Spring Boot Actuator

A aplicação possui suporte nativo ao **Spring Boot Actuator**, que expõe endpoints estratégicos para monitorar a saúde, coletar métricas e validar o estado do serviço em tempo real quando estiver rodando em produção.

### 🔌 Endpoints Disponíveis

Por padrão, os seguintes endpoints estão expostos e podem ser acessados localmente:

* **Health Check (`/actuator/health`):** Retorna o estado geral da aplicação.
* **Métricas (`/actuator/metrics`):** Expõe indicadores detalhados de performance da JVM (como uso de memória heap, threads ativas e consumo de CPU).
* **Informações (`/actuator/info`):** Exibe dados customizados sobre a versão do build e detalhes do serviço.