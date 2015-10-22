package com.largecode.restaurantapp.rest.controller;

import com.largecode.restaurantapp.service.api.RestaurantService;
import com.largecode.restaurantapp.service.api.dto.Restaurant;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchRequest;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchResult;
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
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody Restaurant restaurant, UriComponentsBuilder uriComponentsBuilder) {
        Long id = restaurantService.create(restaurant);
        UriComponents uriComponents = uriComponentsBuilder.path("/restaurants/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
    
    @RequestMapping(path = "/{restaurantId}", method = RequestMethod.GET)
    public Restaurant get(@PathVariable Long restaurantId) {
        return restaurantService.findRestaurantById(restaurantId);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RestaurantSearchResult search(RestaurantSearchRequest searchRequest) {
        return restaurantService.search(searchRequest);
    }
}
