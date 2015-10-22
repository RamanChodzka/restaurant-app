package com.largecode.restaurantapp.service.impl;

import com.largecode.restaurantapp.model.LunchMenuItemEntity;
import com.largecode.restaurantapp.model.RestaurantEntity;
import com.largecode.restaurantapp.model.RestaurantVoteEntity;
import com.largecode.restaurantapp.service.api.dto.LunchMenu;
import com.largecode.restaurantapp.service.api.dto.LunchMenuItem;
import com.largecode.restaurantapp.service.api.dto.RestaurantVote;
import com.largecode.restaurantapp.service.api.dto.TodaysLunchMenuSearchResult;
import com.largecode.restaurantapp.service.api.exception.VoteCannotBeAcceptedException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(value = {"classpath:/testApplicationContext.xml"})
public class RestaurantServiceImplTest {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private RestaurantServiceImpl restaurantService;
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testCreateRestaurantVote_NoOtherVotesExistYet() throws VoteCannotBeAcceptedException {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setTitle("title");
        entityManager.persist(restaurantEntity);
        entityManager.flush();
        RestaurantVote restaurantVote = new RestaurantVote();
        restaurantVote.setRestaurantId(restaurantEntity.getId());
        restaurantVote.setUserId(1L);
        entityManager.flush();
        entityManager.clear();
        
        Long actualId = restaurantService.create(restaurantVote);
        
        assertNotNull(actualId);
    }
    
    @Test
    public void testCreateRestaurantVote_AnotherTodaysVoteExists_UserCanRevote() throws VoteCannotBeAcceptedException {
        final Long userId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setTitle("title");
        entityManager.persist(restaurantEntity);
        entityManager.flush();
        RestaurantVoteEntity previousRestaurantVote = new RestaurantVoteEntity();
        previousRestaurantVote.setRestaurant(restaurantEntity);
        previousRestaurantVote.setUserId(userId);
        previousRestaurantVote.setVoteTime(new Date());
        entityManager.persist(previousRestaurantVote);
        RestaurantVote newRestaurantVote = new RestaurantVote();
        newRestaurantVote.setRestaurantId(restaurantEntity.getId());
        newRestaurantVote.setUserId(userId);
        entityManager.flush();
        entityManager.clear();
        when(restaurantService.canUserChangeVote()).thenReturn(true);
        
        Long actualId = restaurantService.create(newRestaurantVote);
        
        verify(restaurantService).canUserChangeVote();
        assertNotNull(actualId);
        List<RestaurantVoteEntity> actuals = entityManager.createQuery(
                "SELECT v FROM RestaurantVoteEntity v", RestaurantVoteEntity.class)
                .getResultList();
        assertEquals(1, actuals.size());
        assertEquals(actualId, actuals.get(0).getId());
    }
    
    @Test(expected = VoteCannotBeAcceptedException.class)
    public void testCreateRestaurantVote_AnotherTodaysVoteExists_UserCannotRevote() throws VoteCannotBeAcceptedException {
        final Long userId = 1L;
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setTitle("title");
        entityManager.persist(restaurantEntity);
        entityManager.flush();
        RestaurantVoteEntity previousRestaurantVote = new RestaurantVoteEntity();
        previousRestaurantVote.setRestaurant(restaurantEntity);
        previousRestaurantVote.setUserId(userId);
        previousRestaurantVote.setVoteTime(new Date());
        entityManager.persist(previousRestaurantVote);
        RestaurantVote newRestaurantVote = new RestaurantVote();
        newRestaurantVote.setRestaurantId(restaurantEntity.getId());
        newRestaurantVote.setUserId(userId);
        entityManager.flush();
        entityManager.clear();
        when(restaurantService.canUserChangeVote()).thenReturn(false);
        
        restaurantService.create(newRestaurantVote);
    }
    
