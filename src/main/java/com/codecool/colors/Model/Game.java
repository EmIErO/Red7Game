package com.codecool.colors.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class Game {
    private List<Card> canvas;
    private List<Player> players;
    private Red7Rules rules;


    public Game(){}

    public Game(List<Card> canvas, List<Player> players) {
        this.canvas = canvas;
        this.players = players;
    }

    @JsonIgnore
    public Red7Rules getRules() {
        return rules;
    }

    public void setRules(Red7Rules rules) {
        this.rules = rules;
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

    public String getRule(String color) {
        try {
            Method rule = Red7Rules.r7rules.get(color);
            return (String) rule.invoke(rules, players);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "An error ocuured!";
    }

    public String getRuleColor() {
        return canvas.get(canvas.size() - 1).getColor();
    }

    @Override
    public String toString() {
        return "Game{" +
                "canvas=" + canvas +
                ", players=" + players +
                ", rules=" + rules +
                '}';
    }
}
