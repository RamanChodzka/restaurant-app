# restaurant-app

# API Docs

##Create a restaurant
POST /api/v1/restaurants

Example request body:
```javascript
{"title":"My restaurant"}
```

##Get a restaurant
POST /api/v1/restaurants/{restaurantId}

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
POST /api/v1/lunch-menu-items/{lunchMenuItemId}

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
POST /api/v1/restaurant-votes/{restaurantVoteId}

Example response:
```javascript
{"restaurantId":1,"userId":1}
```
