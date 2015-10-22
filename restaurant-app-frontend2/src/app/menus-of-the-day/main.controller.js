(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .controller('MenusOfTheDayController', MenusOfTheDayController);

    /** @ngInject */
    function MenusOfTheDayController(LunchMenu, RestaurantVote, permissionService) {
        var vm = this;
        LunchMenu.today().$promise.then(function (data) {
            vm.lunchMenus = data.lunchMenus;
        });
        
        vm.voteFor = function (restaurant) {
            var restaurantVote = {};
            restaurantVote.restaurantId = restaurant.id;
            restaurantVote.userId = permissionService.user.id;
            new RestaurantVote(restaurantVote).$save().then(function () {
                restaurant.voteSuccess = true;
                restaurant.error = null;
                restaurant.errorStatus = null;
            }, function (reponse) {
                restaurant.error = reponse.data;
                restaurant.errorStatus = reponse.status;
                restaurant.voteSuccess = false;
                
            });
        };
    }
})();
