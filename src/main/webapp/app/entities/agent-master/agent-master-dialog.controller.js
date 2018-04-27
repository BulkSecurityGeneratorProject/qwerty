(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Agent_masterDialogController', Agent_masterDialogController);

    Agent_masterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Agent_master'];

    function Agent_masterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Agent_master) {
        var vm = this;

        vm.agent_master = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.agent_master.id !== null) {
                Agent_master.update(vm.agent_master, onSaveSuccess, onSaveError);
            } else {
                Agent_master.save(vm.agent_master, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:agent_masterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
