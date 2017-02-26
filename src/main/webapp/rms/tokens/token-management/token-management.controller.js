/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TokenManagementController', TokenManagementController);

    TokenManagementController.$inject = ['webservice', '$rootScope', '$state', '$sessionStorage', '$interval'];

    function TokenManagementController(webservice, $rootScope, $state, $sessionStorage, $interval) {
        var vm = this;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.routeToToken = routeToToken;

        var user = $sessionStorage.getObject('user');
        console.log(user);
        if (user == "") {
            $state.go('login');
        }

        var tick = function () {
            initOrderList();
        };
        tick();
        $interval(tick, 1000 * 60);

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingTokens = response.data.dataRows;

                var now = new Date();

                for (var i = 0; i < Object.keys(vm.pendingTokens).length; i++) {
                    var m = new Date(vm.pendingTokens[i].orderTime);
                    var diff = now - m;
                    var minutes = Math.floor((diff / 1000) / 60);
                    vm.pendingTokens[i].remainingTime = minutes;
                }
                console.log(vm.pendingTokens);
            });
        }

        function routeToToken(token) {
            $state.go("token_details", {'tokenId': token.orderId});
        }
    }
})();