# Order_Management_System
Java 8, Spring Framework, Maven, JUnit, Mockito, Lombok, Mapstruct, MySQL, Swagger, Hexagonal Architecture

To start the application, start the OrderManagementSystemApplication class. Before this, a database schema must be created in the MySQL Workbench with the name order_management_system_db (this information is contained in the application.properties file) with username: root, password: root. The database schema will then be generated. A list of all endpoints after starting the application is available at: http://localhost:8080/swagger-ui/ (SwaggerUI), there is also an endpoint for creating random 10 orders(http://localhost:8080/swagger-ui/#/order-rest-controller/createDummyOrdersUsingPOST).
