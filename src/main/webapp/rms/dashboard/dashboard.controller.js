/**
 * Created by Buddhi on 2/13/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['webservice', '$rootScope', '$state', 'localStorageService', '$sessionStorage', '$interval'];

    function DashboardController(webservice, $rootScope, $state, localStorageService, $sessionStorage, $interval) {
        var vm = this;

        // var user = localStorageService.get("user");
        var user = $sessionStorage.getObject('user');
        console.log(user);
        if(user == ""){
            $state.go('login');
        }

        setDateTime();

        function setDateTime() {
            var tick = function() {
                vm.time = Date.now();
            };
            tick();
            $interval(tick, 1000);
        }
    }
})();

