package com.gslc.repository;

import com.gslc.connection.CsvConnection;
import com.gslc.models.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamRepository implements Repository<Team> {
    private final CsvConnection connection;
    private final String filePath;

    public TeamRepository(CsvConnection connection, String filePath) {
        this.connection = connection;
        this.filePath = filePath;
    }
    
    public List<Team> getAllTeams() {
        return find(null, null);
    }

    @Override
    public List<Team> find(String column, String[] filterConditions) {
        List<String[]> csvData = connection.readCsvFile(filePath);
        List<Team> teams = new ArrayList<>();

        int startIndex = csvData.get(0)[0].matches("\\d+") ? 0 : 1;

        for (int i = startIndex; i < csvData.size(); i++) {
            String[] rowData = csvData.get(i);
            Team team = new Team(Integer.parseInt(rowData[0]), rowData[1]);
            if (column == null && filterConditions == null || matchesCondition(team, column, filterConditions)) {
                teams.add(team);
            }
        }

        return teams;
    }

    @Override
    public Team findOne(String column, String[] filterConditions) {
        List<Team> teams = find(column, filterConditions);
        return teams.isEmpty() ? null : teams.get(0);
    }

    private boolean matchesCondition(Team team, String column, String[] filterConditions) {
        if (column == null || filterConditions == null) {
            return true;
        }
        String conditionValue = filterConditions[1].trim();

        switch (column) {
            case "id":
                try {
                    int id = Integer.parseInt(conditionValue);
                    return team.getId() == id;
                } catch (NumberFormatException e) {
                    return false;
                }
            case "Team":
                return team.getName().equalsIgnoreCase(conditionValue);
            default:
                return false;
        }
    }
    
    public Team findByName(String teamName) {
        return find("Team", new String[]{"=", teamName}).stream().findFirst().orElse(null);
    }
    
    public int findMaxTeamId() {
        List<Team> teams = getAllTeams();
        return teams.stream()
                .mapToInt(Team::getId)
                .max()
                .orElse(0);
    }
    
    @Override
    public void insert(List<Team> teams) {
        List<String[]> csvData = connection.readCsvFile(filePath);
        int lastId = findMaxTeamId();

        for (Team team : teams) {
            lastId++;
            team.setId(lastId);
            csvData.add(new String[]{String.valueOf(team.getId()), team.getName()});
        }

        connection.writeCsvFile(filePath, csvData);
    }

}
