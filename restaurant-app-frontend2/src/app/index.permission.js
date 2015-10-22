(function () {
    'use strict';

    angular
            .module('restaurantAppFrontend')
            .service('permissionService', PermissionService);

    /** @ngInject */
    function PermissionService($q) {
        var self = this;
        self.user = null;
        self.isPermitted = function (criteria) {
            if (self.hasSufficientPermissions(criteria)) {
                return true;
            } else {
                return $q.reject('Not Authenticated');
            }
        };
        
        self.hasSufficientPermissions = function (criteria) {
            var permitted = self.user && self.user.id;
            if (permitted && criteria.role) {
                permitted = self.user.role === criteria.role;
            }
            return permitted;
        };
    }

})();
