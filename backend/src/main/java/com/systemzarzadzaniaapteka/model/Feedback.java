package com.systemzarzadzaniaapteka.model;

/**
 * Klasa reprezentująca opinię zwrotną w systemie zarządzania apteką.
 * 
 * <p>Klasa Feedback przechowuje informacje o opinii zwrotnej od klienta,
 * w tym komentarze oraz ocenę.</p>
 * 
 * @author System Zarządzania Apteką
 * @version 1.0
 * @since 1.0
 */
public class Feedback {
    private int id;
    private String comments;
    private int rating;

    public Feedback(int id, String comments, int rating) {
        this.id = id;
        this.comments = comments;
        this.rating = rating;
    }

    public void submitFeedback() {
        System.out.println("Feedback submitted: " + comments);
    }
}
