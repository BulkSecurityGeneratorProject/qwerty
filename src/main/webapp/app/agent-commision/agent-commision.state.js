(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('agent-commision', {
            parent: 'app',
            url: '/agent-commision',
            data: {
                authorities: [],
                pageTitle: 'global.menu.agent-commision'
            },
            views: {
                'content@': {
                    templateUrl: 'app/agent-commision/agent-commision.html',
                    controller: 'AgentCommisionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent-commision');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
