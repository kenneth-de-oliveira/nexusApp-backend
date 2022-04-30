**Nexus App - Backend**
========================================================================
This API is a proposal for a "fictitious" digital account project developed as a way of completing the specialization course in software engineering at PUC MINAS, whose main objective is to offer main services, such as: registration, login and bill.

**Some of the resources used**

- Git
- Lombok
- Jasper Report 
- Spring Security  
- Maven
- Spring Boot 
- Api (Rest)
- JPA (Hibernate) 
- Spring Data 
- PostgreSQL
- H2

========================================================================

**Instructions**

	First, clone the repository at the address:
	
	https://github.com/kenneth-de-oliveira/nexusApp-backend.git
	
	After the project is cloned, open the terminal in the cloned directory **nexusApp-backend** 
 
  And use the following commands:
  
	cd nexusApp-backend
	mvn install
	
**It is very important to wait for the execution of the above mentioned commands.**

========================================================================

Project configuration
========================================================================
- Java 11
- Maven 3.8.3

**EXAMPLES OF EXECUTION**

Create a new account **POST**: localhost:8080/contas/cadastrar
```json
{
    "clienteDTO": {
        "nome": "Mario",
        "sobrenome": "da Silva Soares",
        "documento": "30550479074",
        "email": "mario@outlook.com",
        "telefone": "8228270964",
        "enderecoDTO": {
            "logradouro": "Avenida Monsenhor Odilon Coutinho",
            "bairro": "Cabo Branco",
            "numero": "312",
            "cidade": "João Pessoa",
            "cep": "58045120",
            "uf": "PB"
        }
    }
}
```

Deposit operation **POST**: localhost:8080/contas/depositar
```json
{
    "agencia": "20220101",
    "numero": "20220010000001",
    "valor": "500"
}
```

Transfer operation **POST**: localhost:8080/contas/sacar
```json
{
    "agencia": "20220101",
    "numero": "20220010000001",
    "valor": "10"
}
```

Withdrawal operation **POST**: localhost:8080/contas/transferir
```json
{
    "agencia": "20220101",
    "agenciaDestino": "20220301",
    "numero": "20220010000001",
    "numeroDestino" : "20220030000001",
    "valor": "100"
}
```

Check account balance **GET**: ``` localhost:8080/contas/consultar-saldo/20220101/20220010000001 ```

Check account statement **GET**: ``` http://localhost:8080/contas/extratos?idConta=1 ```

Check account by agency **GET**: ``` localhost:8080/contas/buscar-por-agencia/20220101 ```

Check account by cpf **GET**: ``` localhost:8080/contas/buscar-por-cpf/70943418496 ```

Check account by number **GET**: ``` localhost:8080/contas/buscar-por-numero/20220010000001```
