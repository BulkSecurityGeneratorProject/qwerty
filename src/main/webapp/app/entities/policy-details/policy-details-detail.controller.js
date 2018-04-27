(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Policy_detailsDetailController', Policy_detailsDetailController);

    Policy_detailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Policy_details'];

    function Policy_detailsDetailController($scope, $rootScope, $stateParams, previousState, entity, Policy_details) {
        var vm = this;

        vm.policy_details = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demoApp:policy_detailsUpdate', function(event, result) {
            vm.policy_details = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
