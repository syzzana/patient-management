 Obsidian notes:
### Link to the course: #1 
11h Course: Build & Deploy a Production-Ready Patient Management System with Microservices: Java Spring Boot AWS
https://www.youtube.com/watch?v=tseqdcFfTUY 
- Link to github project: https://github.com/chrisblakely01/java-spring-microservices 
- discord: https://discord.com/invite/CycJRUNvb5 
- website: https://www.codejackal.com/ 
- pictures  of spring boot architecture explained in:/home/syzana/Documents/SpringBoot Architecture/sp_architecture.png


#note DTO = Data Transfer Object 
Keeps the internal domain model (your db entities) hidden from the client. 

Apache Tomcat
```
Is a 
```

-------

#Time Min 29' 27th Dec 2025
#Time Min 1:13' 27th Dec 2025 next step create the controller 
#Time Min 1:23' 27th Dec error on sql jdbc trying to resolve    Error #2 
#Time 1:43:00 request validation handler 
#Time 1:49:11 11.error-handling-request-business-logic-custom-exception 
#Time 1:19:00 checking param in swagger why is not being mapped correctly 
#Time 2:30:00 updating the update endpoint validation because 
#Time 2:49 Intro to docker , time updated 3:04:00 start docker daemon in linux 
#Time 3:28  creating docker services for database and patient services
#Time 3:49 implementing the billing service  - 3:58 time 4:00
#Time 4:43:50 intro to kafka - before that fix the error with the database
#Time 5:29 5:33 fix the kafka producer and topic test in docker - make it work - still fix the issue with kafak making the application to start without failing (error fixed with dependency artifact id changed for kafak srping boot depenedency, aslo a solution by claude to configure the KafkaTemplate bean manually fixed it. I kept the solution with the new artifact id for the kafka dependency)
#Time 5:51 docker analytics service 
#Time 6:15 api gateway setu rules 


------

#note 
data.sql - under resources so every time when we start the app we have some dummy data in our app

#note 
Lambda :: to shorten the expression from
```java
patients.stream().map(patient-> PatientMapper.toDTO(patinet)).toList();
```
to
```java
patients.stream().map(PatientMapper::toDTO).toList()
```

#note @ControllerAdvice 
How does @ControllerAdvice handles Exceptions which are thrown from the Service layer in spring boot. 
Answer from perplexity: 
```
@ControllerAdvice identifies exceptions through Spring's exception resolution mechanism. When an exception is thrown in a service and propagates up to the controller layer, Spring's DispatcherServlet catches it and passes the exact exception instance to registered @ControllerAdvice classes via the HandlerExceptionResolver chain.​[](https://www.geeksforgeeks.org/advance-java/spring-mvc-controlleradvice-annotation-for-global-exception-handling/)​

## Exception Matching Process

Spring uses type-based matching to route exceptions. Each @ExceptionHandler method in a @ControllerAdvice class specifies the exact exception class (or superclass) it handles, such as @ExceptionHandler(MyCustomException.class). The framework inspects the thrown exception's type and invokes the first matching handler based on method specificity and @Order annotations.[](https://www.geeksforgeeks.org/advance-java/spring-mvc-controlleradvice-annotation-for-global-exception-handling/)​

## Propagation Flow

Exceptions thrown in services bubble up through controllers without losing identity. Spring's ExceptionHandlerExceptionResolver scans all @ControllerAdvice beans, matches the exception type, and injects the original exception object as a method parameter, preserving stack trace and message details for custom handling.[](https://gustavopeiretti.com/spring-boot-rest-error-handling-controller-advice/)​​

## Key Benefits

- **Global Scope**: One @ControllerAdvice handles exceptions across all controllers.
    
- **Precise Typing**: Parameter types ensure the correct handler receives the specific exception instance.
    
- **Customization**: Access full exception details (cause, message, stack trace) for tailored HTTP responses
```


