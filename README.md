# restaurant-app

# API Docs

##Create a restaurant
POST /api/v1/restaurants

Example request body:
```javascript
{"title":"My restaurant"}
```

##Get a restaurant
GET /api/v1/restaurants/{restaurantId}

Example response:
```javascript
{"id":1,"title":"My restaurant"}
```

##Find restaurants
GET /api/v1/restaurants

Example response:
```javascript
{"restaurants":[{"id":1,"title":"My restaurant"}]}
```

##Create a lunch menu item
POST /api/v1/lunch-menu-items

Example request body:
```javascript
{"restaurantId":"1","name":"Lunch menu item","price":10,"day":"2015-10-22T23:56:10+03:00"}
```

##Get a lunch menu item
GET /api/v1/lunch-menu-items/{lunchMenuItemId}

Example response:
```javascript
{"id":1,"name":"Lunch menu item","price":10.00,"day":"2015-10-22","restaurantId":1}
```

##Cast a vote
POST /api/v1/restaurant-votes

Example request body:
```javascript
{"restaurantId":1,"userId":1}
```

##Get a vote
GET /api/v1/restaurant-votes/{restaurantVoteId}

Example response:
```javascript
{"restaurantId":1,"userId":1}
```

##Get menu of the day
GET /api/v1/lunch-menus/today

Example response:
```javascript
{
  "lunchMenus":[
    {
      "restaurant":{
        "id":1,
        "title":"My restaurant"
      },
      "lunchMenuItems":[
        {
          "id":1,
          "name":"Lunch menu item",
          "price":10.00,
          "day":"2015-10-22",
          "restaurantId":1
        }
      ]
    }
  ]
}
```


#Building back-end
Run ```mvn clean install``` against restaurant-app/pom.xml. The result is a war file inside restaurant-app-web/target directory.

This app relies on Hibernate schema definition capabilities. This app is configured to use MySQL 5. If you intend to use a different RDBMS, update ```hibernate.dialect``` property (inside restaurant-app-service/src/main/resources/META-INF/persistence.xml) accordingly.

#Building front-end
Run ```npm install``` and ```gulp``` (without parameters) from inside restaurant-app-frontend2 directory. The resulting app is in restaurant-app-frontend2/dist directory
