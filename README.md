# Coding Challenge

## What's Provided

A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped with
data. The application contains
information about all employees at a company. On application start-up, an in-memory Mongo database is bootstrapped with
a serialized snapshot of the database. While the application runs, the data may be accessed and mutated in the database
without impacting the snapshot.

### How to Run

The application may be executed by running `gradlew bootRun`.

*Spring Boot 3 requires Java 17 or higher. This project targets Java 17. If you want to change the targeted Java
version, you can modify the `sourceCompatibility` variable in the `build.gradle` file.*

### How to Use

The following endpoints are available to use:

```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```

The Employee has a JSON schema of:

```json
{
  "title": "Employee",
  "type": "object",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
      "type": "string"
    },
    "position": {
      "type": "string"
    },
    "department": {
      "type": "string"
    },
    "directReports": {
      "type": "array",
      "items": {
        "anyOf": [
          {
            "type": "string"
          },
          {
            "type": "object"
          }
        ]
      }
    }
  }
}
```

For all endpoints that require an `id` in the URL, this is the `employeeId` field.

## What to Implement

This coding challenge was designed to allow for flexibility in the approaches you take. While the requirements are
minimal, we encourage you to explore various design and implementation strategies to create functional features. Keep in
mind that there are multiple valid ways to solve these tasks. What's important is your ability to justify and articulate
the reasoning behind your design choices. We value your thought process and decision-making skills. Also, If you
identify any areas in the existing codebase that you believe can be enhanced, feel free to make those improvements.

### Task 1

Create a new type called `ReportingStructure` that has two fields: `employee` and `numberOfReports`.

The field `numberOfReports` should equal the total number of reports under a given employee. The number of reports is
determined by the number of `directReports` for an employee, all of their distinct reports, and so on. For example,
given the following employee structure:

```
                   John Lennon
                 /             \
         Paul McCartney     Ringo Starr
                            /         \
                       Pete Best    George Harrison
```

The `numberOfReports` for employee John Lennon (`employeeId`: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4.

This new type should have a new REST endpoint created for it. This new endpoint should accept an `employeeId` and return
the fully filled out `ReportingStructure` for the specified `employeeId`. The values should be computed on the fly and
will not be persisted.

### Task 2

Create a new type called `Compensation` to represent an employee's compensation details. A `Compensation` should have at
minimum these two fields: `salary` and `effectiveDate`. Each `Compensation` should be associated with a specific
`Employee`. How that association is implemented is up to you.

Create two new REST endpoints to create and read `Compensation` information from the database. These endpoints should
persist and fetch `Compensation` data for a specific `Employee` using the persistence layer.

## Delivery

Please upload your results to a publicly accessible Git repo. Free ones are provided by GitHub and Bitbucket.
