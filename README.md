### 5.01 Advanced Spring Framework with WebFlux Level 1

# Blackjack Game API  

In this practical exercise, we will create a Java API with Spring Boot for a Blackjack game. The API will be designed to connect to and manage information in two different databases: MongoDB and MySQL. The Blackjack game will be implemented with all the necessary functionalities to play, including player management, card hands, and game rules.  

This application must be properly tested and documented using `README.md`, Swagger, etc.  

## Level 1  

### Basic Implementation:  

- **Development of a Reactive Application with Spring WebFlux**  
  A purely reactive application includes choosing a reactive approach, configuring a reactive MongoDB, and implementing reactive controllers and services.  

- **Global Exception Handling**  
  Implement a `GlobalExceptionHandler` to handle exceptions globally within the application.  

- **Database Configuration**  
  Configure the application to use two database schemas: MySQL and MongoDB.  

- **Controller and Service Testing**  
  Implement unit tests for at least one controller and one service using JUnit and Mockito.  

- **API Documentation with Swagger**  
  Use Swagger to automatically generate API documentation.  
  http://localhost:8080/swagger-ui.html

### Steps to Follow:  

1. **API Design:** Define the necessary endpoints for managing a Blackjack game, including game creation, making plays, etc.  
2. **Database Connection:** Configure connections to MongoDB and MySQL. Create the necessary Java entities to represent the game data.  
3. **Unit Testing:** Write unit tests for each endpoint and core API function using JUnit and Mockito. Ensure the API works correctly and that database operations are performed as expected. Test at least one service and one controller.  

---

## **Game Endpoints**  

### **Create Game**  
- **Method:** `POST`  
- **Description:** Creates a new Blackjack game.  
- **Endpoint:** `/game/new`  
- **Request Body:** New player's name.  
- **Input Parameters:** None  
- **Successful Response:** `201 Created` with information about the created game.  

### **Get Game Details**  
- **Method:** `GET`  
- **Description:** Retrieves details of a specific Blackjack game.  
- **Endpoint:** `/game/{id}`  
- **Input Parameters:** Unique game identifier (`id`).  
- **Successful Response:** `200 OK` with detailed game information.  

### **Make a Move**  
- **Method:** `POST`  
- **Description:** Performs a move in an existing Blackjack game.  
- **Endpoint:** `/game/{id}/play`  
- **Input Parameters:** Unique game identifier (`id`), move data (e.g., move type and bet amount).  
- **Successful Response:** `200 OK` with the move result and the current game state.  

### **Delete Game**  
- **Method:** `DELETE`  
- **Description:** Deletes an existing Blackjack game.  
- **Endpoint:** `/game/{id}/delete`  
- **Input Parameters:** Unique game identifier (`id`).  
- **Successful Response:** `204 No Content` if the game is successfully deleted.  

### **Get Player Ranking**  
- **Method:** `GET`  
- **Description:** Retrieves the ranking of players based on their performance in Blackjack games.  
- **Endpoint:** `/ranking`  
- **Input Parameters:** None  
- **Successful Response:** `200 OK` with a list of players ordered by ranking position and score.  

### **Change Player Name**  
- **Method:** `PUT`  
- **Description:** Changes the name of a player in an existing Blackjack game.  
- **Endpoint:** `/player/{playerId}`  
- **Request Body:** New player name.  
- **Input Parameters:** Unique player identifier (`playerId`).  
- **Successful Response:** `200 OK` with updated player information.  
