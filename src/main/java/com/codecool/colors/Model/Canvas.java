package com.codecool.colors.Model;

import java.util.List;

public class Canvas {
    private List<Card> canvas;

    public Canvas() {
    }

    public Canvas(List<Card> canvas) {
        this.canvas = canvas;
    }

    public List<Card> getCanvas() {
        return canvas;
    }

    public void setCanvas(List<Card> canvas) {
        this.canvas = canvas;
    }
}
