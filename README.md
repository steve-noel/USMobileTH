# USMobile Take Home

This is a Spring Boot microservice that provides RESTful APIs to manage billing cycles, user profiles, and daily usage data. 


## Table of Contents
- [Thought Process](#thought-process)
- [Technologies](#technologies)
- [Setup Instructions](#setup-instructions)
- [Running With Docker](#running-with-docker)
- [Testing](#testing)
- [Documentation](#documentation)
- [Improvements](#improvements)

## Thought Process
I will use Spring Boot 3.x  and Java 22 to create a Restful microservice. I will use a free MongoDb Atlas database
(the database credentials are inside the `application.yaml` file) to store the data.
To insert initial data in the database I will use the Mongock database migration library. This allows 
me to write database migrations inside the codebase. For simplicity, I will use the Lombok library to 
make working with the java objects easier. Lombok also makes logging easier as well. I will use the flapdoodle library 
to add an embedded Mongo database to the integration tests.

Two of the required endpoints(create/update) deal with the user collection, I will create a user controller 
that contains both of these endpoints. The create endpoint should be a POST since it is creating a new user and 
the update endpoints should be a PUT since it is updating an existing user.

One of the endpoints fetches a users cycle history, I will create a cycle controller that defines this endpoint.
This endpoint should be a GET request as it is fetching data from the service.

One of the endpoints fetches a users daily usage for the current cycle, I will create a dailyUsage controller that defines this endpoint.
This endpoint should be a GET request as it is fetching data from the service.

## Technologies
- Java 22
- Spring Boot 3.x
- MongoDB
- Maven
- JUnit 5
- Lombok
- Mongock
- flapdoodle embedded MongoDB

## Setup Instructions

### Prerequisites
- Java 22
- Maven 3.6 or higher

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/steve-noel/USMobileTH.git
    cd USMobileTH
    ```
   
2. Build the project:
    ```bash
    mvn clean install
    ```
4. Run the Spring Boot Application
    ```bash
    mvn spring-boot:run
    ```
## Running With Docker
### Make sure you have docker installed your computer.

1. To build the Docker image, navigate to the root directory containing the `Dockerfile` and run:
   ```bash
   docker build -t us-mobile-th  .
   ```
2. To run the Docker container, use the following command:
   ```bash
   docker run -p 8080:8080 us-mobile-th
   ```
## Testing
To run unit & integration tests:
```bash
mvn test
```

## Documentation
API documentation is available through Swagger. 
Once the application is running, navigate to http://localhost:8080/api/v1/swagger-ui/index.html 
to view the API documentation and test endpoints interactively.

## Improvements

- Create a unique constraint in the user collection on email so two users cant have the same email.
- Add more validation to the API endpoints(ex. usedInMb should be a positive integer).
- Add pagination to endpoints that return a list of results.
- Add indexes to the daily_usage/cycle collections on (userId and mdn) to speed up queries.
- Add `createDate` & `updatedDate` to all collections for auditing purposes.
- Add JWT authentication to each endpoint in the API.
- Return better error messages from the API.
- Use TestContainers for the embedded MongoDB instead of flapdoodle.
