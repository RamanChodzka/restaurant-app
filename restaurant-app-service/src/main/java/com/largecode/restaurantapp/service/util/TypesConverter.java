package com.largecode.restaurantapp.service.util;

import com.largecode.restaurantapp.model.LunchMenuItemEntity;
import com.largecode.restaurantapp.model.RestaurantEntity;
import com.largecode.restaurantapp.model.RestaurantVoteEntity;
import com.largecode.restaurantapp.service.api.dto.LunchMenu;
import com.largecode.restaurantapp.service.api.dto.LunchMenuItem;
import com.largecode.restaurantapp.service.api.dto.Restaurant;
import com.largecode.restaurantapp.service.api.dto.RestaurantVote;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TypesConverter {

    @Autowired
    private RepositoryUtils repositoryUtils;
    
    public RestaurantEntity convertToRestaurantEntity(Restaurant from) {
        RestaurantEntity to = new RestaurantEntity();
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        return to;
    }

    public Restaurant convertToRestaurant(RestaurantEntity from) {
        Restaurant to = new Restaurant();
        to.setId(from.getId());
        to.setTitle(from.getTitle());
        return to;
    }

    public LunchMenuItemEntity convertToLunchMenuItemEntity(LunchMenuItem from) {
        LunchMenuItemEntity to = new LunchMenuItemEntity();
        to.setId(from.getId());
        to.setDay(from.getDay());
        to.setName(from.getName());
        to.setPrice(from.getPrice());
        to.setRestaurant(repositoryUtils.findOrFail(RestaurantEntity.class, from.getRestaurantId()));
        return to;
    }

    public RestaurantVoteEntity convertToRestaurantVoteEntity(RestaurantVote from) {
        RestaurantVoteEntity to = new RestaurantVoteEntity();
        to.setUserId(from.getUserId());
        to.setRestaurant(repositoryUtils.findOrFail(RestaurantEntity.class, from.getRestaurantId()));
        return to;
    }

    public RestaurantVote convertToRestaurantVote(RestaurantVoteEntity from) {
        RestaurantVote to = new RestaurantVote();
        if (from.getRestaurant() != null) {
            to.setRestaurantId(from.getRestaurant().getId());
        }
        to.setUserId(from.getUserId());
        return to;
    }

    public LunchMenuItem convertToLunchMenuItem(LunchMenuItemEntity from) {
        LunchMenuItem to = new LunchMenuItem();
        to.setDay(from.getDay());
        to.setId(from.getId());
        to.setName(from.getName());
        to.setPrice(from.getPrice());
        if (from.getRestaurant() != null) {
            to.setRestaurantId(from.getRestaurant().getId());
        }
        return to;
    }
    
    public LunchMenu convertToLunchMenu(RestaurantEntity restaurantEntity, List<LunchMenuItemEntity> lunchMenuItemEntities) {
        return convertToLunchMenu(
                convertToRestaurant(restaurantEntity),
                lunchMenuItemEntities.stream().map(this::convertToLunchMenuItem).collect(toList()));
    }
    
    public LunchMenu convertToLunchMenu(Restaurant restaurant, List<LunchMenuItem> lunchMenuItems) {
        LunchMenu result = new LunchMenu();
        result.setLunchMenuItems(lunchMenuItems);
        result.setRestaurant(restaurant);
        return result;
    }
    
    public List<LunchMenu> convertToLunchMenus(Collection<LunchMenuItemEntity> lunchMenuItemEntities) {
        Map<RestaurantEntity, List<LunchMenuItemEntity>> lunchMenuItemsByRestaurants =
                lunchMenuItemEntities.stream().collect(groupingBy(LunchMenuItemEntity::getRestaurant));
        return lunchMenuItemsByRestaurants.entrySet().stream()
                .map(entry -> convertToLunchMenu(entry.getKey(), entry.getValue()))
                .collect(toList());
    }
}
