package com.gslc.menu;

import com.gslc.facade.CsvFacade;
import com.gslc.models.User;
import com.gslc.models.Team;

import java.util.Scanner;
import java.util.List;

public class Menu {
    private CsvFacade csvFacade;
    private Scanner scanner;

    public Menu(CsvFacade csvFacade) {
        this.csvFacade = csvFacade;
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("1. Menu Utama");
            System.out.println("2. Insert Data");
            System.out.println("3. Show");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    continue;
                case 2:
                    insertData();
                    break;
                case 3:
                    showData();
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private void insertData() {
        System.out.println("Which table to insert? 1. User, 2. Team");
        int tableChoice = scanner.nextInt();
        scanner.nextLine();

        if (tableChoice == 1) {
            System.out.print("Add name: ");
            String name = scanner.nextLine();
            System.out.print("Add nim: ");
            String nim = scanner.nextLine();
            System.out.print("Add team name: ");
            String teamName = scanner.nextLine();

            csvFacade.addUser(name, nim, teamName);
        } else if (tableChoice == 2) {
            System.out.print("Add team name: ");
            String teamName = scanner.nextLine();

            csvFacade.addTeam(teamName);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void showData() {
        System.out.println("Which table to show? 1. User, 2. Team");
        int tableChoice = scanner.nextInt();
        scanner.nextLine();

        if (tableChoice == 1) {
            showUsers();
        } else if (tableChoice == 2) {
            showTeams();
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void showUsers() {
        System.out.println("Want to filter by condition? 1. Yes, 2. No");
        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        if (filterChoice == 1) {
            System.out.println("Add condition, separate by semicolon (e.g., Name;=;Kevin):");
            String[] parts = scanner.nextLine().split(";");
            if (parts.length == 3) {
                String field = parts[0].trim();
                String condition = parts[1].trim();
                String value = parts[2].trim();
                List<User> users = csvFacade.getUsersByCondition(field, new String[]{condition, value});
                if (users.isEmpty()) {
                    System.out.println("No users found with the condition provided.");
                } else {
                    for (User user : users) {
                        System.out.println("Name = " + user.getName());
                        System.out.println("NIM = " + user.getNim());
                        System.out.println("ID Team = " + user.getTeamId());
                        System.out.println();
                    }
                }
            } else {
                System.out.println("Invalid condition format.");
            }
        } else {
            List<User> users = csvFacade.getAllUsers();
            for (User user : users) {
                System.out.println("Name = " + user.getName());
                System.out.println("NIM = " + user.getNim());
                System.out.println("ID Team = " + user.getTeamId());
                System.out.println();
            }
        }
    }

    private void showTeams() {
        System.out.println("Want to filter by condition? 1. Yes, 2. No");
        int filterChoice = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        if (filterChoice == 1) {
            System.out.println("Add condition, separate by semicolon (e.g., Team;=;Venti):");
            String[] parts = scanner.nextLine().split(";");
            if (parts.length == 3) {
                String field = parts[0].trim();
                String condition = parts[1].trim();
                String value = parts[2].trim();
                List<Team> teams = csvFacade.getTeamsByCondition(field, new String[]{condition, value});
                if (teams.isEmpty()) {
                    System.out.println("No teams found with the condition provided.");
                } else {
                    for (Team team : teams) {
                        System.out.println("Team = " + team.getName());
                        System.out.println("ID = " + team.getId());
                        System.out.println();
                    }
                }
            } else {
                System.out.println("Invalid condition format.");
            }
        } else {
            List<Team> teams = csvFacade.getAllTeams();
            for (Team team : teams) {
                System.out.println("Team = " + team.getName());
                System.out.println("ID = " + team.getId());
                System.out.println();
            }
        }
    }
}