/**
 * Created by Buddhi on 2/27/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('BodyController', BodyController);

    BodyController.$inject = ['$state', '$rootScope', 'webservice', '$cookies'];

    function BodyController($state, $rootScope, webservice, $cookies) {
        var vm = this;
        vm.logOut = logOut;

        vm.userType = $cookies.get('userType');
        console.log("Buddhi " + vm.userType);

        if (vm.userType == undefined) {
            $state.go('login');
        }else{
            $state.go('dashboard');
        }

        function logOut() {
            $cookies.remove('userType');
            $state.go('login');
        }
    }


})();

