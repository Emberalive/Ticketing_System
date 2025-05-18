
# Ticketing System

A basic Java-based Ticketing System application, built with Maven. This project is designed to manage and track support tickets, and can be extended to suit help desk or customer service workflows.

## Features

- Ticket creation and management
- Console-based or GUI (depending on implementation)
- Modular Maven project structure
- Java 1.8 compatibility

## Project Structure

```bash
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
│       │           │   ├── UserModel.java
│       │           │   └── UserView.java
│       │           └── Utils
│       │               └── Styling.java
│       └── resources
│           ├── database.sql
│           └── log4j2.xml
├── target
│   ├── classes
│   │   ├── database.sql
│   │   ├── log4j2.xml
│   │   ├── logs
│   │   │   └── application.log
│   │   ├── org
│   │   │   └── Emberalive
│   │   │       ├── account
│   │   │       │   ├── IT_employee.class
│   │   │       │   └── User.class
│   │   │       ├── dataStructures
│   │   │       │   ├── bucket
│   │   │       │   │   ├── Bucket_Queue.class
│   │   │       │   │   └── Simple_Queue.class
│   │   │       │   ├── priority_queue
│   │   │       │   │   └── Priority_Queue_List.class
│   │   │       │   └── sortedList
│   │   │       │       └── Sorted_List.class
│   │   │       ├── db_access
│   │   │       │   └── Db_Access.class
│   │   │       ├── Employee
│   │   │       │   ├── EmployeeController.class
│   │   │       │   ├── EmployeeModel.class
│   │   │       │   ├── EmployeeView$1.class
│   │   │       │   └── EmployeeView.class
│   │   │       ├── login
│   │   │       │   ├── LoginController.class
│   │   │       │   ├── LoginModel.class
│   │   │       │   └── LoginView.class
│   │   │       ├── Main.class
│   │   │       ├── register
│   │   │       │   ├── RegisterController.class
│   │   │       │   ├── RegisterModel.class
│   │   │       │   └── RegisterView.class
│   │   │       ├── ticket
│   │   │       │   └── Ticket.class
│   │   │       ├── user
│   │   │       │   ├── accountDetailsView
│   │   │       │   │   ├── AccountModel.class
│   │   │       │   │   └── AccountView.class
│   │   │       │   ├── DeleteTicket
│   │   │       │   │   ├── DeleteTicketModel.class
│   │   │       │   │   └── DeleteTicketView.class
│   │   │       │   ├── search
│   │   │       │   │   ├── userSearchTicket.class
│   │   │       │   │   └── userSearchTicketModel.class
│   │   │       │   ├── UserController.class
│   │   │       │   ├── UserModel.class
│   │   │       │   ├── UserView$1.class
│   │   │       │   └── UserView.class
│   │   │       └── Utils
│   │   │           └── Styling.class
│   │   ├── pom.xml
│   │   ├── src
│   │   │   └── main
│   │   │       ├── java
│   │   │       │   └── org
│   │   │       │       └── Emberalive
│   │   │       │           └── dataStructures
│   │   │       │               └── bucket
│   │   │       │                   └── readMe.md
│   │   │       └── resources
│   │   │           ├── database.sql
│   │   │           └── log4j2.xml
│   │   └── Ticket_System.iml
│   └── generated-sources
│       └── annotations
└── Ticket_System.iml

```

## Getting Started

### Prerequisites

- Java JDK 1.8 or higher
- Maven 3.6+
- IntelliJ IDEA (optional but recommended)

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
