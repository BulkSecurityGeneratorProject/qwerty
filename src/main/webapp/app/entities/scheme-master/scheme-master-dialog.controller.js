(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Scheme_masterDialogController', Scheme_masterDialogController);

    Scheme_masterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Scheme_master'];

    function Scheme_masterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Scheme_master) {
        var vm = this;

        vm.scheme_master = entity;
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
            if (vm.scheme_master.id !== null) {
                Scheme_master.update(vm.scheme_master, onSaveSuccess, onSaveError);
            } else {
                Scheme_master.save(vm.scheme_master, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:scheme_masterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
