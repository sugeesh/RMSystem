/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('PendingOrdersController', PendingOrdersController);

    PendingOrdersController.$inject = ['webservice','$rootScope','$state'];

    function PendingOrdersController(webservice,$rootScope,$state) {
        var vm = this;
        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.orderList = [];

<<<<<<< .mine=======
>>>>>>> .theirs        initOrderList();

        /** This function will get all the pending orders */
        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrders = response.data.dataRows;
                vm.pendingOrderCount = response.data.entries;
            });
        }
        
        function routeToOrder(orderId) {
            $state.go("order_update",{ 'orderId' : orderId });
        }
    }



})();

