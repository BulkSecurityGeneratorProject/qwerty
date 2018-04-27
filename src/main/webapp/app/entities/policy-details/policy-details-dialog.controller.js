(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Policy_detailsDialogController', Policy_detailsDialogController);

    Policy_detailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Policy_details', 'Agent_master', 'Scheme_master'];

    function Policy_detailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Policy_details, Agent_master, Scheme_master) {
        var vm = this;

        vm.policy_details = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.agent_master = [];
        vm.scheme_master = [];
        vm.getCommision = getCommision;
        loadAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function loadAll() {
            Scheme_master.query(function (result) {
               vm.scheme_master = result;
            });

            Agent_master.query(function (result) {
                vm.agent_master = result;
            });
        }

        function getCommision() {
            var scheme = vm.scheme_master.filter(function(scheme) { return scheme.id === vm.policy_details.scheme_id}) [0];
            vm.policy_details.commision = vm.policy_details.amount * scheme.commision / 100;
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.policy_details.id !== null) {
                Policy_details.update(vm.policy_details, onSaveSuccess, onSaveError);
            } else {
                Policy_details.save(vm.policy_details, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('demoApp:policy_detailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
