/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrdersHistoryController', OrdersHistoryController);

    OrdersHistoryController.$inject = ['webservice', '$rootScope', '$state'];

    function OrdersHistoryController(webservice, $rootScope, $state) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.initOrderList = initOrderList;
        vm.routeToOrder  = routeToOrder;
        vm.searchOrders = searchOrders;
        vm.changeType = changeType;


        vm.type = 2;

        initOrderList();

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_completed_orders", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

        function routeToOrder(orderId) {
            $state.go("order_detail",{ 'orderId' : orderId });
        }


        function searchOrders() {
            var startDate = $('#reservation').data('daterangepicker').startDate.format("YYYY-MM-DD");
            var endDate = $('#reservation').data('daterangepicker').endDate.format("YYYY-MM-DD");

            webservice.call($rootScope.baseURL + "/order/get_orders_for_date_range/"+startDate+"/"+endDate+"/"+vm.type, "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

        function changeType(type) {
            vm.type = type;
        }
    }
})();
