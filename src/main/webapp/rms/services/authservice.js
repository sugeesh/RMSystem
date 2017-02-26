/**
 * Created by Buddhi on 2/20/2017.
 */

(function () {
    'use strict';

    angular.module('myApp.services', [])
        .service('authservice', authservice);

    authservice.$inject = ['$http', '$q', 'webservice'];

    function authservice($http, $q, webservice) {
        var service = {
            getSession: getSession
        };
        return service;

        function getSession() {

        }
    }
})();
