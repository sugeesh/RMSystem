/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('AdminWaitingOrdersController', AdminWaitingOrdersController);

    AdminWaitingOrdersController.$inject = ['webservice','$rootScope','$state'];

    function AdminWaitingOrdersController(webservice,$rootScope,$state) {
        var vm = this;
        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;
        vm.voidOrder = voidOrder;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.orderList = [];


        initOrderList();

        /** This function will get all the waiting orders */
        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_waiting_orders", "get").then(function (response) {
                vm.pendingOrders = response.data.dataRows;
                vm.pendingOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }
        
        function routeToOrder(orderId) {
            $state.go("admin_order_detail",{ 'orderId' : orderId });
        }


        function voidOrder(orderId) {
            var sendObj = {"orderId":orderId,"voidOrder":true,"state":"WAITING"};

            webservice.call($rootScope.baseURL + "/order/update_void_order/", "put",sendObj).then(function (response) {
                $state.go("admin_waiting_orders");
                alert("Order Voided.");
            });
        }
    }



})();

