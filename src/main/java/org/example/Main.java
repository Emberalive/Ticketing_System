package org.example;

import org.example.Account.IT_employee;
import org.example.Account.user;

public class Main {

    public static void main(String[] args) {

        user customer = new user();

        customer.setUsername("user");
        System.out.println("\ncreating a user account");
        customer.setPassword("password");


        IT_employee employee = new IT_employee();
        employee.setUsername("admin");
        employee.setPassword("password");
    }
}
