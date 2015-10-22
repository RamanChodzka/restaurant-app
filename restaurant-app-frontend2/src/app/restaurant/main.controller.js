(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .controller('RestaurantController', RestaurantController);

    /** @ngInject */
    function RestaurantController(Restaurant, $window) {
        var vm = this;
        vm.restaurant = {};
        vm.error = false;
        
        vm.create = function () {
            vm.error = false;
            new Restaurant(vm.restaurant).$save().then(function () {
                $window.history.back();
            }, function () {
                vm.error = true;
            });
        };
    }
})();
