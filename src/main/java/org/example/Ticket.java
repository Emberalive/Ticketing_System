package org.example;

import java.time.LocalDate;

public class Ticket {
    private int ticketID;
    private String issue;
    private int priority;
    private String status;
    private String userName;
    private LocalDate creationDate;
    private String employee;

    public Ticket(int ticketID, String issue, String priority, String status, String userName, LocalDate creationDate) {
        this.ticketID = ticketID;
        this.issue = issue;
        this.priority = Integer.parseInt(priority);
        this.status = status;
        this.userName = userName;
        this.creationDate = creationDate;
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

    //getting and setting the ticket creationDate
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate() {
        this.creationDate = LocalDate.now();
    }
}