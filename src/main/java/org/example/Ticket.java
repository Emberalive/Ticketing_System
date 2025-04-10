package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dataStructures.bucket.Bucket_Queue;
import org.example.db_access.Db_Access;

import java.time.LocalDate;

public class Ticket {
    private static final Logger logger = LogManager.getLogger(Ticket.class);

    Db_Access db = new Db_Access();
    private int ticketID;
    private String issue;
    private int priority;
    private String status = "InActive";
    private String userName;
    private final LocalDate creationDate;
    private String employee;

    // Constructor for creating new tickets (no ID or creationDate passed)
    public Ticket(String issue, int priority, String userName, String employee, Bucket_Queue dataStruct) {
        this.issue = issue;
        this.priority = priority;
        this.userName = userName;
        this.employee = employee;
        this.creationDate = LocalDate.now();

        // Insert into DB and update ticketID
        dataStruct.setCounterIntial(); // syncs counter with DB
        this.ticketID = dataStruct.getCounter() + 1;
        dataStruct.setCounter(ticketID); // updates counter in memory
        db.insertTicket(this);
        logger.info("Inserted ticket with ID: {}", this.ticketID);
    }

    // Constructor for existing tickets loaded from DB
    public Ticket(String issue, int priority, String status, String userName, String employee, int ticketID, LocalDate creationDate) {
        this.issue = issue;
        this.priority = priority;
        this.status = status;
        this.userName = userName;
        this.employee = employee;
        this.ticketID = ticketID;
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
        logger.info("Updating status from '{}' - '{}' For the Ticket: {}", this.getStatus(), status, this.loggTicket());
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
    public String loggTicket() {
        //concatenating all the values in one string
        return "Ticket{ (ID: '" + ticketID + "') (Date: '" + creationDate + "') (Issue: '" + issue + "') (Username: '" + userName + "') (Status: '" + status + "') (Handled By: '" + employee + "')}";
    }

    public String printTicket() {
        return "Ticket Details:\n"
                + "--------------------------------------------------------------------------\n"
                + "Ticket ID: " + ticketID + "\n"
                + "Issue: " + issue + "\n"
                + "Priority: " + priority + "\n"
                + "Status: " + status + "\n"
                + "User: " + userName + "\n"
                + "Handled By: " + employee + "\n"
                + "Creation Date: " + creationDate + "\n";
    }
}