# Rent a car
**Spring Boot Rest API, Course Project**<br>

**1. Dependencies**:<br>

- Spring Web;
- Spring Data JDBC;
- JDBC API;
- H2 Database;
- Flyway.

**2. Entity Relationship Diagram**:<br>

<img src="https://github.com/user-attachments/assets/5da003ed-4ff8-4a8c-97c0-d4ba1e2c5359" width="80%"><br>

**3. Controllers**:<br>

**Cars:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /cars/{carId} | Get a car by id. |
| GET /cars/?location={location} | List all cars in the current location. |
| GET /cars/locationByCustomer/{customerId} | List all cars available on the location of the current customer by id. |
| POST /cars | Create a new car. |
| PUT /cars/{carId} | Update a car by id. |
| DELETE /cars/{carId} | Delete a car by id. |

**Customers:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /customers | List all customers. |
| GET /customers/{customerId} | Get a customer by id. |
| POST /customers | Create a new customer. |
| PUT /customers/{customerId} | Update a customer by id. |
| DELETE /customers/{customerId} | Delete a customer by id. |

**Offers:**
| Method and Endpoint  | Result |
| ------------- | ------------- |
| GET /offers | List all offers. |
| GET /offers/{offerId} | Get an offer by id. |
| GET /offers/customer/{customerId} | List all available offers of an existing customer by id. |
| POST /offers | Create a new offer. |
| PUT /offers/{offerId}/accept | Accept an existing offer by id. |
| DELETE /offers/{offerId} | Delete an offer by id. |

**4. Resources**:<br>

Custom Repositories:<br>
- https://medium.com/code-with-farhan/spring-boot-database-connection-jdbc-vs-jpa-hibernate-edc9708966fc

Exceptions:<br>
- https://medium.com/@timzowen/building-reactive-rest-apis-with-spring-boot-3-2-0-part-iv-final-599e14e2fee5
