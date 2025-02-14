# Country Information API

This service provides an API to fetch country information, including flags and other details.

## Overview

This API allows you to retrieve data about countries, such as their names, flags, populations, currencies, and more. It's built using Java 17, Spring Boot, and Gradle, and it's designed to be easily containerized with Docker.

## Technologies Used

* **Java 17:** The programming language used for the service.
* **Spring Boot:** The framework for building the API.
* **Gradle:** The build system used for managing dependencies and building the application.
* **Docker:** The containerization platform for deploying the service.

## Getting Started

### Prerequisites

* Java 17 or later
* Gradle 7.0 or later (recommended)
* Docker (if you want to run the Dockerized version)

### Building the Application (without Docker)

1. Clone the repository: `git clone git@github.com:ayomirotoye/country_flags_api.git`
2. Navigate to the project directory: `cd country_flags_api`
3. Build the application: `./gradlew build`

### Running the Application (without Docker)

1. After building, you can run the application: `./gradlew bootRun`

### Running the Dockerized Application

1. Build the Docker image (specify the url upon which the frontend is running): `docker build --build-arg ALLOWED_ORIGINS=http://localhost:5173 -t country-info-api .` (Make sure you are in the project directory containing the `Dockerfile`)
2. Run the Docker container: `docker run -p 8080:8080 country-info-api` (This maps port 8080 on your host machine to port 8080 in the container)

## API Endpoints

(Replace these with your actual endpoints and provide clear descriptions)

* **GET /countries:** Retrieves a list of all countries.
* **GET /countries?countryIsoCode={countryIsoCode}:** Retrieves information about a specific country using its country ISO code (e.g., "US", "GB", "FR").
* **GET /countries?countryName={countryName}:** Retrieves the flag image for a specific country.  (If applicable)
* **GET /countries/search?searchText={countryIsoCode | countryName}:** Searches for the country information by either country ISO code or countryName
