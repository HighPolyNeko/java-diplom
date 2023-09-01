package ru.netology.graphics.image;

public class TextColor implements TextColorSchema {
    protected char[] colors = new char[]{'\\', '-', '+', '*', '%', '@', '$', '#'};
    @Override
    public char convert(int color) {
        return colors[color / (256 / colors.length)];
    }
}
