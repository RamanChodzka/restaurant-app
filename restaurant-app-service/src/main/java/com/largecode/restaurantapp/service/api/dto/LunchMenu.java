package com.largecode.restaurantapp.service.api.dto;

import java.util.List;


public class LunchMenu {
    
    private Restaurant restaurant;
    private List<LunchMenuItem> lunchMenuItems;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    
    public List<LunchMenuItem> getLunchMenuItems() {
        return lunchMenuItems;
    }

    public void setLunchMenuItems(List<LunchMenuItem> lunchMenuItems) {
        this.lunchMenuItems = lunchMenuItems;
    }
}
