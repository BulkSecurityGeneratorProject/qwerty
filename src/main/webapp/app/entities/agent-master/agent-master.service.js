(function() {
    'use strict';
    angular
        .module('demoApp')
        .factory('Agent_master', Agent_master);

    Agent_master.$inject = ['$resource'];

    function Agent_master ($resource) {
        var resourceUrl =  'api/agent-masters/:id';

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
