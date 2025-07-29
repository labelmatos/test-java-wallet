
# 💰 test-java-wallet

Sistema de gerenciamento de carteiras virtuais com autenticação JWT, múltiplas moedas, transferências, extratos, e auditoria completa.

## 🧰 Tecnologias Utilizadas

- Java 20
- Spring Boot
- Spring Web
- Spring Security (JWT)
- MongoDB
- Maven

## 📦 Requisitos para execução

- Java 20+
- Maven 3.8+
- MongoDB Atlas ou local
- Postman (para testes via REST)

## 🔌 Configuração do MongoDB

Configure o acesso ao MongoDB no arquivo:

```
src/main/resources/application.properties
```

Exemplo:
```properties
mongo.username=labelmatos
mongo.password=HEaWP4Ejbie8NTsc
mongo.uri=mongodb+srv://${mongo.username}:${mongo.password}@cluster0.htfvgih.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0
```

## ▶️ Executando o projeto

```bash
mvn clean install
mvn spring-boot:run
```

O backend estará disponível em:
```
http://localhost:8080
```

## 🧪 Rodando os testes

```bash
mvn test
```

---

## 🔐 Autenticação

O login gera um token JWT com validade de 10 minutos. Esse token deve ser enviado no header `Authorization` nas demais requisições.

### Endpoint de login

```http
POST /auth/login
```

**Payload**:
```json
{
  "document": "35562363895",
  "password": "1q2w3e4r"
}
```

**Resposta**:
```json
{
  "token": "..."
}
```

---

## 📚 Endpoints disponíveis

### 👤 Clients
| Método | Rota                     | Descrição                       |
|--------|--------------------------|----------------------------------|
| POST   | `/clientes`              | Cadastro de cliente             |
| PATCH  | `/clientes`              | Atualizar apelido ou telefone   |
| DELETE | `/clientes`              | Exclusão lógica da conta        |
| GET    | `/clientes/me`           | Dados do próprio usuário        |

---

### 💼 Wallets
| Método | Rota                     | Descrição                        |
|--------|--------------------------|----------------------------------|
| POST   | `/wallets`               | Criação de carteira              |
| GET    | `/wallets`               | Listar carteiras                 |
| GET    | `/wallets/{id}`          | Obter carteira por ID            |
| DELETE | `/wallets/{id}`          | Excluir carteira (se saldo = 0)  |

---

### 💵 Transações
| Método | Rota                              | Descrição                       |
|--------|-----------------------------------|----------------------------------|
| PUT    | `/deposit/{walletId}`            | Depositar valores               |
| PUT    | `/withdraw/{walletId}`           | Realizar saque                  |
| PUT    | `/transfer/{walletId}`           | Transferência entre suas carteiras |
| PUT    | `/transfer/client/{walletId}`    | Transferência para outro cliente |

---

### 📊 Saldos e Extratos
| Método | Rota                                         | Descrição                         |
|--------|----------------------------------------------|------------------------------------|
| GET    | `/balance`                                   | Saldo de todas as carteiras       |
| GET    | `/balance/{walletId}`                        | Saldo de uma carteira específica  |
| GET    | `/balance/extract/{walletId}`                | Últimas 10 transações             |
| GET    | `/balance/extract/{walletId}?startDate=...`  | Extrato a partir de uma data      |

---

### 🕵️ Logs de Auditoria
| Método | Rota                                             | Requer perfil admin |
|--------|--------------------------------------------------|---------------------|
| GET    | `/audit/{document}`                              | ✅                  |
| GET    | `/audit/{document}?startDate=...&endDate=...`    | ✅                  |

---

## ⚠️ Regras de Negócio

- Carteiras podem ter moedas diferentes.
- Transferência entre moedas usa taxa de conversão (`currencyTaxes`).
- Exclusão de usuários ou carteiras é lógica (soft delete).
- Apenas carteiras com saldo zero podem ser excluídas.
- Toda operação gera registro de auditoria.

---

## 📮 Postman Collection

Importe o arquivo `java-wallet-postman-collection.json` no Postman.

---

## 👨‍💻 Desenvolvido por

Leonardo da Silva Matos

---

## 📝 Licença

MIT
