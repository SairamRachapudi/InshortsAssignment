package com.sairam.inshortsapp.events;

/**
 * Created by sairamrachapudi on 10/09/17.
 */

public class CategorySelectedEvent {
    String category;

    public CategorySelectedEvent(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
