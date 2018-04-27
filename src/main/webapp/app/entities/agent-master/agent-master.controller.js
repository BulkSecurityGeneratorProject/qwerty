(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Agent_masterController', Agent_masterController);

    Agent_masterController.$inject = ['Agent_master'];

    function Agent_masterController(Agent_master) {

        var vm = this;

        vm.agent_masters = [];

        loadAll();

        function loadAll() {
            Agent_master.query(function(result) {
                vm.agent_masters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
