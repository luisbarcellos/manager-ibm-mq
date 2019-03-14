# manager-mq-service

### Utilizado no projeto:
    *   Spring boot
    *   Gradle
    *   Docker
    *   Fila MQ IBM
    *   Leitura de arquivo xml
    *   Paralelismo
    *   Programação funcional 
    *   Controle de threads 

### Passos para rodar o projeto
## 1- Subir filas no docker com os comandos abaixo.
### Comando para subir docker para o Simular fila Entrada de dados
    docker run -d --env LICENSE=accept --env MQ_QMGR_NAME=QUEUE_IN -p 1414:1414 -p 9443:9443 --name queueIn ibmcom/mq

### Comando para subir docker para o Simular fila de Saída de dados
    docker run -d --env LICENSE=accept --env MQ_QMGR_NAME=QUEUE_OUT -p 1415:1414 -p 9444:9443 --name queueOut ibmcom/mq

## 2- Efetuar download do projeto.

## 3- Entrar na pasta raiz do projeto pelo terminal e rodar o comando abaixo:
    ./gradlew bootrun

Obs.: Necessário acesso a rede para download das dependências.

## Recursos

### 1. Simular fila de entrada

Insere quantidade de registros configurada no application.properties do projeto na fila de entrada.

#### 1.1 Exemplo de Consumo
* Método:
 
        POST
        
* URL:

        http://localhost:8080/ibm-fila/simular/filain
        
### 2. Transferir dados

Transfere a quantidade de registros configurada no application.properties do projeto da fila de entrada, para fila de saída.
        
#### 2.1 Exemplo de Consumo
* Método:

        POST
    
* URL:

        http://localhost:8080/ibm-fila/transferir/filain-to-filaout
        
### 3. Buscar mensagem(ns) Fila Entrada

Busca a quantidade de dados, especificada no query param, da fila de entrada.
        
#### 3.1 Exemplo de Consumo
* Método:

        GET
    
* URL:

        http://localhost:8080/ibm-fila/buscar/filain
        
* Exemplo:
        http://localhost:8080/ibm-fila/buscar/filain?quantidade=1
        
### 3. Buscar mensagem(ns) Fila Saída

Busca a quantidade de dados, especificada no query param, da fila de saída.
        
#### 3.1 Exemplo de Consumo
* Método:

        GET
    
* URL:

        http://localhost:8080/ibm-fila/buscar/filaout

* Exemplo:
    
        http://localhost:8080/ibm-fila/buscar/filaout?quantidade=1
        
#### 4 Retornos Esperados

* Response Code: 200
    * Mensagem(ns) buscada(s) com sucesso
* Response Code: 204
    * Mensagem(ns) inseridas com sucesso
* Response Code: 404
    * Não foi encontrado mensagens na fila