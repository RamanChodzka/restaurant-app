(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .controller('RestaurantsController', RestaurantsController);

    /** @ngInject */
    function RestaurantsController(Restaurant) {
        var vm = this;
        Restaurant.query().$promise.then(function (data) {
            vm.restaurants = data.restaurants;
        });
    }
})();
