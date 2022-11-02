# repositorioDemo

PASSOS PARA RODAR O PROJETO
1. Fazer o download no GitHub e importar para a IDE como Existing Maven Project.
2. Executar a API através do arquivo DemoApplication.java.
3. Utilizar o Postman para fazer as requisições.
4. Para fazer uma requisição via Postman, a API deve estar rodando na IDE através do endereço http://localhost:8080
5. No Postman, criar uma nova Collection e nesta Collection criar um novo Request.
6. Utilizar o tipo POST para o Request e informar o endereço http://localhost:8080/comprar 
7. Selecionar na aba Body da requisição: tipo "raw" e formato "JSON" (o padrão vem como "Text", então é necessário trocar).
8. Informar os seguintes dados no RequestBody:

{ "product": { "id": 123, "name": "Nome do Produto", "price": 800.00 }, "payment": { "entry":
100.00, "installments": 7 } }

9. Ao informar os dados acima, clicar em Send e observar o retorno em formato JSON no ResponseBody.

OBS: Os dados enviados no RequestBody podem ser alterados, contanto que sigam os tipos de dados pré-definidos. (long, String, double, int)

=======================
======= ATENÇÃO =======
=======================

O commit do projeto foi feito através da máquina local para o repositório do GitHub SEM O .gitignore o que acarretou no commit de TODOS OS ARQUIVOS
incluídos no projeto.
