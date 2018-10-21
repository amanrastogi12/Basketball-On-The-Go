package com.example.namankhanna.basketballscorecard;

public class Player {
    String name;
    int TNum;

    public Player(String name, int tNum) {
        this.name = name;
        this.TNum = tNum;
    }

    public String getName() {
        return name;
    }

    public int getTNum() {
        return TNum;
    }
}
