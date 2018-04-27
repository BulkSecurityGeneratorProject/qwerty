(function () {
    'use strict';

    angular
        .module('demoApp')
        .controller('AgentPolicyController', AgentPolicyController);

    AgentPolicyController.$inject = ['$scope', 'AgentPolicyService','Policy_details','Agent_master','Scheme_master'];

    function AgentPolicyController($scope, AgentPolicyService, Policy_details, Agent_master, Scheme_master) {
        var vm = this;
        vm.agentPolicies = [];
        vm.policies = [];
        vm.agents = [];
        vm.schemas = [];
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
            Agent_master.query(function (result) {
                vm.agents = result;

                Policy_details.query(function (result) {
                    vm.policies = result;

                    Scheme_master.query(function (result) {
                        vm.schemas = result;
                        loadData();
                    });
                });
            });
        }

        function dateWithOrdinal(date) {
            return date + AgentPolicyService.getOrdinalIndicator(date);
        }

        function loadData() {
            for(var i=0; i< vm.agents.length; i++){
                for(var j=0; j<vm.schemas.length;j++){
                    var myPolicies = vm.policies.filter(function(policy) { return policy.agent_id === vm.agents[i].id});
                    if(myPolicies.length >= 0){
                        var myPolicies2 = myPolicies.filter(function(policy) { return policy.scheme_id === vm.schemas[j].id});
                        var obj = {};
                        obj.agent = vm.agents[i].name;
                        obj.scheme = vm.schemas[j].name;
                        obj.policies = myPolicies2.length;
                        vm.agentPolicies.push(obj);
                    }
                }
            }
        }

    }
})();
