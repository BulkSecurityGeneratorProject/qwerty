(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Agent_masterDeleteController',Agent_masterDeleteController);

    Agent_masterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Agent_master'];

    function Agent_masterDeleteController($uibModalInstance, entity, Agent_master) {
        var vm = this;

        vm.agent_master = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Agent_master.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
