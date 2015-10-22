(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .config(routeConfig);

    /** @ngInject */
    function routeConfig($routeProvider) {
        $routeProvider
                .when('/', {
                    templateUrl: 'app/main/main.html',
                    controller: 'MainController',
                    controllerAs: 'main'
                })
                .when('/restaurants', {
                    templateUrl: 'app/restaurants/main.html',
                    controller: 'RestaurantsController',
                    controllerAs: 'main',
                    resolve : {
                        permitted: function (permissionService) {
                            return permissionService.isPermitted({
                                role: 'ADMIN'
                            });
                        }
                    }
                })
                .when('/restaurants/new', {
                    templateUrl: 'app/restaurant/main.html',
                    controller: 'RestaurantController',
                    controllerAs: 'main',
                    resolve : {
                        permitted: function (permissionService) {
                            return permissionService.isPermitted({
                                role: 'ADMIN'
                            });
                        }
                    }
                })
                .when('/lunch-menu-items/new', {
                    templateUrl: 'app/lunch-menu-item/main.html',
                    controller: 'LunchMenuItemController',
                    controllerAs: 'main',
                    resolve : {
                        permitted: function (permissionService) {
                            return permissionService.isPermitted({
                                role: 'ADMIN'
                            });
                        }
                    }
                })
                .when('/menus-of-the-day', {
                    templateUrl: 'app/menus-of-the-day/main.html',
                    controller: 'MenusOfTheDayController',
                    controllerAs: 'main',
                    resolve : {
                        permitted: function (permissionService) {
                            return permissionService.isPermitted({});
                        }
                    }
                })
                .otherwise({
                    redirectTo: '/'
                });
    }

})();
