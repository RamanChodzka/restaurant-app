package com.largecode.restaurantapp.service.util;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;


@Service
public class RepositoryUtils {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public <T> T findOrFail(Class<T> clazz, Object primaryKey) {
        T result = entityManager.find(clazz, primaryKey);
        if (result == null) {
            throw new IllegalArgumentException(String.format("Failed to find %s by primary key '%s'", clazz, primaryKey));
        }
        return result;
    }
}
