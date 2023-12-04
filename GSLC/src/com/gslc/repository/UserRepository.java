package com.gslc.repository;

import com.gslc.connection.CsvConnection;
import com.gslc.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository implements Repository<User> {
    private final CsvConnection connection;
    private final String filePath;

    public UserRepository(CsvConnection connection, String filePath) {
        this.connection = connection;
        this.filePath = filePath;
    }

    @Override
    public List<User> find(String column, String[] filterConditions) {
        List<String[]> csvData = connection.readCsvFile(filePath);
        List<User> users = new ArrayList<>();

        if (column == null && filterConditions == null) {
            for (String[] rowData : csvData) {
                if(rowData.length >= 3) {
                    User user = new User(rowData[0], rowData[1], Integer.parseInt(rowData[2]));
                    users.add(user);
                }
            }
        } else {
            for (String[] rowData : csvData) {
                User user = new User(rowData[0], rowData[1], Integer.parseInt(rowData[2]));
                if (matchesCondition(user, column, filterConditions)) {
                    users.add(user);
                }
            }
        }

        return users;
    }
    
    @Override
    public User findOne(String column, String[] filterConditions) {
        List<User> users = find(column, filterConditions);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void insert(List<User> users) {
        List<String[]> csvData = connection.readCsvFile(filePath);
        for (User user : users) {
            csvData.add(new String[]{user.getNim(), user.getName(), String.valueOf(user.getTeamId())});
        }
        connection.writeCsvFile(filePath, csvData);
    }

    
    private boolean matchesCondition(User user, String column, String[] filterConditions) {
        if (column == null || filterConditions == null) {
            return true;
        }
        
        String conditionValue = filterConditions[1].trim();

        switch (column) {
            case "NIM":
                return user.getNim().equals(conditionValue);
            case "Name":
                return user.getName().toLowerCase().contains(conditionValue.toLowerCase());
            case "ID Team":
                try {
                    int teamIdCondition = Integer.parseInt(conditionValue);
                    return user.getTeamId() == teamIdCondition;
                } catch (NumberFormatException e) {
                    return false;
                }
            default:
                return false;
        }
    }
    
    public List<User> findByTeamId(int teamId) {
        return find("ID Team", new String[]{"=", String.valueOf(teamId)});
    }
}
