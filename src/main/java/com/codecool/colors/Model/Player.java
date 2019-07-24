package com.codecool.colors.Model;

import com.codecool.colors.Factory.FIFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> palette;

    public Player() {
    }

    public Player(String name, List<Card> palette) {
        this.name = name;
        this.palette = palette;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getPalette() {
        return palette;
    }

    public void setPalette(List<Card> palette) {
        this.palette = palette;
    }

    @JsonIgnore
    public int getPalleteSize() {
        return palette.size();
    }

    @JsonIgnore
    public Card getHighest() {
        Collections.sort(palette);

        return palette.get(palette.size()-1);
    }

    public Map<String, List<Card>> getAsMap() {
        Map<String, List<Card>> map = new HashMap<>();
        map.put(name, palette);
        return map;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", palette=" + palette +
                '}';
    }
}
