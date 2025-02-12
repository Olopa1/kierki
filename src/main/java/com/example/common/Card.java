package com.example.common;

import java.io.Serializable;

/**
 * Stores data about card.
 */
public class Card implements Serializable {
    private int value;
    private Colors color;

    public Card(int value, Colors color) {
        this.value = value;
        this.color = color;

    }

    public int getValue() {
        return value;
    }

    public Colors getColor() {
        return color;
    }

    public void displayCard() {
        System.out.println(this.color + ":" + this.value + " ");
    }
}
