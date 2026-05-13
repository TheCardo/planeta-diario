# O Planeta Diário - Plataforma de Notícias e Dossiês para Informação Qualificada

![Daily Planet Banner](https://i.pinimg.com/736x/b2/22/ed/b222ed324bc607b55d2a98398971a83d.jpg)

##  Sobre o Projeto
O **Planeta Diário** é uma plataforma digital que integra notícias globais em tempo real com análises profundas de especialistas. Diferente de portais de notícias convencionais, o projeto propõe um ecossistema de **Dossiês**, onde criadores de conteúdo qualificados (médicos, engenheiros, juristas, jornalistas) podem publicar análises técnicas e opiniões fundamentadas sobre as manchetes do dia.

O objetivo é transformar a notícia "crua" em conhecimento especializado, combatendo a desinformação e centralizando o acesso a vozes qualificadas da sociedade.

##  Proposta e Impacto
* **Centralização da Qualidade:** Um hub onde a notícia serve como ponto de partida para debates técnicos.
* **Combate a Fake News:** Ao permitir que profissionais mostrem suas credenciais (bio, profissão e certificações), o usuário pode escolher fontes confiáveis para se informar.
* **Arquitetura Pub/Sub:** O sistema utiliza o paradigma *Publish-Subscribe* para notificar o ecossistema sempre que um novo dossiê é publicado, garantindo agilidade na disseminação da informação.
* **Perfis Profissionais:** Criadores possuem um acervo próprio de suas publicações, funcionando como um portfólio de sua expertise técnica.

##  Tecnologias Utilizadas

### Backend
* **Java 21** e **Spring Boot 3**
* **Spring Security** com autenticação **JWT (JSON Web Tokens)** e chaves RSA
* **Spring Data JPA** para persistência
* **PostgreSQL** como banco de dados relacional
* **Lombok** para produtividade
* **Paradigma Pub/Sub** implementado via `ApplicationEventPublisher`

### Frontend
* **Vue.js 3** (Vite)
* **Tailwind CSS** para estilização personalizada
* **Axios** para integração com API
* **Vue Router** para navegação SPA
* **Pinia** para gerenciamento de estado e token no frontend
* **GNews API** para consumo de notícias mundiais em tempo real

##  Como Rodar o Projeto

### Pré-requisitos
* Java 21 instalado
* Node.js (v18 ou superior) instalado
* PostgreSQL rodando localmente
* Chave API gratuita do GNews

### 1. Obter a Chave da GNews API
Como o sistema consome notícias em tempo real, você precisará de uma chave de API:
1. Acesse o site oficial: [GNews.io](https://gnews.io/)
2. Clique em **"Get API Key"** e crie uma conta gratuita.
3. No seu painel (Dashboard), copie a chave alfanumérica gerada.

### 2. Configuração do Banco de Dados
Crie um banco de dados vazio chamado `planetadiario` no seu PostgreSQL.

### 3. Configuração do Backend (Java)
1.  Navegue até `src/main/resources/application.properties` e verifique as credenciais do banco e insira sua chave da GNews:
    ```properties
    # Configurações do Banco
    spring.datasource.url=jdbc:postgresql://localhost:5432/planetadiario
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    
    # Configuração da API de Notícias
    gnews.api.key=COLE_SUA_CHAVE_AQUI
    ```
2.  **Segurança (JWT):** Certifique-se de que possui os arquivos `app.pub` e `app.key` na pasta `resources` para a criptografia RSA do Token.
3.  Execute a aplicação via sua IDE (IntelliJ/Eclipse) ou via terminal:
    ```bash
    ./mvnw spring-boot:run
    ```
    *Nota: No primeiro acesso, o `ddl-auto=create` recriará as tabelas. Após a aplicação rodar com sucesso, altere para `update` no arquivo properties para não perder os dados.*

### 4. Configuração do Frontend (Vue.js)
1.  Navegue até a pasta do frontend:
    ```bash
    cd frontend
    npm install
    ```
2.  Inicie o servidor de desenvolvimento:
    ```bash
    npm run dev
    ```
3.  Acesse a aplicação no seu navegador em `http://localhost:5173`.

## Tipos de Usuários
* **Leitor (COMUM):** Pode ler notícias, acessar dossiês de especialistas e comentar (funcionalidade futura).
* **Criador de Conteúdo:** Possui as permissões de leitor e a capacidade única de redigir e publicar dossiês técnicos vinculados a notícias reais, além de gerenciar seu perfil profissional.

---
*Este projeto é uma homenagem à estética clássica do Daily Planet misturado com designs de jornais digitais reais, focado em levar a verdade e a análise profunda e especializada à era digital.*

![Daily Planet Banner](https://i.pinimg.com/736x/88/35/24/883524255f67a44095cf6b84ec110962.jpg)