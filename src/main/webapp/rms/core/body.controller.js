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
        vm.routeToKitchen = routeToKitchen;

        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.userType = $cookies.get('userType');
        console.log("Buddhi " + vm.userType);

        // if(vm.userType=="KITCHEN"){
            loadKitchen();
        // }

        if (vm.userType == undefined) {
            $state.go('login');
        }

        function logOut() {
            $cookies.remove('userType');
            $state.go('login');
        }

        function loadKitchen() {
            webservice.call($rootScope.baseURL + "/kitchen/all_kitchen", "get", {}).then(function (response) {
                console.log(response.data);
                vm.kitchenList = response.data;
            }).catch(function () {

            });
        }

        function routeToKitchen(kitchenId) {
            $state.go("token_management", {'kId': kitchenId});
        }
    }


})();

