package org.example;

import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.db_access.Db_Access;

import java.time.LocalDate;

public class Ticket {
    Db_Access db = new Db_Access();
    private int ticketID;
    private String issue;
    private int priority;
    private String status;
    private String userName;
    private final LocalDate creationDate;
    private String employee;

    public Ticket(String issue, int priority, String status, String userName, String employee, Bucket_Queue dataStruct, int ticketID, LocalDate creationDate) {
        boolean ticketExist = db.getTickets(this);
        this.issue = issue;
        this.priority = priority;
        this.status = status;
        this.userName = userName;
        this.employee = employee;


        if (!ticketExist) {
            this.creationDate = LocalDate.now();
        } else {
            this.creationDate = creationDate;
        }

        if (!ticketExist) {
            db.insertTicket(this);
            dataStruct.setCounterIntial();
            this.ticketID = dataStruct.getCounter() + 1;
            dataStruct.setCounter(ticketID);
        } else {
            this.ticketID = ticketID;
        }
    }

    //created setters and getters for employee
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    public String getEmployee() {
        return employee;
    }

    //setting and getting the issue
    public String getIssue() {
        return issue;
    }
    public void setIssue(String issue) {
        this.issue = issue;
    }

    //setting and getting the ticketID
    public int getTicketID() {
        return ticketID;
    }
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    //getting and setting the ticket priority
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    //getting and setting the ticket priority
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    //getting and setting the tickets user
    public String getUserID() {
        return userName;
    }
    public void setUserID(String userID) {
        this.userName = userID;
    }

    //creating the getter the ticket creationDate
    public LocalDate getCreationDate() {
        return creationDate;
    }

    // allows me to print the ticket in the logs
    public String printTicket() {
        //concatenating all the values in one string
        return "Ticket{ (ID: " + ticketID + ") (Date: " + creationDate + ") (Issue: " + issue + ") (Username: " + userName + ") (Status: " + status + ") (Handled By: " + employee + ")}";
    }
}