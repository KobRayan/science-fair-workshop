# Fete de la Science - Workshop Management System

## Project Overview

This is a full-stack web application designed to digitize the organization of the "Fete de la Science" (Science Fair) event. The system streamlines the management of scientific workshops, allowing participants—such as students and teachers—to browse available sessions and register for specific time slots. It also provides administrative tools for workshop leaders (Animateurs) and administrators to manage schedules and validate registrations.

The project is currently built using a Java Spring Boot backend with a Thymeleaf frontend. The architecture is designed to support a future migration to a separated Angular client.

## Technical Architecture

* **Backend:** Java 21, Spring Boot (Web, JPA, Session Management)
* **Frontend:** HTML5, CSS3, JavaScript, Thymeleaf
* **Database:** PostgreSQL (Production), H2 (Development)
* **Build Tool:** Maven

## Key Features

### Authentication and Access Control
The application implements a secure authentication system distinguishing between three user roles:
* **Participants:** Can browse workshops and manage their own registrations.
* **Animateurs (Workshop Leaders):** access specific dashboard views (under development).
* **Admins:** Have global oversight of the event.

Security measures include:
* **Role-Based Redirection:** Users are automatically routed to their specific dashboard upon login.
* **Route Protection:** URL guards prevent unauthorized access. For example, participants cannot access `/animateur_page` or `/admin/inscriptions`.
* **Data Isolation:** Security checks ensure logged-in users cannot manipulate or view the registration data of other participants.

### Workshop and Registration Management
* **User Registration:** New users can sign up directly through the interface as either Participants or Animateurs.
* **Browsing:** A catalog view allows users to explore available workshops.
* **Registration Logic:** Participants can sign up for specific time slots. If a user is logged in, their details are pre-filled to streamline the process.
* **Validation Workflow:** Administrators have a dedicated view to inspect all registrations and can manually "Accept" or "Refuse" them.

## Current Development Status

The core backend logic and database structures are fully operational. The project is in an iterative development phase with the following status:

* **Database:** The schema is finalized and functional, supporting relationships between participants, workshops, and time slots.
* **Data Entry:** Currently, workshops and time slots are initialized manually. A graphical interface for Animateurs to manually create workshops and slots at runtime is the next major feature.
* **User Management:** While user creation is functional, the specific "Animateur Management" page is currently a placeholder awaiting implementation.
* **Security:** Password storage currently uses plain text for prototyping purposes. Integration of BCrypt for password hashing is scheduled for the next security sprint.
* **Error Handling:** Basic exception handling is in place. Future updates will improve frontend feedback for HTTP 400 errors to provide a smoother user experience.
* **Dynamic Scheduling:** enabling full CRUD operations for workshops and time slots via the UI.
* **Advanced Security:** Completing the implementation of password encryption and CSRF protection.

## Future Roadmap

* **Frontend Migration:** Transitioning the view layer (or most of them) to a Single Page Application (SPA) using Angular.
* **Group Management:** Adding functionality for teachers to register groups or entire classes.
* **Containerization:** Implement Docker and Docker Compose to containerize the application and database, simplifying the setup process for developers and enabling consistent deployments.

## Installation

1.  Clone the repository.
2.  Configure the database connection settings in `src/main/resources/application.properties`.
3.  Build and run the application using Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
4.  Access the application at `http://localhost:8080`.
