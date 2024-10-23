# Index
- [Spring Publisher](#spring-publisher)
- [Prerequisites](#prerequisites)
- [Usage](#usage)
- [Links](#links)

## Spring Publisher
> Application designed for online book publishers. Manage books and authors.

## Prerequisites
- Java `22`
- Windows `11`

## Usage
1. At the root of the project, run the command ```./mvnw clean package```
2. From here, you can access the documentation at http://localhost:8080/doc.html. The username is **admin** and the 
   password is **123**.

## Testing
- To run mutation testing, execute the command ```./mvnw test-compile org.pitest:pitest-maven:mutationCoverage```.

## Links
- [ISBN 13 Generator](https://generate.plus/en/number/isbn)