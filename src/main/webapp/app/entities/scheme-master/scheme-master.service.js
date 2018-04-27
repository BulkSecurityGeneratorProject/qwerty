(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Scheme_master', Scheme_master);

    Scheme_master.$inject = ['$resource'];

    function Scheme_master ($resource) {
        var resourceUrl =  'api/scheme-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
