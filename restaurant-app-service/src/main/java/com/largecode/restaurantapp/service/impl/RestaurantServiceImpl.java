package com.largecode.restaurantapp.service.impl;

import com.largecode.restaurantapp.service.util.TypesConverter;
import com.largecode.restaurantapp.model.LunchMenuItemEntity;
import com.largecode.restaurantapp.model.RestaurantEntity;
import com.largecode.restaurantapp.model.RestaurantVoteEntity;
import com.largecode.restaurantapp.service.api.RestaurantService;
import com.largecode.restaurantapp.service.api.dto.LunchMenu;
import com.largecode.restaurantapp.service.api.dto.LunchMenuItem;
import com.largecode.restaurantapp.service.api.dto.TodaysLunchMenuSearchResult;
import com.largecode.restaurantapp.service.api.dto.Restaurant;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchRequest;
import com.largecode.restaurantapp.service.api.dto.RestaurantSearchResult;
import com.largecode.restaurantapp.service.api.dto.RestaurantVote;
import com.largecode.restaurantapp.service.api.exception.VoteCannotBeAcceptedException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private TypesConverter typesConverter;

    @Override
    public Long create(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = typesConverter.convertToRestaurantEntity(restaurant);
        entityManager.persist(restaurantEntity);
        return restaurantEntity.getId();
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) {
        Restaurant result = null;
        RestaurantEntity restaurantEntity = entityManager.find(RestaurantEntity.class, restaurantId);
        if (restaurantEntity != null) {
            result = typesConverter.convertToRestaurant(restaurantEntity);
        }
        return result;
    }

    @Override
    public RestaurantSearchResult search(RestaurantSearchRequest searchRequest) {
        List<RestaurantEntity> restaurantEntities = entityManager.createQuery(createRestaurantSearchQuery())
                .setFirstResult(searchRequest.getOffset())
                .setMaxResults(searchRequest.getLimit())
                .getResultList();
        RestaurantSearchResult result = new RestaurantSearchResult();
        result.setRestaurants(restaurantEntities.stream()
                .map(typesConverter::convertToRestaurant)
                .collect(toList()));
        return result;
    }
    
    private CriteriaQuery<RestaurantEntity> createRestaurantSearchQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RestaurantEntity> query = criteriaBuilder.createQuery(RestaurantEntity.class);
        Root<RestaurantEntity> restaurantEntityRoot = query.from(RestaurantEntity.class);
        query.select(restaurantEntityRoot);
        query.orderBy(criteriaBuilder.asc(restaurantEntityRoot.get("id")));
        return query;
    }

    @Override
    public Long create(LunchMenuItem lunchMenuItem) {
        LunchMenuItemEntity lunchMenuItemEntity = typesConverter.convertToLunchMenuItemEntity(lunchMenuItem);
        entityManager.persist(lunchMenuItemEntity);
        return lunchMenuItemEntity.getId();
    }

    @Override
    @Transactional(rollbackFor = VoteCannotBeAcceptedException.class)
    public Long create(RestaurantVote restaurantVote) throws VoteCannotBeAcceptedException {
        RestaurantVoteEntity restaurantVoteEntity = typesConverter.convertToRestaurantVoteEntity(restaurantVote);
        restaurantVoteEntity.setVoteTime(new Date());
        List<RestaurantVoteEntity> todaysVotes = findTodaysVotes(restaurantVoteEntity.getUserId(), restaurantVoteEntity.getRestaurant());
        if (!todaysVotes.isEmpty()) {
            if (canUserChangeVote()) {
                todaysVotes.stream().forEach(entityManager::remove);
            } else {
                throw new VoteCannotBeAcceptedException();
            }
        }
        entityManager.persist(restaurantVoteEntity);
        return restaurantVoteEntity.getId();
    }
    
    boolean canUserChangeVote() {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        Date elevenOClock = calendar.getTime();
        return now.before(elevenOClock);
    }
    
    private List<RestaurantVoteEntity> findTodaysVotes(Long userId, RestaurantEntity restaurantEntity) {
        Calendar today = Calendar.getInstance();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RestaurantVoteEntity> criteriaQuery = criteriaBuilder.createQuery(RestaurantVoteEntity.class);
        Root<RestaurantVoteEntity> restaurantVoteEntityRoot = criteriaQuery.from(RestaurantVoteEntity.class);
        criteriaQuery.select(restaurantVoteEntityRoot);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(restaurantVoteEntityRoot.get("userId"), userId),
                criteriaBuilder.equal(
                        criteriaBuilder.function("year", Integer.class, restaurantVoteEntityRoot.get("voteTime")),
                        criteriaBuilder.literal(today.get(Calendar.YEAR))),
                criteriaBuilder.equal(
                        criteriaBuilder.function("month", Integer.class, restaurantVoteEntityRoot.get("voteTime")),
                        criteriaBuilder.literal(today.get(Calendar.MONTH) + 1)),
                criteriaBuilder.equal(
                        criteriaBuilder.function("day", Integer.class, restaurantVoteEntityRoot.get("voteTime")),
                        criteriaBuilder.literal(today.get(Calendar.DAY_OF_MONTH))),
                criteriaBuilder.equal(restaurantVoteEntityRoot.get("restaurant"), restaurantEntity)
        ));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public RestaurantVote findRestaurantVoteById(Long restaurantVoteId) {
        RestaurantVote result = null;
        RestaurantVoteEntity restaurantVoteEntity = entityManager.find(RestaurantVoteEntity.class, restaurantVoteId);
        if (restaurantVoteEntity != null) {
            result = typesConverter.convertToRestaurantVote(restaurantVoteEntity);
        }
        return result;
    }

    @Override
    public LunchMenuItem findLunchMenuItemById(Long lunchMenuItemId) {
        LunchMenuItem result = null;
        LunchMenuItemEntity lunchMenuItemEntity = entityManager.find(LunchMenuItemEntity.class, lunchMenuItemId);
        if (lunchMenuItemEntity != null) {
            result = typesConverter.convertToLunchMenuItem(lunchMenuItemEntity);
        }
        return result;
    }

    @Override
    public TodaysLunchMenuSearchResult findTodaysLunchMenus() {
        TodaysLunchMenuSearchResult result = new TodaysLunchMenuSearchResult();
        List<LunchMenuItemEntity> lunchMenuItemEntities = findTodaysLunchMenuItemEntities();
        result.setLunchMenus(typesConverter.convertToLunchMenus(lunchMenuItemEntities));
        return result;
    }
    
    private List<LunchMenuItemEntity> findTodaysLunchMenuItemEntities() {
        Calendar today = Calendar.getInstance();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<LunchMenuItemEntity> criteriaQuery = criteriaBuilder.createQuery(LunchMenuItemEntity.class);
        Root<LunchMenuItemEntity> lunchMenuItemEntityRoot = criteriaQuery.from(LunchMenuItemEntity.class);
        criteriaQuery.select(lunchMenuItemEntityRoot);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(
                        criteriaBuilder.function("year", Integer.class, lunchMenuItemEntityRoot.get("day")),
                        criteriaBuilder.literal(today.get(Calendar.YEAR))),
                criteriaBuilder.equal(
                        criteriaBuilder.function("month", Integer.class, lunchMenuItemEntityRoot.get("day")),
                        criteriaBuilder.literal(today.get(Calendar.MONTH) + 1)),
                criteriaBuilder.equal(
                        criteriaBuilder.function("day", Integer.class, lunchMenuItemEntityRoot.get("day")),
                        criteriaBuilder.literal(today.get(Calendar.DAY_OF_MONTH)))
        ));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
