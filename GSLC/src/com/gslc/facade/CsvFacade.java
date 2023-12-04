package com.gslc.facade;

import com.gslc.connection.CsvConnection;
import com.gslc.models.User;
import com.gslc.models.Team;
import com.gslc.repository.UserRepository;
import com.gslc.repository.TeamRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CsvFacade {
    private UserRepository userRepository;
    private TeamRepository teamRepository;

    public CsvFacade(CsvConnection connection, String userCsvPath, String teamCsvPath) {
        this.userRepository = new UserRepository(connection, userCsvPath);
        this.teamRepository = new TeamRepository(connection, teamCsvPath);
    }

    public List<User> getAllUsers() {
        return userRepository.find(null, null);
    }

    public List<Team> getAllTeams() {
        return teamRepository.find(null, null);
    }
    
    public List<User> getUsersByCondition(String field, String[] condition) {
        return userRepository.find(field, condition);
    }

    public List<Team> getTeamsByCondition(String field, String[] condition) {
        return teamRepository.find(field, condition);
    }
    
    public User getUserById(String id) {
        return userRepository.findOne("NIM", new String[]{"=", id});
    }

    public Team getTeamById(String id) {
        return teamRepository.findOne("id", new String[]{"=", id});
    }
    
    public Team getTeamByName(String teamName) {
        return teamRepository.findByName(teamName);
    }
    
    public List<User> getUsersByTeamId(int teamId) {
        return userRepository.findByTeamId(teamId);
    }

    public void addUser(String name, String nim, String teamName) {
        Team team = getTeamByName(teamName);
        if (team == null) {
            System.out.println("Error: No such team with name " + teamName);
            return;
        }

        List<User> usersInTeam = getUsersByTeamId(team.getId());
        if (usersInTeam.size() >= 3) {
            System.out.println("Error: Team is full.");
            return;
        }

        User newUser = new User(nim, name, team.getId());
        userRepository.insert(List.of(newUser));
    }



    public void addTeam(String teamName) {
        Team existingTeam = getTeamByName(teamName);
        if (existingTeam != null) {
            System.out.println("Error: Team with this name already exists.");
            return;
        }

        int newId = teamRepository.findMaxTeamId() + 1;
        Team newTeam = new Team(newId, teamName); 
        teamRepository.insert(List.of(newTeam));
        System.out.println("Team created!");
        System.out.println("Team: " + newTeam.getName());
        System.out.println("ID: " + newTeam.getId());
    }

}