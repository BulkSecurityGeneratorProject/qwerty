(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('agent-policy', {
            parent: 'app',
            url: '/agent-policy',
            data: {
                authorities: [],
                pageTitle: 'global.menu.agent-policy'
            },
            views: {
                'content@': {
                    templateUrl: 'app/agent-policy/agent-policy.html',
                    controller: 'AgentPolicyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent-policy');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
