/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('AdminOpenOrdersController', AdminOpenOrdersController);

    AdminOpenOrdersController.$inject = ['webservice','$rootScope','$state'];

    function AdminOpenOrdersController(webservice,$rootScope,$state) {
        var vm = this;
        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.orderList = [];


        initOrderList();

        /** This function will get all the waiting orders */
        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_open_orders", "get").then(function (response) {
                vm.openOrders = response.data.dataRows;
                vm.openOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }
        
        function routeToOrder(orderId) {
            $state.go("admin_order_detail",{ 'orderId' : orderId });
        }


    }



})();

