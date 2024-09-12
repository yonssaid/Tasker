# Tasker - A Simple Task Management App
**Hosted at:** [Tasker App](https://tasker-app-bzga.onrender.com/)

## Overview
Tasker is a full-stack task management application designed to provide a simple, intuitive solution for tracking and managing tasks, especially tailored for college students. This project showcases my experience in back-end and front-end development, using **Java 17**, **Gradle 7.6**, and **Spring Boot 3.3.1** on the back-end, with a **React** front-end for a responsive and user-friendly interface.

I built Tasker to demonstrate my skills in:
- **REST API development**
- **JWT-based authentication**
- **Database management with PostgreSQL**
- **Cloud deployment using Render**

## Key Features
- **Authentication & Security**: Implemented JWT authentication for secure user login and session management.
- **Task Management**: Users can create, update, delete, and categorize tasks. Each task includes fields such as priority, status, and deadlines.
- **Category Management**: Tasks can be assigned to categories for better organization.
- **Admin Tools**: Administrators can manage users and view system statistics.
- **Responsive & Intuitive UI**: Built with React, the interface is clean, responsive, and easy to navigate.
- **PostgreSQL Integration**: Efficient data storage and retrieval using PostgreSQL.

## Technologies Used

### Back-End (Java)
- **Java 17**: Leveraging modern features of the latest long-term support (LTS) release.
- **Spring Boot 3.3.1**: For rapid application development using RESTful API principles.
- **Gradle 7.6**: Managing dependencies and building the project efficiently.
- **JWT Authentication**: Secure, token-based authentication for user sessions.
- **PostgreSQL**: Reliable, relational database for handling data persistence.
- **Render**: Hosting the back-end for production.

### Front-End (JavaScript/React)
- **React**: Component-based architecture for dynamic user experiences.
- **Axios**: For making efficient API requests and handling responses.
- **CSS**: Custom styles for a sleek and responsive interface.

## Live Demo
You can try out the live version of Tasker here: [Tasker App](https://tasker-app-bzga.onrender.com/).

> **Note**: The app is hosted on Render's free tier, so it may take a few seconds to respond if it has been idle. Please be patient while the app "wakes up."

## Skills Demonstrated
- **Full-Stack Development**: Designed and implemented both front-end and back-end components.
- **REST API Development**: Built secure and scalable RESTful APIs following best practices.
- **JWT Authentication**: Implemented secure token-based authentication.
- **Database Design**: Designed efficient database schemas and handled CRUD operations with PostgreSQL.
- **Responsive Web Design**: Developed a friendly, responsive user interface using React and modern CSS practices.
- **Version Control**: Managed the project using Git with a focus on code organization.
- **Cloud Deployment**: Deployed and managed the application on Render.

## Setup Instructions

### 1. Clone the repository:

```
git clone https://github.com/yourusername/tasker.git
cd tasker

cd tasker
```
### 2. Back-End Setup:

Ensure you have PostgreSQL installed and running.
Open the src/main/resources/application.properties file and update the database connection details:
properties

```
spring.datasource.url=jdbc:postgresql://localhost:5432/taskerdb
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Run the Spring Boot application using Gradle:
```
./gradlew bootRun
```

### 3. Front-End Setup:

Navigate to the front-end directory:

```
cd frontendmaster
```

Install dependencies:

```
npm install
```

Run the front-end application:

```
npm start
```


### 4. Access the app:
   
The app will be available at http://localhost:8000.


### Project Structure

**Backend**    - Spring Boot code (REST API, JWT Authentication, PostgreSQL integration)
**Frontend**   - React components, pages, and styling
**docs**       - Documentation and API details


###Future Enhancements

Task Filtering: Enable task filtering based on parameters such as deadlines or priority.
Real-Time Notifications: Implement user notifications for task deadlines and status changes.


###Contact
For any questions or to discuss this project further, feel free to reach out:

**Name**: Yons Said

**Email**: yonssaid@gmail.com

**LinkedIn**: [Yons Said](https://www.linkedin.com/in/yonssaid/)
