(function () {
    'use strict';

    var API_PREFIX = '/api/v1';

    angular
            .module('restaurantAppFrontend')
            .factory('Restaurant', function ($resource) {
                return $resource(API_PREFIX + '/restaurants/:restaurantId', {restaurantId: '@id'}, {query: {isArray: false}});
            })
            .factory('LunchMenuItem', function ($resource) {
                return $resource(API_PREFIX + '/lunch-menu-items/:lunchMenuItemId', {lunchMenuItemId: '@id'}, {query: {isArray: false}});
            })
            .factory('LunchMenu', function ($resource) {
                return $resource(API_PREFIX + '/lunch-menus/', {}, {query: {isArray: false}, today: {isArray: false, url: API_PREFIX + '/lunch-menus/today'}});
            })
            .factory('RestaurantVote', function ($resource) {
                return $resource(API_PREFIX + '/restaurant-votes', {}, {});
            });
})();
