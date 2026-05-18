# Planeta Diário

![Daily Planet Banner](https://i.pinimg.com/736x/b2/22/ed/b222ed324bc607b55d2a98398971a83d.jpg)

## Sobre o Projeto

O **Planeta Diário** é uma plataforma digital que integra notícias globais em tempo real com análises de especialistas. Diferente de portais convencionais, o projeto propõe um ecossistema de **Dossiês**, onde criadores de conteúdo qualificados (médicos, engenheiros, juristas, jornalistas) publicam análises técnicas e opiniões fundamentadas sobre as manchetes do dia.

O objetivo é transformar a notícia "crua" em conhecimento especializado, combatendo a desinformação e centralizando o acesso a vozes qualificadas da sociedade.

---

## Arquitetura e Engenharia de Software

### Padrão MVC (Desacoplado)

A aplicação adota o padrão **Model-View-Controller** adaptado para SPAs:

- **Model:** Entidades JPA (`Publicacao`, `Usuario`) e repositórios gerenciam persistência no PostgreSQL.
- **View:** Vue.js consome os dados via API REST e cuida exclusivamente da interface.
- **Controller:** REST Controllers interceptam requisições HTTP, delegam lógica à camada de Service e retornam DTOs estruturados.

### Princípios SOLID Aplicados

- **Single Responsibility (SRP):** Classes com responsabilidades únicas. O `PublicacaoService` orquestra regras de dossiês; o `JwtService` apenas gera tokens.
- **Dependency Inversion (DIP):** Controllers dependem de interfaces (`NoticiaService`, `AuthService`), não de implementações concretas, facilitando testes e substituições.

### Paradigma Pub/Sub

Utiliza o `ApplicationEventPublisher` do Spring para criar um sistema reativo interno. Quando um dossiê é salvo, um evento é publicado e outras partes do sistema reagem de forma desacoplada — como o envio de notificações para seguidores.

---

## Segurança (JWT & RSA)

A autenticação utiliza **JWT assinado assimetricamente com RSA**:

- **Chave privada (`app.key`):** usada pelo servidor para assinar o token no login.
- **Chave pública (`app.pub`):** usada para validar a assinatura a cada requisição.
- **Stateless:** O backend não guarda sessão. O token trafega no header `Authorization: Bearer <token>` contendo o UUID do usuário e seu tipo (Leitor ou Criador). O Spring Security valida matematicamente a assinatura e libera o endpoint.

---

## Tecnologias Utilizadas

**Backend**
- Java 21 e Spring Boot 3
- Spring Security com JWT (RSA)
- Spring Data JPA e PostgreSQL
- Lombok
- ApplicationEventPublisher (Pub/Sub)

**Frontend**
- Vue.js 3 com Vite
- Tailwind CSS
- Axios, Pinia, Vue Router
- GNews API

---
![Daily Planet Banner](https://uploads.jovemnerd.com.br/wp-content/uploads/2024/06/superman_jornal_planeta_diario__6zy43f.jpg)

## Como Rodar o Projeto

### Pré-requisitos

- Java 21
- Node.js v18 ou superior
- PostgreSQL rodando localmente
- Chave de API do GNews (obtenha em [gnews.io](https://gnews.io/))

### 1. Banco de Dados

Crie um banco de dados vazio no PostgreSQL chamado `planetadiario`.

### 2. Gerando as Chaves RSA

O projeto utiliza um par de chaves RSA para assinar e validar os tokens JWT. Você precisa gerá-las localmente — elas não estão no repositório por segurança.

Dentro da pasta `src/main/resources/`, execute os comandos abaixo no terminal:

```bash
# Gera a chave privada (app.key)
openssl genrsa -out app.key 2048

# Extrai a chave pública a partir da privada (app.pub)
openssl rsa -in app.key -pubout -out app.pub
```

> Requer OpenSSL instalado. No Windows, use o Git Bash ou WSL. Os arquivos gerados devem ficar em `src/main/resources/` junto ao `application.properties`.

### 3. Configuração do Backend

Copie o arquivo `application.properties.example` e renomeie para `application.properties`. Preencha com suas credenciais:

```properties
spring.application.name=PlanetaDiario

jwt.public.key=classpath:app.pub
jwt.private.key=classpath:app.key

gnews.api.key=SUA_CHAVE_AQUI
endpoint.gnews=https://gnews.io/api/v4

spring.datasource.url=jdbc:postgresql://localhost:5432/planetadiario
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 4. Rodando o Backend

```bash
./mvnw spring-boot:run
```

O servidor estará disponível em `http://localhost:8080`.

### 5. Rodando o Frontend

```bash
cd frontend
npm install
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`.

---

## Endpoints Principais

**Autenticação**
- `POST /auth/register` — Cadastro de novo usuário
- `POST /auth/login` — Login, retorna JWT

**Notícias**
- `GET /api/news/busca/{termo}` — Busca notícias por termo
- `GET /api/news/top/{categoria}` — Notícias em alta por categoria

**Publicações (Dossiês)**
- `POST /publicacoes` — Criador publica um dossiê vinculado a uma notícia
- `GET /publicacoes/artigo?url={url}` — Lista dossiês de um artigo específico

---

## Atualizações Futuras

- Sistema de seguidores: usuários seguem criadores e recebem notificações a cada novo dossiê publicado
- Upload de conteúdo em vídeo e formato de artigo longo pelo criador
- Gerenciamento de estado global no frontend com Pinia (autenticação, feed, notificações)
- Dockerização da aplicação (backend + PostgreSQL + frontend em containers)
- Sistema de moderação: denúncia de dossiês e revisão por administradores
- Verificação de especialistas: validação de credenciais profissionais para obtenção do selo de criador qualificado
- Feed personalizado: algoritmo que prioriza dossiês de áreas de interesse do usuário
- Suporte multilíngue para expansão além do mercado brasileiro

---

 ---

*Este projeto é uma homenagem à estética clássica do Daily Planet do Universo dos quadrinhos do Superman misturado com designs de jornais digitais reais (The New York Times, Folha) focado em levar a verdade e a análise profunda e especializada à era digital.*
## Autores

- Ricardo Fragoso — [github.com/TheCardo](https://github.com/TheCardo)

![Daily Planet Banner](https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgCD20DHywC3ECfCXf91cjH_kG1IizheIJoaeSJaoozZa4LiEr3Wd1eRxi3t1Ga-qjYsnUm-rj3vB8_qlBfH5hL-whgcRka1aDUxBtz372Zu7GTOCVocGfGmAUlMAW1QkRNkdV7NElg42c/s1600/daily-planet.jpg)
