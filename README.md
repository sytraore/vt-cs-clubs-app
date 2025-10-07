## VT CS Clubs - Unified Event Platform
A full-stack web application designed to be the central hub for all computer science club events at Virginia Tech. <br>
This platform enables students in discovering events that align with their technical interests and career goals, while simplifying the process of event promotion and management for club leaders.

### ✨ Key Features
This project is being built with two primary users in mind: students looking for opportunities and club leaders organizing them.

#### For Students

- Unified Event Dashboard: A clean, modern interface showing all upcoming events from every major CS club.

- Smart Tagging & Filtering: A powerful filtering system allowing students to find events by specific technologies (Java, React, AWS), fields (AI/ML, Cybersecurity), or event types (Workshop, Info Session).

- Company/Sponsor Profiles: Links from sponsored events to dedicated pages with information about internship/new grad roles and application deadlines.

- Simple RSVP System: A straightforward way for students to register their attendance for an event.

- Calendar Integration: A one-click option to add an event to a user's Google Calendar or iCal.

#### For Club Admins

- Secure Admin Portal: A protected, login-only interface for authenticated club leaders.

- Event Management: A full suite of CRUD (Create, Read, Update, Delete) operations for admins to manage their own club's events.

- RSVP Management: The ability to view the list of students who have RSVP'd for an upcoming event.

### 🛠️ Tech Stack
This project is built using a modern tech stack, focusing on scalability, and maintainability. 

#### **1. Backend**
- **Language:** Java
- **Framework:** Spring Boot, Spring Security
- **ORM:** Spring Data JPA
- **Database:** PostgreSQL

#### **2. Frontend**
- **Language:** JavaScript
- **Library:** React
- **UI Framework:** Material UI

#### **3. Tools & DevOps**
- **Version Control:** Git
- **Containerization:** Docker
- **Cloud Provider:** Amazon Web Services (AWS)
- **Testing:** JUnit, Mockito, Apache JMeter (Performance), Spring Boot Test (Integration)


### ⚙️ CI/CD Pipeline
This project utilizes a Continuous Integration (CI) pipeline configured with GitHub Actions.

On every push or pull request to the **main** branch, the pipeline automatically triggers.

It builds the Spring Boot application and runs the full suite of unit and integration tests using Maven.

This ensures that all new code is verified for correctness and quality before being merged.

### ⏳ Phase of development
- Backend Satus: **📌in progress**
- Frontend Status: **❌not started**
- Deployment to AWS: **‼️node done yet**


