(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Policy_detailsDeleteController',Policy_detailsDeleteController);

    Policy_detailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Policy_details'];

    function Policy_detailsDeleteController($uibModalInstance, entity, Policy_details) {
        var vm = this;

        vm.policy_details = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Policy_details.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
