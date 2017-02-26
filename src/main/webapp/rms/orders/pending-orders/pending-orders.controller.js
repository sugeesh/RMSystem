/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('PendingOrdersController', PendingOrdersController);

    PendingOrdersController.$inject = ['webservice','$rootScope','$state', '$sessionStorage'];

    function PendingOrdersController(webservice,$rootScope,$state, $sessionStorage) {
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
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrders = response.data.dataRows;
                vm.pendingOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }
        
        function routeToOrder(orderId) {
            $state.go("order_update",{ 'orderId' : orderId });
        }
    }



})();

