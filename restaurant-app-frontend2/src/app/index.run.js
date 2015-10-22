(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .run(runBlock);

    /** @ngInject */
    function runBlock($rootScope, $location) {
        $rootScope.$on('$routeChangeError', function (event, current, previous, rejection) {
            if (rejection === 'Not Authenticated') {
                $location.path('/');
            }
        });
    }


})();
