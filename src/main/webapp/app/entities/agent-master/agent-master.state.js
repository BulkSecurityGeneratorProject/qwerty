(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agent-master', {
            parent: 'entity',
            url: '/agent-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.agent_master.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent-master/agent-masters.html',
                    controller: 'Agent_masterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent_master');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('agent-master-detail', {
            parent: 'agent-master',
            url: '/agent-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.agent_master.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agent-master/agent-master-detail.html',
                    controller: 'Agent_masterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agent_master');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Agent_master', function($stateParams, Agent_master) {
                    return Agent_master.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agent-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agent-master-detail.edit', {
            parent: 'agent-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'Agent_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agent_master', function(Agent_master) {
                            return Agent_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agent-master.new', {
            parent: 'agent-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'Agent_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('agent-master');
                });
            }]
        })
        .state('agent-master.edit', {
            parent: 'agent-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-dialog.html',
                    controller: 'Agent_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agent_master', function(Agent_master) {
                            return Agent_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agent-master.delete', {
            parent: 'agent-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agent-master/agent-master-delete-dialog.html',
                    controller: 'Agent_masterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Agent_master', function(Agent_master) {
                            return Agent_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agent-master', null, { reload: 'agent-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
