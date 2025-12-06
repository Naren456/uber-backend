# Uber Backend

A backend built with Spring Boot and MongoDB.

## Features
- User Registration & Login (JWT Authentication)
- Ride Management (Create, Accept, Complete)
- Driver & Passenger Roles
- Input Validation & Global Exception Handling

## Setup

1. **Prerequisites**:
   - Java 21
   - MongoDB (running on `localhost:27017`)

2. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on `http://localhost:8089`.

## API Endpoints

### Authentication (Public)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user (Passenger/Driver) |
| POST | `/api/auth/login` | Login and get JWT Token |

### Passenger (Requires ROLE_USER)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/rides` | Request a new ride |
| GET | `/api/v1/user/rides` | View my requested rides |

### Driver (Requires ROLE_DRIVER)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/driver/rides/requests` | View all pending ride requests |
| POST | `/api/v1/driver/rides/{id}/accept` | Accept a ride request |

### Common (Authenticated)
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/rides/{id}/complete` | Mark a ride as completed |

## Example CURL Commands

### 1. Register User (Passenger)
```bash
curl -X POST http://localhost:8089/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"password123","role":"ROLE_USER"}'
```

### 2. Login User
```bash
curl -X POST http://localhost:8089/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"password123"}'
```
*Copy the token from the response.*

### 3. Create Ride (as User)
```bash
curl -X POST http://localhost:8089/api/v1/rides \
-H "Authorization: Bearer <TOKEN>" \
-H "Content-Type: application/json" \
-d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

### 4. Register Driver
```bash
curl -X POST http://localhost:8089/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"driver1","password":"password123","role":"ROLE_DRIVER"}'
```

### 5. Login Driver
```bash
curl -X POST http://localhost:8089/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"driver1","password":"password123"}'
```
*Copy the driver token.*

### 6. View Requests (as Driver)
```bash
curl -X GET http://localhost:8089/api/v1/driver/rides/requests \
-H "Authorization: Bearer <DRIVER_TOKEN>"
```

### 7. Accept Ride (as Driver)
```bash
curl -X POST http://localhost:8089/api/v1/driver/rides/{RIDE_ID}/accept \
-H "Authorization: Bearer <DRIVER_TOKEN>"
```

### 8. Complete Ride
```bash
curl -X POST http://localhost:8089/api/v1/rides/{RIDE_ID}/complete \
-H "Authorization: Bearer <TOKEN>"
```

## Postman Screenshots

### User Workflow
**Register User**
![Register User](postman_screenshots/user/user_register.png)

**Create Ride**
![Create Ride](postman_screenshots/user/user_create_ride.png)

**View User Rides**
![View User Rides](postman_screenshots/user/user_view_all.png)

### Driver Workflow
**Register Driver**
![Register Driver](postman_screenshots/driver/driver_register.png)

**View Pending Requests**
![View Requests](postman_screenshots/driver/driver_ride_request.png)

**Accept Ride**
![Accept Ride](postman_screenshots/driver/driver_accept_ride.png)

**Complete Ride**
![Complete Ride](postman_screenshots/driver/driver_complete_ride.png)
