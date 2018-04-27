(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Scheme_masterDeleteController',Scheme_masterDeleteController);

    Scheme_masterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Scheme_master'];

    function Scheme_masterDeleteController($uibModalInstance, entity, Scheme_master) {
        var vm = this;

        vm.scheme_master = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Scheme_master.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
