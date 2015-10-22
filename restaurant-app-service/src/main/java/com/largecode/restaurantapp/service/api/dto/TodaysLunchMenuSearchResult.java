package com.largecode.restaurantapp.service.api.dto;

import java.util.List;


public class TodaysLunchMenuSearchResult {
    
    private List<LunchMenu> lunchMenus;

    public List<LunchMenu> getLunchMenus() {
        return lunchMenus;
    }

    public void setLunchMenus(List<LunchMenu> lunchMenus) {
        this.lunchMenus = lunchMenus;
    }
}
