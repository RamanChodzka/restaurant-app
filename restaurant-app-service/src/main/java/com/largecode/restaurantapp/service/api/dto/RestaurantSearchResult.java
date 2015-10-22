package com.largecode.restaurantapp.service.api.dto;

import java.util.List;


public class RestaurantSearchResult {

    private List<Restaurant> restaurants;

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
