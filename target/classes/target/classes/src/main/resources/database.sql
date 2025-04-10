CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(512),
    role VARCHAR(30)
);

CREATE TABLE ticket (
    ID int NOT NULL,
    issue VARCHAR(300) NOT NULL,
    priority int NOT NULL,
    status VARCHAR(10) NOT NULL,
    username VARCHAR(50)NOT NULL,
    date date NOT NULL,
    employee VARCHAR(50) NOT NULL,
    PRIMARY KEY(ID, username),
    FOREIGN KEY (username) REFERENCES users(username)
);