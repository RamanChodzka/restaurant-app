(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .directive('navbar', Navbar);

    /** @ngInject */
    function Navbar() {
        var directive = {
            restrict: 'E',
            templateUrl: 'app/components/navbar/navbar.html',
            scope: {},
            controller: NavbarController,
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;

        /** @ngInject */
        function NavbarController(permissionService, $location) {
            var vm = this;
            vm.hasSufficientPermissions = function (criteria) {
                return permissionService.hasSufficientPermissions(criteria);
            };
            vm.isActive = function (path) {
                return $location.path() === path;
            };
        }
    }

})();
