(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('policy-details', {
            parent: 'entity',
            url: '/policy-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.policy_details.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/policy-details/policy-details.html',
                    controller: 'Policy_detailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('policy_details');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('policy-details-detail', {
            parent: 'policy-details',
            url: '/policy-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.policy_details.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/policy-details/policy-details-detail.html',
                    controller: 'Policy_detailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('policy_details');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Policy_details', function($stateParams, Policy_details) {
                    return Policy_details.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'policy-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('policy-details-detail.edit', {
            parent: 'policy-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/policy-details/policy-details-dialog.html',
                    controller: 'Policy_detailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Policy_details', function(Policy_details) {
                            return Policy_details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('policy-details.new', {
            parent: 'policy-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/policy-details/policy-details-dialog.html',
                    controller: 'Policy_detailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                scheme_id: null,
                                agent_id: null,
                                customer_name: null,
                                amount: null,
                                commision: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('policy-details', null, { reload: 'policy-details' });
                }, function() {
                    $state.go('policy-details');
                });
            }]
        })
        .state('policy-details.edit', {
            parent: 'policy-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/policy-details/policy-details-dialog.html',
                    controller: 'Policy_detailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Policy_details', function(Policy_details) {
                            return Policy_details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('policy-details', null, { reload: 'policy-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('policy-details.delete', {
            parent: 'policy-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/policy-details/policy-details-delete-dialog.html',
                    controller: 'Policy_detailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Policy_details', function(Policy_details) {
                            return Policy_details.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('policy-details', null, { reload: 'policy-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
