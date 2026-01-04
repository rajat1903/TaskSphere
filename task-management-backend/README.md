# TaskSphere - Task Management Backend

A comprehensive microservices-based task management system built with Spring Boot, featuring service discovery, API gateway, and JWT authentication.

## 🏗️ Architecture

This project follows a microservices architecture pattern with the following services:

### Services Overview

1. **Eureka Server** (Port: 8070)
   - Service discovery and registration server
   - Enables microservices to find and communicate with each other

2. **API Gateway** (Port: 5000)
   - Single entry point for all client requests
   - Routes requests to appropriate microservices
   - Handles CORS configuration
   - Load balancing using Eureka service discovery

3. **Task Service** (Port: 5002)
   - Manages task creation, updates, and deletion
   - Handles task assignment to users
   - Task status management
   - Database: `task_task_service`

4. **Task Submission Service** (Port: 5003)
   - Manages task submissions
   - Handles submission-related operations
   - Database: `task_submission_service`

5. **Task User Service** (Port: 5001)
   - User authentication and authorization
   - JWT token generation and validation
   - User registration and login
   - Database: `task_user_service`

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Cloud 2025.0.0**
- **Spring Cloud Gateway** - API Gateway
- **Netflix Eureka** - Service Discovery
- **Spring Security** - Authentication & Authorization
- **JWT (JSON Web Tokens)** - Token-based authentication
- **Spring Data JPA** - Database operations
- **MySQL** - Relational database
- **OpenFeign** - Service-to-service communication
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

## 📋 Prerequisites

Before running the application, ensure you have:

- Java 21 or higher
- Maven 3.6+
- MySQL 8.0+ installed and running
- MySQL databases created:
  - `task_task_service`
  - `task_submission_service`
  - `task_user_service`

## 🛠️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/rajat1903/TaskSphere.git
cd task-management-backend
```

### 2. Database Configuration

Create the following MySQL databases:

```sql
CREATE DATABASE task_task_service;
CREATE DATABASE task_submission_service;
CREATE DATABASE task_user_service;
```

Update database credentials in each service's `application.yaml` file:
- `task-service/src/main/resources/application.yaml`
- `task-submission-service/src/main/resources/application.yaml`
- `task-user-service/src/main/resources/application.yaml`

### 3. Build the Project

Build all services using Maven:

```bash
# Build all services
mvn clean install
```

Or build individual services:

```bash
cd eureka-server && mvn clean install
cd ../gateway && mvn clean install
cd ../task-service && mvn clean install
cd ../task-submission-service && mvn clean install
cd ../task-user-service && mvn clean install
```

### 4. Run the Services

**Important:** Services must be started in the following order:

1. **Start Eureka Server** (must be started first):
```bash
cd eureka-server
mvn spring-boot:run
```
Eureka Dashboard: http://localhost:8070

2. **Start Task User Service**:
```bash
cd task-user-service
mvn spring-boot:run
```

3. **Start Task Service**:
```bash
cd task-service
mvn spring-boot:run
```

4. **Start Task Submission Service**:
```bash
cd task-submission-service
mvn spring-boot:run
```

5. **Start API Gateway** (start last):
```bash
cd gateway
mvn spring-boot:run
```

### Eureka Dashboard

Once all services are running, you can view the Eureka Dashboard at http://localhost:8070 to see all registered microservices:

![Eureka Dashboard](images/eureka-dashboard.png)

The dashboard shows:
- **Registered Services**: GATEWAY-SERVICE, SUBMISSION-SERVICE, TASK-SERVICE
- **Service Status**: All services should show as "UP"
- **Service Instances**: Hostname and port information for each service
- **System Status**: Uptime, renews threshold, and other system metrics

## 📡 API Endpoints

### Authentication (via Gateway: http://localhost:5000)

#### User Registration
```
POST /auth/signup
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "role": "USER"
}
```

#### User Login
```
POST /auth/signin
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}
```

Response includes JWT token in the response body.

### Task Management (via Gateway: http://localhost:5000)

All task endpoints require `Authorization` header with JWT token:
```
Authorization: Bearer <your-jwt-token>
```

#### Create Task
```
POST /api/tasks
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Task Title",
  "description": "Task Description",
  "image": "image-url",
  "assignedUserId": 1,
  "tags": ["tag1", "tag2"],
  "status": "PENDING",
  "dueDate": "2024-12-31"
}
```

#### Get Task by ID
```
GET /api/tasks/{id}
Authorization: Bearer <token>
```

#### Get All Tasks
```
GET /api/tasks?status=PENDING
Authorization: Bearer <token>
```

#### Get User's Assigned Tasks
```
GET /api/tasks/user?status=PENDING
Authorization: Bearer <token>
```

#### Assign Task to User
```
PUT /api/tasks/{id}/user/{userId}/assigned
Authorization: Bearer <token>
```

#### Update Task
```
PUT /api/tasks/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Updated Title",
  "description": "Updated Description",
  ...
}
```

#### Complete Task
```
PUT /api/tasks/{id}/complete
Authorization: Bearer <token>
```

#### Delete Task
```
DELETE /api/tasks/{id}
Authorization: Bearer <token>
```

### Submission Management (via Gateway: http://localhost:5000)

```
POST /api/submission
GET /api/submission/task/{taskId}
GET /api/submission/{id}
PUT /api/submission/{id}
```

## 🔐 Security

- JWT-based authentication
- Password encryption using BCrypt
- Role-based access control
- Spring Security integration
- Token validation for protected endpoints

## 🗂️ Project Structure

```
task-management-backend/
├── eureka-server/          # Service Discovery Server
├── gateway/                # API Gateway
├── task-service/           # Task Management Service
├── task-submission-service/# Task Submission Service
└── task-user-service/      # User Authentication Service
```

Each service follows a standard Spring Boot structure:
```
service-name/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/project/
│   │   │       ├── controller/    # REST Controllers
│   │   │       ├── service/       # Business Logic
│   │   │       ├── repository/    # Data Access Layer
│   │   │       ├── model/         # Entity Models
│   │   │       └── exception/     # Exception Handlers
│   │   └── resources/
│   │       └── application.yaml   # Configuration
│   └── test/                      # Unit Tests
└── pom.xml                        # Maven Dependencies
```

## 🔄 Service Communication

- **Service Discovery**: All services register with Eureka Server
- **Inter-Service Communication**: Uses OpenFeign for REST calls
- **Load Balancing**: Gateway uses Eureka for load-balanced routing
- **API Gateway**: Routes all external requests to appropriate services

## 🧪 Testing

Run tests for each service:

```bash
cd <service-name>
mvn test
```

## 📝 Configuration

Key configuration files:
- `application.yaml` in each service directory
- Database connection settings
- Eureka server URL
- Service ports
- JWT secret keys

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is open source and available under the MIT License.

## 👤 Author

**Rajat**

- GitHub: [@rajat1903](https://github.com/rajat1903)

## 🙏 Acknowledgments

- Spring Boot Team
- Spring Cloud Team
- Netflix Eureka Team

---

**Note**: Make sure all services are running and registered with Eureka before making API calls through the Gateway.

