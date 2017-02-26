/**
 * Created by Buddhi on 2/13/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('NotFoundController', NotFoundController);

    NotFoundController.$inject = ['$rootScope', '$state', 'localStorageService'];

    function NotFoundController($rootScope, $state, localStorageService) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        var user = localStorageService.get("user");
        if(user == ""){
            $state.go('login');
        }
    }
})();
