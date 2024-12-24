# Grocery Booking API

A Spring Boot-based RESTful API for managing grocery items and orders. This application supports role-based access control for `ADMIN` and `USER` roles, with JWT-based authentication and SQLite as the database.

## Features

### Admin Features
- Add new grocery items
- View all grocery items
- Update grocery item details (name, price, inventory)
- Delete grocery items

### User Features
- View available grocery items
- Place an order for multiple grocery items

---

## Technologies Used
- **Java Spring Boot**: Backend framework
- **JWT (JSON Web Token)**: For secure authentication
- **SQLite**: Lightweight database
- **Hibernate**: ORM for database operations
- **Postman**: API testing
- **Maven**: Dependency management
- **Docker**: Containerization support

---

## Setup and Installation

### Clone the Repository
```bash
git clone https://github.com/Nimisha888/qp-assessment.git
cd qp-assessment

## **Prerequisites**

Ensure the following are installed on your system:

- [Docker](https://www.docker.com/products/docker-desktop) (version 20.10 or later)
- [Docker Compose](https://docs.docker.com/compose/) (version 2.0 or later)

### Create Docker image and start the appplication

docker-compose build
docker-compose up

Thank You!!