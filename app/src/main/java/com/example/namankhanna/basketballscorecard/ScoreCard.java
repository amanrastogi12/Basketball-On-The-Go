package com.example.namankhanna.basketballscorecard;

import java.io.Serializable;

public class ScoreCard implements Serializable {
    String team1, team2;
    int totalScore1, totalScore2, fouls1, fouls2, timeOuts1, timeOuts2;

    public ScoreCard(String team1, String team2, int totalScore1, int totalScore2, int fouls1, int fouls2, int timeOuts1, int timeOuts2) {
        this.team1 = team1;
        this.team2 = team2;
        this.totalScore1 = totalScore1;
        this.totalScore2 = totalScore2;
        this.fouls1 = fouls1;
        this.fouls2 = fouls2;
        this.timeOuts1 = timeOuts1;
        this.timeOuts2 = timeOuts2;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public int getTotalScore1() {
        return totalScore1;
    }

    public int getTotalScore2() {
        return totalScore2;
    }

    public int getFouls1() {
        return fouls1;
    }

    public int getFouls2() {
        return fouls2;
    }

    public int getTimeOuts1() {
        return timeOuts1;
    }

    public int getTimeOuts2() {
        return timeOuts2;
    }
}
