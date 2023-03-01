# Projeto Spring Boot "Attornatus"

Este é um exemplo de API construída com Java / Maven / Spring Boot (version 3.0.3) que pode ser usada para cadastro de pessoas e endereços atribuídos a essas pessoas. Esta API está subindo com o banco de dados H2 em memória junto com a própria aplicação, sem precisar de novas ou outras configurações além das já feitas nesta aplicação.

## Como rodar?

Esta aplicação por usar Spring Boot já traz o servidor embutido. Nada de Tomcat ou JBoss é necessário instalar. Primeiro se cria os arquivos .jar com o comando estando na pasta raiz do projeto onde se vê o arquivo 'pom.xml':

1. ```mvn clean install```

Entra na pasta 'target':
2. ```cd target```

Executa a aplicação com servidor embutido:
3. ```java -jar attornatus-0.0.1-SNAPSHOT.jar```

* Clone este repositório
* Garanta que está usando JDK 17, Maven 3.8.x e Spring Boot 3.0.x
* Também é possível buildar usando o comando: ```mvn clean package```

* Ver o stdout ou o arquivo boot_example.log para ter certeza das exceções

Quando a aplicação estiver corretamente configurada e inicializada aparecerá no console algo assim:

```
2023-03-01T15:46:17.479-03:00  INFO 33008 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2023-03-01T15:46:17.494-03:00  INFO 33008 --- [  restartedMain] b.com.attornatus.AttornatusApplication   : Started AttornatusApplication in 8.473 seconds (process running for 9.379)
```

## Sobre os serviços

O serviço está implementado utilizando token JWT para as requisições dos endpoints. O fluxo da aplicação exige registro para pegar um token que possa fazer as demais requisições. O serviço tem apenas duas entidades mapeadas em REST, que é 'Pessoa' e 'Endereço'. O projeto assume o inglês como linguagem de desenvolvimento. Está sendo usado um banco em memória (H2) para armazenamento dos registros, porém o cuidado de lembrar que caso a aplicação seja reiniciada, por ser em memória, todos os dados são perdidos. Porém a adaptação é fácil para um MySQL ou PostgreSQL (em Docker ou não). Com o banco funcionando é possível chamar os endpoints de:

**br.com.attornatus.controller.AddressController**

E

**br.com.attornatus.controller.PersController**

## Postman

Na pasta 'postman' na raiz do projeto há um arquivo JSON com a collection de requisições para a aplicação.

## Aplicação (endpoints)

O fluxo da aplicação exige pegar um token anteriormente à requisição direta no endpoint. Os endpoints abaixo seguem uma certa "ordem" para que possam ocorrer corretamente. Os detalhes da requisição como parâmetros e corpo de requisição estão com detalhes na collection do **Postman**, por isso aqui estão apenas pontuados os endpoints.

1. POST - http://localhost:8080/register

Caso o token de registro tenha expirado, autenticar em:

2. POST - http://localhost:8080/authenticate

Endpoints de **Person**:

O endpoint de **Person** de criação (POST) pode já criar a pessoa com um endereço associado que será tido como principal, ou vazio e pelo endpoint de **Address** é possível adicionar endereços e mudar o principal.

3. POST - http://localhost:8080/person

4. PATCH - http://localhost:8080/person?document=358.644.828-70

5. GET - http://localhost:8080/person

6. GET - http://localhost:8080/person?document=358.644.828-70

Endpoints de **Address**:

A requisição de PATCH apenas altera o endereço principal. O de POST adiciona um endereço à lista dos endereços da pessoa.

1. POST - http://localhost:8080/address?document=358.644.828-70

2. PATH - http://localhost:8080/address/4?document=358.644.828-70

3. GET - http://localhost:8080/address?document=358.644.828-70

4. GET - http://localhost:8080/address/main?document=358.644.828-70


## Testes

Apenas os testes unitários foram realizados com cobertura integral por linhas e faltando apenas as branchs de exceção.

**Não** foram feitos testes de integração.

### Documentação Swagger

Com a aplicação funcionando acessar:

http://localhost:8080/swagger-ui/index.html


### Arquivo application.properties: 

O arquivo application.properties contém algumas configurações para a aplicação Spring.