    @Test
    public void testSearchTodaysLunchMenus() {
        RestaurantEntity restaurantEntity1 = new RestaurantEntity();
        restaurantEntity1.setTitle("restaurantEntity1");
        entityManager.persist(restaurantEntity1);
        RestaurantEntity restaurantEntity2 = new RestaurantEntity();
        restaurantEntity2.setTitle("restaurantEntity2");
        entityManager.persist(restaurantEntity2);
        LunchMenuItemEntity lunchMenuItemEntity1 = new LunchMenuItemEntity();
        lunchMenuItemEntity1.setDay(new Date());
        lunchMenuItemEntity1.setName("lunchMenuItemEntity1");
        lunchMenuItemEntity1.setPrice(BigDecimal.ZERO);
        lunchMenuItemEntity1.setRestaurant(restaurantEntity1);
        entityManager.persist(lunchMenuItemEntity1);
        LunchMenuItemEntity lunchMenuItemEntity2 = new LunchMenuItemEntity();
        lunchMenuItemEntity2.setDay(new Date());
        lunchMenuItemEntity2.setName("lunchMenuItemEntity1");
        lunchMenuItemEntity2.setPrice(BigDecimal.ONE);
        lunchMenuItemEntity2.setRestaurant(restaurantEntity1);
        entityManager.persist(lunchMenuItemEntity2);
        LunchMenuItemEntity lunchMenuItemEntity3 = new LunchMenuItemEntity();
        lunchMenuItemEntity3.setDay(new Date());
        lunchMenuItemEntity3.setName("lunchMenuItemEntity1");
        lunchMenuItemEntity3.setPrice(BigDecimal.TEN);
        lunchMenuItemEntity3.setRestaurant(restaurantEntity2);
        entityManager.persist(lunchMenuItemEntity3);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_MONTH, -1);
        LunchMenuItemEntity unexpectedLunchMenuItemEntity = new LunchMenuItemEntity();
        unexpectedLunchMenuItemEntity.setDay(yesterday.getTime());
        unexpectedLunchMenuItemEntity.setName("lunchMenuItemEntity1");
        unexpectedLunchMenuItemEntity.setPrice(BigDecimal.valueOf(2));
        unexpectedLunchMenuItemEntity.setRestaurant(restaurantEntity1);
        entityManager.persist(unexpectedLunchMenuItemEntity);
        entityManager.flush();
        entityManager.clear();
        
        
        TodaysLunchMenuSearchResult searchResult = restaurantService.findTodaysLunchMenus();
        
        
        List<LunchMenu> actuals = searchResult.getLunchMenus();
        assertEquals(2, actuals.size());
        
        LunchMenu lunchMenuOfRestaurant1 = actuals.stream()
                .filter(lunchMenu -> lunchMenu.getRestaurant().getId().equals(restaurantEntity1.getId()))
                .findFirst().get();
        assertEquals(2, lunchMenuOfRestaurant1.getLunchMenuItems().size());
        List<Long> lunchMenuItemsOfRestaurant1 = lunchMenuOfRestaurant1.getLunchMenuItems()
                .stream().map(LunchMenuItem::getId).collect(toList());
        assertTrue(lunchMenuItemsOfRestaurant1.contains(lunchMenuItemEntity1.getId()));
        assertTrue(lunchMenuItemsOfRestaurant1.contains(lunchMenuItemEntity2.getId()));
        
        LunchMenu lunchMenuOfRestaurant2 = actuals.stream()
                .filter(lunchMenu -> lunchMenu.getRestaurant().getId().equals(restaurantEntity2.getId()))
                .findFirst().get();
        assertEquals(1, lunchMenuOfRestaurant2.getLunchMenuItems().size());
        LunchMenuItem lunchMenuItemOfRestaurant2 = lunchMenuOfRestaurant2.getLunchMenuItems().get(0);
        assertEquals(lunchMenuItemEntity3.getId(), lunchMenuItemOfRestaurant2.getId());
    }
}
