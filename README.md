# Hotel Room Booking Service

A Spring Boot application that provides an asynchronous hotel room booking system with real-time status tracking.

## Features

- Asynchronous booking processing
- Real-time booking status tracking (PENDING, CONFIRMED, CANCELLED)
- Room capacity validation
- Date validation for check-in/check-out
- Price calculation based on room rates and guests
- Notification system for booking status updates

## Technology Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Project Lombok
- Jakarta Persistence
- Async Processing with CompletableFuture

## Prerequisites

- JDK 21
- PostgreSQL
- Maven

## Database Configuration

The application uses PostgreSQL. Configure your database connection in `application.yml`:
