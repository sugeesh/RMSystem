/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrdersHistoryController', OrdersHistoryController);

    OrdersHistoryController.$inject = ['webservice', '$rootScope', '$state', '$sessionStorage'];

    function OrdersHistoryController(webservice, $rootScope, $state, $sessionStorage) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.initOrderList = initOrderList;

        var user = $sessionStorage.getObject('user');
        console.log(user);
        if(user == ""){
            $state.go('login');
        }

        initOrderList();

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_completed_orders", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

    }
})();
