(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .controller('MainController', MainController);

    /** @ngInject */
    function MainController(permissionService, $location) {
        var vm = this;
        vm.userRoles = [
            {
                code: 'ADMIN',
                title: 'Admin'
            },
            {
                code: 'USER',
                title: 'Regular user'
            }
        ];
        vm.user = {};
        permissionService.user = null;
        
        vm.submit = function () {
            permissionService.user = vm.user;
            $location.path('/menus-of-the-day');
        };
    }
})();
