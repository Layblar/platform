# Important Information

__by [Jakob Feistenauer](https://github.com/yescob)__

## Table of Content

1. [Introduction](#introduction)
  1.1 [Quarkus Tutorials](#tutorials)
2. [Running in development mode](#running-in-development-mode)
3. [Authorization](#authorization)
4. [Handeling of Ids](#handeling-of-ids)
5. [Smart-Meter data pipeline](#smart-meter-data-pipeline)
6. [Labeled Data Batches](#labeled-data-batches)
7. [Deprecated APIs](#deprecated-apis)
8. [Data deletion](#data-deletion)

## Introduction

For all microservices we have used the Quarkus framework. Quarkus is a Java Framework specially design for microservices.
For more information visit: <https://quarkus.io/>

The individual projects were build with the following configurations:

- Gradle
- Java 11
- Quarkus 3.4.2

We also highly recommend having Docker Desktop installed and running when working with Quarkus. The [Dev Services](https://quarkus.io/guides/dev-services) features can be extremely handy during development.

We assume that you are already familiar with microservice environments and the different concepts assosiated with microservices.

### Tutorials

If you have experience with Spring Boot, many things will be familiar. However, if you are not familiar with Quarkus, we suggest that you look into the following tutorials to get a better understanding of how Quarkus works:

- <https://quarkus.io/guides/rest-json>
- <https://quarkus.io/guides/rest-client-reactive>
- <https://quarkus.io/guides/security-jwt>
- <https://quarkus.io/guides/security-jwt-build>
- <https://quarkus.io/guides/kafka-reactive-getting-started>
- <https://quarkus.io/guides/rabbitmq>
- <https://quarkus.io/guides/hibernate-orm-panache>
- <https://quarkus.io/guides/mongodb-panache>
- <https://docs.quarkiverse.io/quarkus-rabbitmq-client/dev/index.html>

## Running in development mode

Ensure you meet the following requirements:

- JDK 11+
- Gradle
- Docker Desktop (optional)

Before starting a service in development mode have a look into the `application.properties` file.

```properties
#kafka configuration
%dev.kafka.bootstrap.servers=localhost:9093
mp.messaging.incoming.household.connector=smallrye-kafka
mp.messaging.incoming.household.topic=household.public.event
mp.messaging.incoming.household.auto.offset.reset=earliest
mp.messaging.incoming.household.group.id=project-service-household-consumer

mp.messaging.incoming.project.connector=smallrye-kafka
mp.messaging.incoming.project.topic=project.public.event
mp.messaging.incoming.project.auto.offset.reset=earliest
mp.messaging.incoming.project.group.id=project-service-project-consumer
mp.messaging.incoming.project.failure-strategy=ignore
mp.messaging.incoming.project.broadcast=true

mp.messaging.outgoing.label-event.connector=smallrye-kafka
mp.messaging.outgoing.label-event.topic=label-event

quarkus.datasource.db-kind = postgresql
%dev.quarkus.datasource.username = project
%dev.quarkus.datasource.password = project
%dev.quarkus.datasource.jdbc.url = jdbc:postgresql://localhost:5435/project
quarkus.hibernate-orm.database.generation=update

# jwt key locations
%dev.mp.jwt.verify.publickey.location=publicKey.pem
# jwt issuer
%dev.mp.jwt.verify.issuer=layblar

%dev.quarkus.http.port=8086

# enable tracing
quarkus.datasource.jdbc.telemetry=true
```

Properties with the suffix `%dev` are only in effect when running the application in development mode. For connections like databases and Kafka make sure that a service is running on the corresponding port or update the configuration property. If you have Docker Desktop installed you can comment out the connection configurations. Quarkus will automatically start docker containers for the services and connect your application to them.

Start the application by running:

```console
./gradlew quarkusDev
```

> __NOTE__: Quarkus offers live-reloading.

## Authorization

All endpoints are secured and require a valid JWT. A valid JWT can be obtained through the login endpoint of the Gateway.

The JWT has the following structure and fields:

```json
{
  "iss": "testIssuer",
  "householdId": "15640ef8-8d6a-4501-8137-f1869df0118e",
  "userId": "4fa0324b-5341-4b5d-864f-3aa9b208dc3e",
  "researcherId": "24bc9d00-ac52-421b-a92d-d3d5bb05abd9",
  "sub": "6eae00ee-8d8c-4bf2-8ae7-25bed3aae1b6",
  "groups": [
    "User",
    "Researcher"
  ],
  "iat": 1706033940,
  "exp": 1734833940,
  "jti": "b6e93599-346b-4a64-8f54-dff930c03d10"
}
```

Currently we only support self-created JWT and RBAC based on Bearer Token Authorization. However it is possible to switch to OAuth2 without changing the code in each microservice. You just have to give the correct environment variables when starting the services as specified in this guide for [OAuth2 with Quarkus](https://quarkus.io/guides/security-oauth2).
However for OAuth2, the token generation has to be adapted. As you can see we save the `householdId`, `userId`, `researcherId` and the roles in the token. The ids are only generated when a user registeres a new household or researcher account. If you want to use an OAuth2 provider like KeyCloak you have to implement a new update logic for the tokens. Otherwise access will be denied as the tokens do not contain the necessary claims.

For more information on how to get tokens for your deployment visit the [depolyment](./deployment.html) documentation.

## Handeling of Ids

As it is optional to give an Id when adding or updating a device the following images shows how the Ids are handeled.

![Id generation process](./img/idprocess.svg)

The same process happens for deviceCategoryIds wether they come from the Household-Service or the Project-Service.

## Smart-Meter data pipeline

In our case the IoT device sends data over MQTT to the RabbitMQ Broker. MQTT topics are collected under the `"amq.topics"` exchange by default, with the routing key beeing the topic name. As we use AMQP for our Smart-Meter-Service we have to connect to this exchange. We only start listening to a topic after we have recived a `SmartMeterRegisteredEvent` from the Household-Service. Then we connect to the exchange with `smartMeterId` as the routing key.
Additionaly, before saving the messages to Timescale, we send the messages into a Kafka topic. The Smart-Meter-Service listens to this topic and persists all the messages into the database.
We have opted for this solution, because we planned to add stream processing to our architecture. By sending the messages to Kafka we can generate streams from this topic and process incoming data in real time (e.g prelabel data, match data with events).

## Labeled Data Batches

We labeling data over a long period of time, the list of datapoints grows very large. If a device was running for four hours that would be 2.880 Smart-Meter readings. To better larger amounts of data, we split the incoming data into batches of size 1000. One one hand we did this, because the max record size for Kafka is 1MB, on the other hand it improves the performances when saving the data to the database.

Currently the systems support labeled data with 18.000 datapoins, which is slightly more than a complete day of Smart-Meter readings.
Larger sets have to be split into multiple calls, otherwise you will recive error  code `412 Payload Too Large`.

## Deprecated APIs

Currently there a four deprecated APIs. This is because we have not implemented the functionalities yet.
Three apis are for the combination of households. Users should be able to join and leave other households. We found that the handeling of legacy data when switching households requires further considerations.
The last endpoint was meant to support labeling through events. The idea is that users send start and stop events and the data between those events is collected and labeled. This is one of the usecases we thought stream processing could be used for.

## Data deletion

Currently the platform supports only logical deletion of data. Removing a device, Smart-Meter or labeled data will delete the data from the database. However, as we use event sourcing and CQRS, the information is still present in the events.
Additionally we have not yet implemented the option for a user the delete his data completely. We planned on using a `HouseholdDeletedEvent` to send to all services, indicating that all data belonging to this household should be deleted. We have already setup event consumers in each service that can listen for such events.
