package com.example.namankhanna.basketballscorecard;

import java.io.Serializable;

public class Player implements Serializable {
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
