package com.codecool.colors.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class GameState {
    private List<Card> canvas;
    private List<Player> players;

    public GameState(){}

    @Autowired
    public GameState(List<Card> canvas, List<Player> players) {
        this.canvas = canvas;
        this.players = players;
    }

    public List<Card> getCanvas() {
        return canvas;
    }

    public void setCanvas(List<Card> canvas) {
        this.canvas = canvas;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @JsonIgnore
    public String getRuleColor() {
        return canvas.get(canvas.size() - 1).getColor();
    }

    @JsonIgnore
    @Override
    public String toString() {
        return "GameState{" +
                "canvas=" + canvas +
                ", players=" + players +
                '}';
    }
}
