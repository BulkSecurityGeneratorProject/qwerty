(function () {
    'use strict';

    angular
        .module('demoApp')
        .controller('AgentCommisionController', AgentCommisionController);

    AgentCommisionController.$inject = ['$scope', 'AgentCommisionService','Policy_details','Agent_master'];

    function AgentCommisionController($scope, AgentCommisionService, Policy_details, Agent_master) {
        var vm = this;
        vm.policies = [];
        vm.agents = [];

        loadAll();

        $scope.controllerMessage = todayToString();

        vm.todayToString = todayToString;

        function todayToString() {
            var today = new Date();
            var dayName = today.toLocaleString('en-us', {weekday: 'long'});
            var monthName = today.toLocaleString('en-us', {month: 'long'});
            var date = today.getDate();

            return 'Today is ' + dayName + ', the ' + dateWithOrdinal(date) + ' of ' + monthName + ', ' + today.getFullYear();
        }

        function loadAll() {
            Policy_details.query(function (result) {
                vm.policies = result;

                Agent_master.query(function (result) {
                    getCommision(result);
                });

            });
        }

        function getCommision(result) {
             var commision = null;
            for(var i=0; i < result.length; i++){
                var myPolicies = vm.policies.filter(function(policy) { return policy.agent_id === result[i].id});
                if(myPolicies.length >= 0){
                    for(var j=0; j < myPolicies.length; j++){
                        commision = commision + myPolicies[j].commision;
                    }
                    vm.agents.push({name:result[i].name, commision:commision});
                    commision = 0;
                }
            }
        }

        function dateWithOrdinal(date) {
            return date + AgentCommisionService.getOrdinalIndicator(date);
        }

    }
})();
