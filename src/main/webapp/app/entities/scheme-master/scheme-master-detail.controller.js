(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Scheme_masterDetailController', Scheme_masterDetailController);

    Scheme_masterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Scheme_master'];

    function Scheme_masterDetailController($scope, $rootScope, $stateParams, previousState, entity, Scheme_master) {
        var vm = this;

        vm.scheme_master = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demoApp:scheme_masterUpdate', function(event, result) {
            vm.scheme_master = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
