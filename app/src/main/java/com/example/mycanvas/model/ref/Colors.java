package com.example.mycanvas.model.ref;

import android.graphics.Color;

public enum Colors {
    BLACK("Черный", Color.BLACK),
    GREEN("Зеленый", Color.GREEN),
    BLUE("Синий", Color.BLUE),
    RED("Красный", Color.RED),
    YELLOW("Желтый", Color.YELLOW),
    WHITE("Белый", Color.WHITE);

    private final String title;

    private final int color;

    Colors(String title, int color) {
        this.title = title;
        this.color = color;
    }


    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }
}
