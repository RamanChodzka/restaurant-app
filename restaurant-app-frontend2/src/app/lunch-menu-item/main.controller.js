(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .controller('LunchMenuItemController', LunchMenuItemController);

    /** @ngInject */
    function LunchMenuItemController(LunchMenuItem, $window, $location, moment) {
        var vm = this;
        vm.lunchMenuItem = {
            restaurantId: $location.search().restaurantId
        };
        vm.error = false;
        
        vm.create = function () {
            vm.error = false;
            vm.lunchMenuItem.day = moment().format();
            new LunchMenuItem(vm.lunchMenuItem).$save().then(function () {
                $window.history.back();
            }, function () {
                vm.error = true;
            });
        };
    }
})();
