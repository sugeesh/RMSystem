/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TokenManagementController', TokenManagementController);

    TokenManagementController.$inject = ['webservice', '$stateParams', '$rootScope', '$state', '$interval'];

    function TokenManagementController(webservice, $stateParams, $rootScope, $state, $interval) {
        var vm = this;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.routeToToken = routeToToken;

        var tick = function () {
            initOrderList();
        };
        tick();
        $interval(tick, 1000 * 60);

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders/"+$stateParams.kId, "get").then(function (response) {
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
            $state.go("token_details", {'tokenId': token.orderId,'kId': $stateParams.kId});
        }
    }
})();