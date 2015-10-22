package com.largecode.restaurantapp.rest.controller;

import com.largecode.restaurantapp.service.api.RestaurantService;
import com.largecode.restaurantapp.service.api.dto.TodaysLunchMenuSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/lunch-menus")
public class LunchMenuController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @RequestMapping(path = "/today", method = RequestMethod.GET)
    public TodaysLunchMenuSearchResult todaysMenu() {
        return restaurantService.findTodaysLunchMenus();
    }
}
