package com.largecode.restaurantapp.model;

import java.io.Serializable;
import java.util.Objects;


public abstract class BaseEntity implements Serializable {

    public abstract Long getId();

    public abstract void setId(Long id);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return getId() != null && getId().equals(other.getId());
    }
}
