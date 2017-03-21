/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('PendingOrdersController', PendingOrdersController);

    PendingOrdersController.$inject = ['webservice', '$rootScope', '$state'];

    function PendingOrdersController(webservice, $rootScope, $state) {
        var vm = this;
        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;
        vm.voidOrder = voidOrder;
        vm.voidOrderModal = voidOrderModal;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.orderList = [];
        vm.voidkotNumber = 0;
        vm.voidReason = "";

        initOrderList();

        /** This function will get all the pending orders */
        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrders = response.data.dataRows;
                vm.pendingOrderCount = response.data.entries;
            });
        }

        function routeToOrder(orderId) {
            $state.go("order_detail", {'orderId': orderId});
        }

        function voidOrderModal(orderId) {
            vm.voidkotNumber = orderId;
            $("#voidOrderModal").modal();
        }


        function voidOrder() {
            // console.log(vm.voidReason);
            if (vm.voidReason != '') {
                var sendObj = {
                    "orderId": vm.voidkotNumber,
                    "voidOrder": true,
                    "state": "WAITING",
                    "comment": vm.voidReason
                };

                webservice.call($rootScope.baseURL + "/order/update_void_order/", "put", sendObj).then(function (response) {
                    $state.go("waiting_orders");
                    alert("Order Voided.");
                });
            } else {
                alert("Please enter a message before voiding the order.");
            }
        }


    }


})();

