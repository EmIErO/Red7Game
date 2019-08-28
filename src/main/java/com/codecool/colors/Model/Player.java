package com.codecool.colors.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> palette;

    public Player() {
    }

    public Player(String name, List<Card> palette) {
        this.name = name;
        Collections.sort(palette);
        this.palette = palette;
    }

    public Player(Player that) {
        List<Card> copyOfPallete = that.getPalette().stream()
                .map(Card::new)
                .collect(Collectors.toList());
        this.name = that.getName();
        this.palette = copyOfPallete;
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
        Collections.sort(palette);
        this.palette = palette;
    }

    @JsonIgnore
    public int getPalleteSize() {
        return palette.size();
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", palette=" + palette +
                '}';
    }
}
