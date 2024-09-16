# Documento de Requisitos - Caso de Uso: Avaliação de Candidato a Desenvolvedor Java

#### RUN APP
<img src="https://i.imgur.com/oOjtPQ0.png" width=800>

#### DATABASE AND DATA
<img src="https://i.imgur.com/WbklzgN.png" width=800>

#### MENSEGER SQS - LOCALSTACK
<img src="https://i.imgur.com/N4fu1LD.png" width=800><br>
<img src="https://i.imgur.com/tCsy49F.png" width=800>

## Introdução
O caso de uso consiste em desenvolver uma funcionalidade que recebe um objeto contendo o código do vendedor e uma lista de pagamentos realizados. Cada pagamento é identificado pelo código da cobrança a que ele se refere. O sistema deve validar se o vendedor e o código da cobrança existem na base de dados. Além disso, ele deve verificar se o pagamento é parcial, total ou excedente em comparação com o valor original cobrado. Para cada situação de pagamento, o sistema deve enviar o objeto para uma fila SQS (Simple Queue Service) distinta e retornar o mesmo objeto recebido com a informação do status de pagamento preenchida.

## Requisitos Funcionais

### 1. Receber objeto contendo código do vendedor e lista de pagamentos
<img src="https://i.imgur.com/gJ84771.png" width=800>
#### RESULTADO = ATENDIDO

### 2. Validar existência do vendedor
O sistema deve verificar se o vendedor informado no objeto existe na base de dados. Caso não exista, o sistema deve retornar uma mensagem de erro informando que o vendedor não foi encontrado.
<img src="https://i.imgur.com/PpmwlpC.png" width=800><br>
#### RESULTADO = ATENDIDO

### 3. Validar existência do código da cobrança
Para cada pagamento realizado na lista, o sistema deve verificar se o código da cobrança informado existe na base de dados. Caso não exista, o sistema deve retornar uma mensagem de erro informando que o código da cobrança não foi encontrado.
<img src="https://i.imgur.com/XzmJA5V.png" width=800><br>
#### RESULTADO = ATENDIDO

### 4. Validar valor do pagamento
O sistema deve comparar o valor do pagamento recebido na requisição com o valor original cobrado, a fim de determinar se o pagamento é parcial, total ou excedente.
<img src="https://i.imgur.com/RdMVPTD.png" width=800><br>
#### RESULTADO = ATENDIDO

### 5. Enviar objeto para fila SQS
De acordo com a situação de pagamento (parcial, total ou excedente), o sistema deve enviar o objeto para uma fila SQS distinta. Essa fila será responsável por processar o objeto de acordo com a situação de pagamento.
<img src="https://i.imgur.com/xom0Mhg.png" width=800><br>
#### RESULTADO = ATENDIDO

### 6. Preencher status de pagamento
Após o processamento do objeto, o sistema deve preencher a informação do status de pagamento no mesmo objeto recebido. Essa informação indicará se o pagamento foi parcial, total ou excedente.
<img src="https://i.imgur.com/jQD4fte.png" width=800><br>
<img src="https://i.imgur.com/E3FArwM.png" width=800><br>
#### RESULTADO = ATENDIDO

## Requisitos Não Funcionais
Os requisitos não funcionais descrevem características do sistema que não estão diretamente relacionadas às funcionalidades, mas afetam seu desempenho, segurança, usabilidade, entre outros aspectos.

<b>
Primeiramente, orbigado por participar do processo seletivo, acredito que tenha atendidos, ao máximo que pude, os itens solicitados,
mas cabe aos senhores avaliarem, o desafio foi bem bacana, porém o tempo para atender o desafio eu achei um pouco pequeno,
por isso não consegui entragar com mais antecedência. No mais, espero que com esse teste eu possa ser bem avaliado.

Obrigado.
</b>

#### RESULTADO = ATENDIDO

### 1. Teste unitários
O caso de uso deve ser testavel através de testes unitários.
#### RESULTADO = ATENDIDO

### 2. Tratamento de resposta e status code
O sistema deve retornar uma resposta com status code 200 em caso de sucesso e 4XX em caso de erro.
#### RESULTADO = ATENDIDO

## Pontos a serem avaliados
- Qualidade do código - CLEAN CODE
- Testes unitários - RESULTADO NÃO PUDE ATENDER OS TESTES, PELO TEMPO, MAS INICIEI
- Arquitetura - ADOTEI A ARQUITETURA LIMPA PARA ESTA SOLUAÇÃO
- Abstração - REALIZEI PEQUENAS ALTERAÇÕES E ADICIONEIS OUTROS ATRIBUTOS

