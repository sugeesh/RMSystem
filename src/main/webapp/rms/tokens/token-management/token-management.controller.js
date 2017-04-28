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

        vm.oldCount = 0;

        var tick = function () {
            initOrderList();
        };
        tick();
        $interval(tick, 1000 * 60);

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders/" + $stateParams.kId, "get").then(function (response) {
                vm.pendingTokens = response.data.dataRows;

                var now = new Date();

                for (var i = 0; i < Object.keys(vm.pendingTokens).length; i++) {
                    var m = new Date(vm.pendingTokens[i].orderTime);
                    var diff = now - m;
                    var minutes = Math.floor((diff / 1000) / 60);
                    vm.pendingTokens[i].remainingTime = minutes;
                }

                if (vm.oldCount < Object.keys(vm.pendingTokens).length) {
                    qz.websocket.connect().then(function () {
                        console.log("Connected to the qz service.");
                        qz.printers.find("EPSON").then(function (printer) {
                            console.log("Printer with name " + printer + " found.");

                            var config = qz.configs.create(printer);

                            console.log("gggggggggg");
                            console.log(vm.pendingTokens[i]);
                            console.log("gggggggggg");

                            var today = new Date();
                            var dd = today.getDate();
                            var mm = today.getMonth() + 1; //January is 0!
                            var yyyy = today.getFullYear();
                            if (dd < 10) {
                                dd = '0' + dd
                            }
                            if (mm < 10) {
                                mm = '0' + mm
                            }
                            today = mm + '/' + dd + '/' + yyyy;

                            var menuText = '';
                            // angular.forEach(vm.pendingTokens, function (value) {
                            //     menuText += value.name + '\t' + value.quantity + '\t' + value.amount;
                            // });

                            var data = [
                                '\n',
                                '\n',
                                '\n',
                                'DATE: ' + today + '\n',
                                'TABLE NO.: ' + vm.pendingTokens[i].tableId + '\n',
                                'KOT NO.: ' + vm.pendingTokens[i].kotNumber + '\n',
                                '- - - - - - - - - - - - - - - - - - - -\n',
                                'NAME\t\tQTY\tAMOUNT\n',
                                '- - - - - - - - - - - - - - - - - - - -\n',
                                menuText + '\n',
                                '- - - - - - - - - - - - - - - - - - - -\n',
                                'TOTAL\t\t\t:' + vm.pendingTokens[i].amount + '\n',
                                '- - - - - - - - - - - - - - - - - - - -\n',
                                'Meepura Restaurant, Negombo\n',
                                'THANK YOU, COME AGAIN\n',
                                '\n',
                                '\n',
                                '\n',
                                '\n',
                                '\n',
                                '\n',
                                '\n'];

                            qz.print(config, data).then(function (response) {
                                console.log(response);
                                console.log("Print Command Issued!");
                            }).catch(function (e) {
                                console.error(e);
                            });
                            vm.oldCount = Object.keys(vm.pendingTokens).length;
                        });
                    });


                }
                console.log(vm.pendingTokens);
            });
        }

        function routeToToken(token) {
            $state.go("token_details", {'tokenId': token.orderId, 'kId': $stateParams.kId});
        }
    }
})();