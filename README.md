# Beauty Salon Parser

## Overview

Beauty Salon Parser is a Spring Boot application that imports beauty salon data from the Google Places API, stores it in PostgreSQL, and displays the collected salons through a web interface.

The application currently imports beauty salons located in Warsaw, stores relevant business information, and allows users to browse and filter the collected data.

---

## How to Run the Application

### Prerequisites

* Java 21+
* PostgreSQL
* Google Places API Key
* Maven

### Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE beauty_salons;
```

Configure database credentials in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/beauty_salons
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Google Places API Configuration

Add your Google Places API key:

```properties
google.places.api-key=YOUR_API_KEY
google.places.base-url=https://maps.googleapis.com/maps/api/place/textsearch/json
```

Make sure the following Google APIs are enabled:

* Places API
* Place Details API

### Build and Run

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Or run the generated JAR:

```bash
java -jar target/beauty-salon-parser.jar
```

### Access the Application

Open:

```text
http://localhost:8080
```

---

## Technical Solution

### Backend

The application is built using:

* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Thymeleaf
* Kotlin

### External Integrations

Google Places API is used for:

* Searching beauty salons
* Retrieving detailed salon information

The importer collects salon identifiers through Google Text Search and then fetches detailed information for each salon using the Place Details endpoint.

### Data Storage

Salon data is persisted in PostgreSQL.

To avoid duplicates, the application checks whether a salon with the same name and address already exists before inserting a new record.

### Frontend

The frontend is intentionally lightweight and built with:

* Thymeleaf
* HTML5
* CSS3
* Vanilla JavaScript

Features include:

* Dark mode
* Client-side filtering
* Salon details modal
* Responsive table layout
* External website links

### Import Strategy

Google Places Text Search limits the number of results returned per query.

To gather a larger dataset, the importer performs multiple searches using different Warsaw districts and combines the results while removing duplicates.

---

## What I Would Improve With More Time

### Backend

* Add pagination and server-side filtering
* Implement scheduled imports using Spring Scheduler
* Add caching for Google API requests
* Introduce structured logging instead of console output
* Add unit and integration tests
* Add API rate-limit handling and retry mechanisms
* Store Google Place IDs to improve deduplication

### Frontend

* Add pagination for large datasets
* Improve mobile responsiveness
* Add advanced sorting
* Add export to CSV/Excel
* Add salon statistics and analytics dashboard

### Infrastructure

* Docker support
* Docker Compose configuration for PostgreSQL
* CI/CD pipeline
* Environment-based configuration management
* Monitoring and health checks

### Data Quality

* Better district detection
* Data validation and cleanup
* Automatic detection of closed businesses
* More accurate duplicate detection using Google Place IDs

---

## Assumptions

* The application focuses on beauty salons located in Warsaw.
* Google Places data is considered the source of truth.
* Duplicate prevention is currently based on salon name and address.
* The project prioritizes simplicity and readability over production-scale optimization.
