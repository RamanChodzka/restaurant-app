package com.largecode.restaurantapp.rest.controller;

import com.largecode.restaurantapp.service.api.dto.RestaurantVote;
import com.largecode.restaurantapp.service.api.RestaurantService;
import com.largecode.restaurantapp.service.api.exception.VoteCannotBeAcceptedException;
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
@RequestMapping("/restaurant-votes")
public class RestaurantVoteController {

    @Autowired
    private RestaurantService restaurantService;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody RestaurantVote restaurantVote, UriComponentsBuilder uriComponentsBuilder) throws VoteCannotBeAcceptedException {
        Long id = restaurantService.create(restaurantVote);
        UriComponents uriComponents = uriComponentsBuilder.path("/restaurant-votes/{id}").buildAndExpand(id);
        return ResponseEntity.created(uriComponents.toUri()).build();
    }
    
    @RequestMapping(path = "/{restaurantVoteId}", method = RequestMethod.GET)
    public RestaurantVote create(@PathVariable Long restaurantVoteId) throws VoteCannotBeAcceptedException {
        return restaurantService.findRestaurantVoteById(restaurantVoteId);
    }
}
