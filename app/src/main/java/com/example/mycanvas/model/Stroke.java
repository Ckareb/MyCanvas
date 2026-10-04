package com.example.mycanvas.model;

import android.graphics.Path;

public class Stroke {
    private Path path;
    private int color;
    private float width;

    public Stroke(Path path, int color, float width) {
        this.path = path;
        this.color = color;
        this.width = width;
    }

    public Path getPath() {
        return path;
    }

    public int getColor() {
        return color;
    }

    public float getWidth() {
        return width;
    }
}
