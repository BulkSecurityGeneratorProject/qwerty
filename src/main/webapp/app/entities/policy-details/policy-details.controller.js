(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Policy_detailsController', Policy_detailsController);

    Policy_detailsController.$inject = ['Policy_details'];

    function Policy_detailsController(Policy_details) {

        var vm = this;

        vm.policy_details = [];

        loadAll();

        function loadAll() {
            Policy_details.query(function(result) {
                vm.policy_details = result;
                vm.searchQuery = null;
            });
        }
    }
})();
