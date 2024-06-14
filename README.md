# Setup
Para realizar o setup do projeto:

- Instalar as dependências com o maven
- Copiar o arquivo `.env.sample` para um novo arquivo `.env`.
  - Verificar se as variáveis fazem sentido com o seu ambiente (por exemplo verificar as portas do banco de dados e do servidor)
  - Crie ao menos uma linha na tabela `membership_plan`
  - Seguir o tópico [de Configuração do OAuth2](#configuração-do-oauth2) e depois seguir com o próximo passo abaixo
  - Configurar o Azure Storage e RabbitMQ. Isso pode ser feito com a [instalação via docker](#docker-wip)
  - Criar a queue, exchange e binding no rabbitmq com os seguintes valores, respectivamente: `document-indexing-queue`, `document-indexing-exchange` e `document-indexing`
  - Mock do módulo de AI/Python: como este módulo ainda não existe, estou utilizando um mock server. Você pode fazer isso por exemplo pelo postman. 
    - Atualize seu .env com a url do mock server em `QUESTION_SERVICE_URL`
    - Utilize o mock para a seguinte request:
    `POST /chat/question`
    ```
    Response:
    
    {
      "id": "msg_1234_1234_1234",
      "content": [
          {
              "type": "text",
              "message": "Resposta da LLM"
          }
      ],
      "role": "assistant",
      "model": "model-used-identifier",
      "stop_reason": "end_turn",
      "usage": {
          "prompt_tokens": 3,
          "result_tokens": 7
      }
    }
    ```
  - Você pode usar a collection do postman em `docs/RAG.postman_collection.json` para testar o serviço 


# Configuração do OAuth2
## Google
Para conseguir utilizar o login social pelo Google você precisará configurar um novo aplicativo em sua conta. Isso pode ser feito no [console da Google](https://console.cloud.google.com/):
- Crie um novo projeto com a nomenclatura que quiser (e.g. RAG)
- Entre no projeto e depois em `APIs e serviços`. Vá para a área de credenciais e crie uma nova credencial do tipo `ID do cliente OAuth`. 
  - Em algum momento você terá a opção de adicionar as redirect_uri's permitidas. Adicione: `http://localhost:8080/login/oauth2/code/google` (ajustar número da porta se necessário)
  - Ao fim destes passos você deve pegar o clientId e clientSecret gerados e substituir nos campos correspondentes do seu `.env`

# Docker (WIP)
Atualmente a configuração do docker está feita apenas para o banco de dados e Azure Storage. Você pode subir o container utizando o comando `docker-compose  up --build -d` na raiz do projeto


