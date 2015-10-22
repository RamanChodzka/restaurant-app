package com.largecode.restaurantapp.service.api;

import com.largecode.restaurantapp.service.api.dto.LunchMenuItem;
import com.largecode.restaurantapp.service.api.dto.TodaysLunchMenuSearchResult;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchRequest;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchResult;
import com.largecode.restaurantapp.service.api.dto.Restaurant;
import com.largecode.restaurantapp.service.api.dto.RestaurantVote;
import com.largecode.restaurantapp.service.api.exception.VoteCannotBeAcceptedException;


public interface RestaurantService {
    Long create(Restaurant restaurant);
    Restaurant findRestaurantById(Long restaurantId);
    RestaurantSearchResult search(RestaurantSearchRequest searchRequest);
    Long create(LunchMenuItem lunchMenuItem);
    Long create(RestaurantVote restaurantVote) throws VoteCannotBeAcceptedException;
    RestaurantVote findRestaurantVoteById(Long restaurantVoteId);
    LunchMenuItem findLunchMenuItemById(Long lunchMenuItemId);
    TodaysLunchMenuSearchResult findTodaysLunchMenus();
}
