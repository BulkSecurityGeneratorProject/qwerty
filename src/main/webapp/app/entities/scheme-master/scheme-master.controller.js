(function() {
    'use strict';

    angular
        .module('demoApp')
        .controller('Scheme_masterController', Scheme_masterController);

    Scheme_masterController.$inject = ['Scheme_master'];

    function Scheme_masterController(Scheme_master) {

        var vm = this;

        vm.scheme_masters = [];

        loadAll();

        function loadAll() {
            Scheme_master.query(function(result) {
                vm.scheme_masters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
