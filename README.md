# E-Commerce CRUD Using RabbitMQ, Lombok, ModelMapper and Swagger

## 📝 Table of Contents

- [About](#about)
- [Getting Started](#getting_started)
- [Tests and Usage](#usage)
- [Built Using](#built_using)
- [Authors](#authors)
- [Acknowledgments](#acknowledgement)

## 🧐 About <a name = "about"></a>

- In this microservice, it was explored the use of: 
  - Message broker RabbitMQ, 
  - Lombok,
  - Model Mapper,
  - Swagger.

## 🏁 Getting Started <a name = "getting_started"></a>

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What you need to install/have installed in order to execute this piece of code.

```
- Docker
- Java Language
- IDE - Intellij
- Postman
```

## 🔧 Running the tests and Usage <a name = "usage"></a>

- Go to the main application, run this code and in postman input the data that you want. 
- As soon as you have posted it, go to the port that you defined for RabbitMQ, login in and there you can check the messages that were generated and are in the Queue
- For example:
```
- For Swagger:  http://localhost:8080/swagger-ui.html
- For RabbitMQ: http://localhost:15672
```

## ⛏️ Built Using <a name = "built_using"></a>

- [RabbitMQ](https://www.rabbitmq.com/) - Message Broker
- [Docker](https://www.docker.com/products/docker-desktop/) - Container
- [Java](https://www.oracle.com/java/technologies/java-se-glance.html) - Language
- [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) - IDE


## ✍️ Authors <a name = "authors"></a>

- [@crizostomo](https://https://github.com/crizostomo) - Initial work

## 🎉 Acknowledgements <a name = "acknowledgement"></a>

- This piece of code was built thanks to @Caio Costa, which is my friend, and on top of that he is professor at this moment.
