# Gym-Guru
Below, after expanding the collapsed section you can find a Project content:
<details><summary>Project content</summary>
<p>
  
---

The GYM-GURU application allows users to easily find a personal trainer and order a workout plan with a diet. The application enables registered personal trainers to search for meals by name using the Edamam API service. Trainers can also use the Wger exercise database and modify their added plans. The API of the backend application is secured by a JWT token and all passwords in the database are encrypted. The application repository is controlled by CircleCI. The frontend of the application is available after launching the Java Vaadin frontend project at http://localhost:8081/gymguru/. </p>
</details>

# Project Specification
Below, after expanding the collapsed section you can find a Project Specification details:

<details><summary>Project Specification Details</summary>
<p>
  
---

- Users

    * Get user by id

    * Get user by email

    * Create user

    * Update user

    * Change password

---

- Trainers

    * Get all trainers

    * Get trainers by specialization

    * Get trainer by id

    * Add trainer

    * Update trainer

    * Change password

---

- Subscriptions:

    * Get subscriptions by trainer id

    * Get subscirptions without created plan

    * Get subscriptions with created plan

    * Get subscription by user id

    * Check subscription status by user id

    * Create subscription

    * Extend subscription

---  

- Plans:

    * Get plan by user id

    * Create plan

    * Update plan

--- 

- Meals:

    * Get meal by plan id

    * Update meal

--- 

- Exercises:

    * Get exercise by plan id

    * Update exercise

--- 

- ExternalApi:

    * Get openAi response

    * Get meals by name

    * Get exercises by category id

    * Get exercise categories

--- 

</p>
</details>

# DATABASE SCHEMA
Below, after expanding the collapsed section you can find a Project Database schema:

<details><summary>Database schema</summary>
<p>
 
---

CREATE TABLE CREDENTIALS (

    ID BIGINT NOT NULL AUTO_INCREMENT,
    
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    
    PASSWORD VARCHAR(255) NOT NULL,
    
    ROLE ENUM('USER', 'TRAINER') NOT NULL,
    
    PRIMARY KEY (ID)
);

---

CREATE TABLE USERS (

    ID BIGINT NOT NULL AUTO_INCREMENT,

    FIRST_NAME VARCHAR(255) NOT NULL,
    
    LAST_NAME VARCHAR(255) NOT NULL,
    
    CREDENTIAL_ID BIGINT NOT NULL,
    
    PRIMARY KEY (ID),
    
    CONSTRAINT FK_CREDENTIAL
    
        FOREIGN KEY (CREDENTIAL_ID)
        
        REFERENCES CREDENTIALS(ID)
        
        ON DELETE CASCADE
);
  
---

CREATE TABLE TRAINERS (

    ID BIGINT NOT NULL AUTO_INCREMENT,
    
    FIRST_NAME VARCHAR(255) NOT NULL,
    
    LAST_NAME VARCHAR(255) NOT NULL,
    
    TRAINER_DESCRIPTION VARCHAR(500) NOT NULL,
    
    EDUCATION VARCHAR(255) NOT NULL,
    
    MONTH_PRICE DECIMAL(10,2) NOT NULL,
    
    SPECIALIZATION ENUM('Strength', 'Health', 'Looks') NOT NULL,
    
    CREDENTIAL_ID BIGINT NOT NULL,
    
    PRIMARY KEY (ID),
    
    CONSTRAINT FK_CREDENTIAL
    
        FOREIGN KEY (CREDENTIAL_ID)
        
        REFERENCES CREDENTIALS(ID)
        
        ON DELETE CASCADE
);

---

CREATE TABLE SUBSCRIPTIONS (

    ID BIGINT NOT NULL AUTO_INCREMENT,
    
    PRICE DECIMAL(10, 2) NOT NULL,
    
    START_DATE DATE NOT NULL,
    
    END_DATE DATE NOT NULL,
    
    USER_ID BIGINT NOT NULL,
    
    TRAINER_ID BIGINT NOT NULL,
    
    PRIMARY KEY (ID),
    
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE,
    
    FOREIGN KEY (TRAINER_ID) REFERENCES TRAINERS(ID) ON DELETE RESTRICT

);

---

CREATE TABLE PLANS (

    ID BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    
    DIET_DESCRIPTION VARCHAR(500) NOT NULL,
    
    TRAINING_DESCRIPTION VARCHAR(500) NOT NULL,
    
    USER_ID BIGINT NOT NULL,
    
    TRAINER_ID BIGINT,
    
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE,
   
    FOREIGN KEY (TRAINER_ID) REFERENCES TRAINERS(ID),
    
    CONSTRAINT FK_PLANS_USERS UNIQUE (USER_ID),
    
    CONSTRAINT FK_PLANS_TRAINERS UNIQUE (TRAINER_ID)

);

---

CREATE TABLE MEALS (

    ID BIGINT NOT NULL AUTO_INCREMENT,
    
    NAME VARCHAR(255) NOT NULL,
    
    COOK_INSTRUCTION VARCHAR(1000) NOT NULL,
    
    PLAN_ID BIGINT,
    
    PRIMARY KEY (ID),
    
    CONSTRAINT FK_PLAN_MEALS FOREIGN KEY (PLAN_ID) REFERENCES PLANS(ID)

);

---

CREATE TABLE EXERCISES (

    ID BIGINT NOT NULL AUTO_INCREMENT,
    
    NAME VARCHAR(255) NOT NULL,
    
    DESCRIPTION VARCHAR(1000) NOT NULL,
    
    SERIES_QUANTITY INT NOT NULL,
    
    REPETITIONS_QUANTITY INT NOT NULL,
    
    PLAN_ID BIGINT NOT NULL,
    
    PRIMARY KEY (ID),
    
    CONSTRAINT FK_EXERCISE_PLAN_ID FOREIGN KEY (PLAN_ID) REFERENCES PLANS (ID)

);

---

</p>
</details>

# RUN INSTRUCTION
Below, after expanding the collapsed section you can find a Project run instruction:

<details><summary>Run insctruction</summary>
<p>

1. Set the server port to 8080 in application.properties file.

2. Create a database according to the data from the application.properties file:

    * spring.datasource.username=karolWeb
    
    * spring.datasource.password=BFzx156_tc0

3. Run the application.

4. Run the frontend project.

5. Go to http://localhost:8081/gymguru/

In case of no response from the OpenAI API (OpenAiClient), update the API key in the application.properties file. It will be located in the latest commit of the README.md file.

Current API key:
* openai.api.key=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

---
</p>
</details>

# FRONTEND PROJECT
Below, after expanding the collapsed section you can find a Frontend project link:

<details><summary>Frontend project</summary>
<p>

- Frontend project

    *  https://github.com/Karol002/Gym-Guru-Frontend/blob/e53e62ea421aeec96827e1f6c33f5f27dc9f07c1/README.md

---

</p>
</details>