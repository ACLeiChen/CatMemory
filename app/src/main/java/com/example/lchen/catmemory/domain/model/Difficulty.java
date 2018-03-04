package com.example.lchen.catmemory.domain.model;

/**
 * Created by Lei Chen on 2018/2/25.
 */

public class Difficulty {

    public static final String DIFFICULTY_EASY = "EASY";
    public static final String DIFFICULTY_MIDDLE = "MIDDLE";
    public static final String DIFFICULTY_HARD = "HARD";

    private int numberOfCards;
    private String name;

    public Difficulty(String name) {
        this.name = name;
        setNumberOfCards();
    }

    private void setNumberOfCards() {
        if(name.equals(DIFFICULTY_EASY)) numberOfCards = 4;
        if(name.equals(DIFFICULTY_MIDDLE)) numberOfCards = 8;
        if(name.equals(DIFFICULTY_HARD)) numberOfCards = 12;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
