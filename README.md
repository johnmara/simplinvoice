# Introduction
With this application, a company is able to manage its invoices and issue new invoices.  
It offers a cloud solution in order to support multi companies/users with a single deployment.  
It also supports the Greek directive to send real-time invoices to AADE myDATA. For this reason  
it can be easily used by an e-invoicing provider.

# Features
The main features of the application are:
- Dashboard with useful info
- Management of Products/Services
- Management of Customers/Suppliers
- Issue an Invoice and send it to MyDATA
- Publicly accesible screen with qrcode to view invoice
- Add a received invoice
- Configure invoice parameters, company and system

# Technology Stack
The application is a Web Application, written in Spring Boot with MVC pattern.
For page rendering, thymeleaf is used. For persistence layer, JPA with Hibernate is used.
Regarding the database, it is tested with MySQL but any Relation database should work.

# Deployment

## Demo environment
A demo is already deployed under [Simplinvoice Demo](https://simplinvoice.eu)  
_Send me an email in order to enable you, after your registration.  
In demo environment, invoices are sent to a mock endpoint._

## Local deployment

### Manual

#### System Requirements
- Java 1.8+
- Relational Database (MySQL is recommended)

#### Deploy
In order to create the executable, run
```bash
>mvn package
```
Then, you need to set the following **Environment Variables**.
**Windows**
```bash
>set MYSQL_HOST=HOST:PORT
>set MYSQL_USER=YOUR_DB_USER
>set MYSQL_PASS=YOUR_DB_PASS
>set MYSQL_DB=YOUR_DB_NAME
>set AADE_MYDATA_URL=https://mydata-dev.azure-api.net
```
**Linux**
```bash
>export MYSQL_HOST=HOST:PORT
>export MYSQL_USER=YOUR_DB_USER
>export MYSQL_PASS=YOUR_DB_PASS
>export MYSQL_DB=YOUR_DB_NAME
>export AADE_MYDATA_URL=https://mydata-dev.azure-api.net
```
In order to run the application, run
```bash
>java -jar simplinvoice-X.X.X.jar
```
Application will be reachable at `http://localhost:8080`.
You can also register it as windows or linux service in order to run it as daemon.

### Docker
You can also deploy the application with a Docker container.  
You can use [this](Dockerfile) Dockerfile to build the image and then run  
```bash
docker build -t IMAGE_NAME .
docker run --name CONTAINER_NAME -p 8480:8080 \
-e MYSQL_HOST='HOST:PORT' \
-e MYSQL_USER='YOUR_DB_USER' \
-e MYSQL_PASS='YOUR_DB_PASS' \
-e MYSQL_DB='YOUR_DB_NAME' \
-e AADE_MYDATA_URL='https://mydata-dev.azure-api.net' \
-d IMAGE_NAME
```
Application will be reachable at `http://localhost:8480`.

## Contribute
You can fork the project and start working with any open issue which is available here [Simplinvoice Open Issues](https://github.com/johnmara/simplinvoice/issues)  
Once you are done with your implementation you can submit a **Merge Request**.

### Development
This is a maven project and you can import it in any Java IDE.  
A Relational Database (MySQL is suggested) is required in order to run the application.

### License
The project is protected under Apache License 2.0