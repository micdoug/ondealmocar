# Sistema de votação do melhor restaurante 

Projeto desenvolvido utilizando o gerenciador de dependências gradle. 
O sistema foi desenvolvido em duas camadas.

## Backend (interface rest)

O backend expõe uma api rest que permite manipular as estruturas de dados existentes no aplicativo. 
As seguintes bibliotecas foram utilizadas:

- Spring Boot: utilizado como ferramenta básica para implementação do serviço rest a partir da utilização de suas estruturas internas.
- Hibernate: Foi utilizado para abstrair as operações de acesso e manipulação do banco de dados.
- H2: Implementação de banco de dados in-memory, utilizada no projeto por fins de simplicidade.

## Front-end 

O frontend é responsável por renderizar as telas para o usuário e utilizar a api rest para executar as funções do aplicativo.
Foi desenvolvida basicamente utilizando html, css e javascript. As seguintes bibliotecas javascript foram utilizadas:

- Materialize (CSS e Javascript): responsável pelo visual do site.
- JQuery: dependência do materialize
- UnderscoreJS: expõe algumas funções javascript bastante úteis
- VueJS: foi utilizada para comunicar com o backend e manipular o DOM de acordo com os dados recuperados do backend.


## Como executar

Como o projeto utiliza gradle, você deve utilizá-lo para restaurar as dependências antes de executar.
Existem duas opções para executar:

- Execute `gradle bootRun` para executar o projeto diretamente sem gerar um jar
- Execute `gradle build`, isso irá gerar um jar. Aí basta executar `java -jar build\libs\gs-spring-boot-0.1.0.jar` para executar o projeto.

Ambos os comandos irão iniciar o site no endereço http://localhost:8080/, aí basta acessar este endereço.
