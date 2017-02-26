/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('ServedOrdersController', ServedOrdersController);

    ServedOrdersController.$inject = ['webservice','$rootScope','$state', '$sessionStorage'];

    function ServedOrdersController(webservice,$rootScope,$state, $sessionStorage) {
        var vm = this;
        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;

        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.orderList = [];

        var user = $sessionStorage.getObject('user');
        console.log(user);
        if(user == ""){
            $state.go('login');
        }

        initOrderList();

        /** This function will get all the pending orders */
        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_served_orders", "get").then(function (response) {
                vm.servedOrders = response.data.dataRows;
                vm.servedOrderCount = response.data.entries;
            });
        }

        function routeToOrder(orderId) {
            $state.go("order_payment",{ 'orderId' : orderId });
        }
    }



})();

