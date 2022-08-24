# Microservices e RabbitMQ
ANDAMENTO - Estudo sobre Microservices com Spring Cloud e Mensageira RabbitMQ

* Como funciona um Arquitetura Microservices:

![image](https://user-images.githubusercontent.com/101612046/186195195-c12f7c24-633a-45ae-a285-66548589fe2d.png)

<p>Comunicação entre services (síncrona) - Feign</p>
<p>Comunicação entre mensageria (assíncrona) - RabbitMQ</p>

# Conteúdo do Curso

* Criação do Eureka Server

* Criação do Cliente Service

* Criação do Cloud Gateway

* Criação do Cartão Service

* Criação do Avaliador de Crédito

* Criação do Serviço de Mensageria

</br>

# Mensageria

* Como funciona mensageria:

![image](https://user-images.githubusercontent.com/101612046/186403170-7280f517-59d3-4a06-9f31-0ad8b3c91d30.png)

* Instalar via Docker e fazer login no  RabbitMQ

```cmd
docker run -it --name microservicerabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management

```
<p>http://localhost:15672</p>
<p>username: guest</p>
<p>password: guest</p>

![image](https://user-images.githubusercontent.com/101612046/186440388-dcb8680d-a943-4200-9dca-02e63603507e.png)

</br>

# Postman

![image](https://user-images.githubusercontent.com/101612046/186402011-4e935c4c-ba8a-4b60-bbc5-8e690aa2e3c1.png)
![image](https://user-images.githubusercontent.com/101612046/186440089-a5d6c0bf-a36b-4deb-8bc0-ced0b093e1a5.png)
![image](https://user-images.githubusercontent.com/101612046/186440212-e6694a0c-c873-4616-9e02-73963efc1d66.png)




