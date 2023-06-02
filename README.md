# Library API para Gerenciamento de livraria


## Sobre
Projeto com foco e tema de gerenciamento de uma livraria.  Recursos da API: <br>
<li>Armazenamento de Livros</li>
<li>Controle de acesso de usuarios</li>
<li>Pedido de livros</li>
...

## Construido com
  <ul>
      <li>Spring(Java)</li>
      <li>H2 Database e PostgreSQL</li>
      <li>Swagger</li>
      <li>JWT Token</li>
      <li>Cache tool</li>
...
 </ul>

## Veja online

Projeto hospedado em:`https://spring-api-library-production.up.railway.app/`
<br>Página para documentação de recursos da API com [Swagger UI](https://spring-api-library-production.up.railway.app/api/swagger-ui/index.html)

## Testar API
- Para recursos que não precisam de autenticação basta acessar o link pelo seu cliente favorito (Postman, Insomnia, ou o proprio Swagger através do **Try it out**) passando parâmetros caso forem necessarios.
- Os que precisam de autenticação, basta requisitar via POST o [link de login do projeto](https://spring-api-library-production.up.railway.app/api/login), passando **email** 
e **senha** no formato _JSON_, dados previamente cadastrados no banco. Podem ser utilizados os dados do exemplo abaixo ou um usuario cadastrado através do recurso de **Salvar um usuario**.
<br/><br/>
  Exemplo de requisição:
``` 
  {
    "email": "usuario@mail.com",
    "senha": "123"
  } 
```

Com dados corretos será retornado O status `200` e um `token`, utilize nas requisições que precisam de autenticação passando como atributo header com o cabeçario "Authorization", ou até setando o valor do Token gerado(sem o **Bearer** ) no `Authorize` do Swagger. Após feita a validação do Token é liberado o acesso.


`Seja livre para deixar uma estrela e fazer um Fork desse projeto. Aceito críticas e sugestões sobre o mesmo.`






