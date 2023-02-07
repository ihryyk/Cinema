<h1 align="center">Cinema</h1>

## Description

The system is an Internet showcase of a cinema with one hall. The system has a schedule of films for the week from 
9:00 to 22:00 (starts of the last film) hours.
- **An unregistered user** can see the schedule, free seats in the hall and has the opportunity to register.
- **The user** should be able to sort the schedule entries to find movies by title, search session for movie, 
sort the schedule entries by number of available seats, session date, and filter schedule by the movies available for viewing today.
- **The registered user** must be able to purchase a ticket for the selected session.
- **The administrator** can schedule a new movie, cancel ,movie, update information about movie and session, 
view the attendance of the hall.

## About the project


- **Database:** PostgreSQL
- **Tools:** Maven, IntelliJ, pgAdmin 4, Apache Tomcat
- **Technologies:** Java Servlet API, HTML, CSS, JDBC, Junit, Mockito, JSP, Log4j, Lombok 

- To access the data, used postgres connection pool.
- Provided the application with support to work with the Cyrillic alphabet (be multilingual), including when storing information in the database: it is possible to switch the interface language;
- There is support for input, output and storage of information (in the database), recorded in different languages;
- Chose at least two languages: one based on Cyrillic (Ukrainian), another based on Latin (English).
- The application architecture conformed to the MVC template.
- When implementing business logic, used design patterns:Factory, Singleton, Front Controller, etc.
- Using servlets and JSP, implemented the functionality -described in the functional requirements.
- Used Apache Tomcat as a servlet container.
- Used JSTL library tags and custom tags (minimum: one) on JSP pages custom tag library tag and one tag file tag).
- Implemented protection against re-sending data to the server when refreshing the page (implement PRG).
- Used sessions, filters, listeners when developing.
- Authentication and authorization, delimitation of access rights of system users to program components implemented in the application. Password encryption is done.
- Introduced an event log into the project using the log4j library.
- The code contains comments on the documentation (all top-level classes, non-trivial methods and designers).
- The application is covered by modular tests (minimum coverage percentage of 40%).
- Implemented the mechanism of pagination of data pages.
- All input fields use data validation.
- The application respond correctly to errors and exceptions of various kinds (final user should not see the stack trace on the client side).
- Used HTML, CSS.
