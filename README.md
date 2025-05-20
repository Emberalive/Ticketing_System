
# Ticketing System

A basic Java-based Ticketing System application, built with Maven. This project is designed to manage and track support tickets, and can be extended to suit help desk or customer service workflows.

## Login's
**There is already several accounts that have been set up**

- username: user, Pswd: pass Role: user
#### Employees

- Username: Olivia, pswd: password
- Username: Lucas, pswd: password
- Username: Mia, pswd: password
- Username: Sophia, pswd: password
- Username: Ethan, pswd: password

```
These users and passwords are already set in the database, feel free to use these to log in.
you are able to register a user, you can register a user however not an employee, as realistically a user or
an employee should not be able to set their own password an dusername, just though i would show
how i wwould set it up for this kind of application. 
```

## Features

- Ticket creation and management
- Console-based or GUI (depending on implementation)
- Modular Maven project structure
- Java 1.8 compatibility

## Project Structure

```
Ticketing_System
├── LICENSE
├── logs
│   └── application.log
├── pom.xml
├── README.md
├── src
│   └── main
│       ├── java
│       │   └── org
│       │       └── Emberalive
│       │           ├── account
│       │           │   ├── IT_employee.java
│       │           │   └── User.java
│       │           ├── dataStructures
│       │           │   ├── bucket
│       │           │   │   ├── Bucket_Queue.java
│       │           │   │   ├── readMe.md
│       │           │   │   └── Simple_Queue.java
│       │           │   ├── priority_queue
│       │           │   │   └── Priority_Queue_List.java
│       │           │   └── sortedList
│       │           │       └── Sorted_List.java
│       │           ├── db_access
│       │           │   └── Db_Access.java
│       │           ├── Employee
│       │           │   ├── EmployeeController.java
│       │           │   ├── EmployeeModel.java
│       │           │   └── EmployeeView.java
│       │           ├── login
│       │           │   ├── LoginController.java
│       │           │   ├── LoginModel.java
│       │           │   └── LoginView.java
│       │           ├── Main.java
│       │           ├── register
│       │           │   ├── RegisterController.java
│       │           │   ├── RegisterModel.java
│       │           │   └── RegisterView.java
│       │           ├── ticket
│       │           │   └── Ticket.java
│       │           ├── user
│       │           │   ├── accountDetailsView
│       │           │   │   ├── AccountModel.java
│       │           │   │   └── AccountView.java
│       │           │   ├── DeleteTicket
│       │           │   │   ├── DeleteTicketModel.java
│       │           │   │   └── DeleteTicketView.java
│       │           │   ├── search
│       │           │   │   ├── userSearchTicket.java
│       │           │   │   └── userSearchTicketModel.java
│       │           │   ├── UserController.java
│       │           │   └── UserView.java
│       │           └── Utils
│       │               └── Styling.java
│       └── resources
│           ├── database.sql
│           └── log4j2.xml
├── target
│   ├── classes
│   ├── generated-sources
│   └── Ticket_System.iml
└── Ticket_System.iml


```

## Getting Started

### Prerequisites

- Java 17 or above
- Maven 3.6+
- IntelliJ IDEA (optional but recommended)
- postgresql-42.7.5.jar as a library, the library is stored in the db_access package

### Build the Project

```bash
mvn clean install
```

### Run the Application

Update the `mainClass` in `pom.xml` under `exec-maven-plugin` to match your actual main class (currently set to `your.package.MyApp`), then:

```bash
mvn exec:java
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/YourFeature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin feature/YourFeature`)
5. Create a new Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
