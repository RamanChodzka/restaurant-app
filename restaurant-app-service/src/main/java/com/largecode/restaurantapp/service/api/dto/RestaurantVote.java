package com.largecode.restaurantapp.service.api.dto;


public class RestaurantVote {
    
    private Long restaurantId;
    private Long userId;

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
