# API-tester
Esta aplicação é uma API criada para testar outras API's, com testes de seguraça, de performance e testes automatizados.

## Desenvolvimento:
* Foi usado Java com SpringBoot;
* Para segurança foi implementado um sistema de token como login;
* Existem testes individuais para requisições POST, GET, DELETE e PUT;
* Cada teste de requisição feita será salva em um banco PostgreSQL.

## Projeto:
* O projeto conta com algumas funcionalidades relacionadas a testes de requisições;
* Para teste de segurança existem testes de SQL Injection, Command Injection, XSS Injection, senhas fracas e validação de dados;
* Existe uma integração com o GPT-3 para verificar problemas na API;
* É possível fazer testes de performance com diversas requisições paralelas;
* Também é possível automatizar testes, fazendo com que a aplicação envie diversas requisições para endpoints diferentes;
* Nestes testes automatizados é possível definir variáveis que são alimentadas com as responses de outras requisições.



---
⭐️ From [DarlanNoetzold](https://github.com/DarlanNoetzold)
