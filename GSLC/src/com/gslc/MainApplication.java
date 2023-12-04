package com.gslc;

import com.gslc.connection.CsvConnection;
import com.gslc.facade.CsvFacade;
import com.gslc.menu.Menu;
import com.gslc.models.User;
import com.gslc.models.Team;

import java.util.List;

public class MainApplication {
    public static void main(String[] args) {
        CsvConnection connection = CsvConnection.getInstance();
        String userCsvPath = "D:/Semester 3/Object Oriented Programming/user.csv";
        String teamCsvPath = "D:/Semester 3/Object Oriented Programming/teams.csv";
        
        CsvFacade csvFacade = new CsvFacade(connection, userCsvPath, teamCsvPath);
        Menu menu = new Menu(csvFacade);
        menu.displayMenu();
    }
}
