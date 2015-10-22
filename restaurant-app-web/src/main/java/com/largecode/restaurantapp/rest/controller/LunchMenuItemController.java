package com.largecode.restaurantapp.rest.controller;

import com.largecode.restaurantapp.service.api.RestaurantService;
import com.largecode.restaurantapp.service.api.dto.LunchMenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/lunch-menu-items")
public class LunchMenuItemController {
    
    @Autowired
    private RestaurantService restaurantService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody LunchMenuItem lunchMenuItem, UriComponentsBuilder uriComponentsBuilder) {
        Long id = restaurantService.create(lunchMenuItem);
        UriComponents uriComponents = uriComponentsBuilder.path("/lunch-menu-items/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
    
    @RequestMapping(path = "/{lunchMenuItemId}", method = RequestMethod.GET)
    public LunchMenuItem get(@PathVariable Long lunchMenuItemId) {
        return restaurantService.findLunchMenuItemById(lunchMenuItemId);
    }
}
