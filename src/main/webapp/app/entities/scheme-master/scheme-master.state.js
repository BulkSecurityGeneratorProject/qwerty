(function() {
    'use strict';

    angular
        .module('demoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scheme-master', {
            parent: 'entity',
            url: '/scheme-master',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.scheme_master.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheme-master/scheme-masters.html',
                    controller: 'Scheme_masterController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scheme_master');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scheme-master-detail', {
            parent: 'scheme-master',
            url: '/scheme-master/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'demoApp.scheme_master.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scheme-master/scheme-master-detail.html',
                    controller: 'Scheme_masterDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scheme_master');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Scheme_master', function($stateParams, Scheme_master) {
                    return Scheme_master.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'scheme-master',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('scheme-master-detail.edit', {
            parent: 'scheme-master-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheme-master/scheme-master-dialog.html',
                    controller: 'Scheme_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scheme_master', function(Scheme_master) {
                            return Scheme_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheme-master.new', {
            parent: 'scheme-master',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheme-master/scheme-master-dialog.html',
                    controller: 'Scheme_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                commision: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('scheme-master', null, { reload: 'scheme-master' });
                }, function() {
                    $state.go('scheme-master');
                });
            }]
        })
        .state('scheme-master.edit', {
            parent: 'scheme-master',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheme-master/scheme-master-dialog.html',
                    controller: 'Scheme_masterDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scheme_master', function(Scheme_master) {
                            return Scheme_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheme-master', null, { reload: 'scheme-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scheme-master.delete', {
            parent: 'scheme-master',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scheme-master/scheme-master-delete-dialog.html',
                    controller: 'Scheme_masterDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scheme_master', function(Scheme_master) {
                            return Scheme_master.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('scheme-master', null, { reload: 'scheme-master' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
