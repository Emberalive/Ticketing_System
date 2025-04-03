package org.example;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.Account.Account;


import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        Console console = System.console();
        String username;
        String password;

        System.out.println("\nWelcome to the Ticketing system!");


        if (console != null) {
            BCrypt.Result result;
            do {

                do {
                    System.out.println("\nPlease enter your username:");
                    username = console.readLine();

                    System.out.println("\nPlease enter your password:");
                    char[] passwordArray = System.console().readPassword();
                    password = new String(passwordArray);

                    if (username.trim().isEmpty() || password.trim().isEmpty()) {
                        System.out.println("\nEither your username or password is empty!");
                    }
                } while (username.trim().isEmpty() || password.trim().isEmpty()); // Keep asking if empty/null

                result = account.login(username, password);
                if (result == null) {
                    System.out.println("\n!Login failed!");
                } else {
                    System.out.println("\nWelcome: " + username + "to the ticketing system!");
                }
            } while (result == null);

            System.out.println("Thanks for using the Ticketing System!");
        }
    }
}