#note @Validated(Default.class)
```
It tells spring to validate our request body based on the validation we put in the class dto
```
----------

 #note   Creating a specific Validator interface, to avoid which attributes can be updated on the put call, in this case the registered date is something that we need only on create, but we do not update it anymore. To have less verbose code, meaning less classes to maintain, then you just add this group to the field.  Meaning you only use it on the POST teh validator class if you want this attribute to be considered there. 
 ```java 
 @NotBlank(groups = CreatePatientValidationGroup.class, message = "Registered Date is required")  
private String registeredDate;
 ```
 ... and then in the Controller as well, you have to mention it on the parameters, so it DOES   GET  considered in the POST call only. Like below:
 ```java 
 @PostMapping  
public ResponseEntity<PatientResponseDTO> createPatient(  
    @Validated({Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDTO patientRequestDTO) {  
  
    PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);  
    return ResponseEntity.ok().body(patientResponseDTO);  
}
 ```



#hint #note #important #logic
``` 
In the updatePatient code, we first check the id if it exists, and then we check if the email exists. This will cause a clash. 
Check minute 2:30:00 
So for that we add a method at the repository 
*existsByEmailAndIdNot(String email, UUID id)*
so that when we want to update a patient, we check that if there is someone with the same email already and id, to flag the record when we are trying to update teh same record, so that it does not clash 

```


#note 
- https://springdoc.org/
#note #docker-compose-yaml file 
	unable to get image 'postgres:latest': permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get "http://%2Fvar%2Frun%2Fdocker.sock/v1.51/images/postgres:latest/json": dial unix /var/run/docker.sock: connect: permission denied
	
	Reviewed 10 sources
	
	The "permission denied ... /var/run/docker.sock" error during `postgres:latest` pull means your user lacks access to the Docker socket despite the daemon running. Fix by adding your user to the `docker` group and refreshing permissions.[](https://stackoverflow.com/questions/48957195/how-to-fix-docker-permission-denied)​
	
	## Primary Fix (Linux/Ubuntu/Debian)
	
	text
	
	`sudo usermod -aG docker $USER newgrp docker  # Or log out/in`
	
	Test: `docker ps` (no sudo). Then retry `docker compose up -d` to pull postgres.[](https://stackoverflow.com/questions/44678725/cannot-connect-to-the-docker-daemon-at-unix-var-run-docker-sock-is-the-docker)​
	
	## Socket Ownership (If Group Fails)
	
	text
	
	`sudo chown root:docker /var/run/docker.sock sudo chmod 660 /var/run/docker.sock`


#### GRPC Architecture 
#note gRPC Architecture 

#note db docker access 
docker exec -it patient-service-db psql -U admin_user -d db


#### Intro to Kafka 

- gRPC and REST are synchronous - good for 1-1 microservice communication 
- But what about 1-to-many microservice communication?
- to read more on configuring kafka in spring https://www.confluent.io/learn/spring-boot-kafka/ 
------

Apache Kafka acts as a high-speed messaging system that handles massive streams of data reliably, like a central post office sorting and storing letters (messages) for multiple senders and receivers. Producers send data to topics, which Kafka stores in ordered partitions across servers (brokers) for durability and scalability, while consumers pull data from those topics at their own pace. This setup ensures fault tolerance through replication and allows replaying old messages, unlike traditional queues that delete after delivery.[](https://aws.amazon.com/what-is/apache-kafka/)​

## Core Concepts

- **Topics**: Categories for messages, like "orders" or "logs," divided into partitions for parallel processing.[](https://www.geeksforgeeks.org/apache-kafka/what-is-apache-kafka-and-how-does-it-work/)​
    
- **Producers**: Apps that publish messages to topics.[](https://www.geeksforgeeks.org/apache-kafka/apache-kafka/)​
    
- **Consumers**: Apps that subscribe to topics and read messages using offsets to track progress.[](https://www.redpanda.com/guides/kafka-architecture)​
    
- **Brokers**: Servers managing storage and replication; a cluster has multiple for high availability.[](https://www.redpanda.com/guides/kafka-architecture)​



#### API Gateways 

- An API Gateway acts as a single entry point for clients to interact with multiple services 
- Routes requests to the micro-services, hiding the internal addresses from the clients 
- Handles concerns like authentication, authorization, logging, monitoring, rate limiting, and caching centrally, that are common to all micro-services 
- Without gateway we have to expose ports to the internet which is not secure




#### Intro to AUTH 
