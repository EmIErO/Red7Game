package com.codecool.colors.Model;

import java.util.HashMap;
import java.util.Map;

public class Card implements Comparable<Card>{
    private short number;
    private String color;

    public static Map<String, Integer> colorMap = new HashMap<>();

    static {
        colorMap.put("red",7);
        colorMap.put("orange",6);
        colorMap.put("yellow",5);
        colorMap.put("green",4);
        colorMap.put("blue",3);
        colorMap.put("indigo",2);
        colorMap.put("violet",1);
    }

    public Card() {}

    public Card(Short number, String color) {
        this.number = number;
        this.color = color;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public int compareTo(Card card) {

        if(this.number==card.getNumber()) {
            return colorMap.get(this.color)-colorMap.get(card.getColor());
        }

        return this.number-card.getNumber();
    }


    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", color='" + color + '\'' +
                '}';
    }



}
