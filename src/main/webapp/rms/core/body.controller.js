/**
 * Created by Buddhi on 2/27/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('BodyController', BodyController);

    BodyController.$inject = ['$state', '$rootScope', 'webservice','$cookies'];

    function BodyController($state, $rootScope, webservice,$cookies) {
        var vm = this;
        vm.userType = $cookies.get('userType');

        vm.logOut = logOut;

        console.log("Buddhi"+vm.userType);


        function logOut() {
            $cookies.remove('userType');
            $state.go('login');
        }
    }




})();

