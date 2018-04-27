(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Agent_masterDetailController', Agent_masterDetailController);

    Agent_masterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Agent_master'];

    function Agent_masterDetailController($scope, $rootScope, $stateParams, previousState, entity, Agent_master) {
        var vm = this;

        vm.agent_master = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('demoApp:agent_masterUpdate', function(event, result) {
            vm.agent_master = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
