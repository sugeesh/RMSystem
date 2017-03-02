/**
 * Created by Buddhi on 2/14/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrderDetailController', OrderDetailController);

    OrderDetailController.$inject = ['webservice', '$stateParams', '$rootScope', '$state'];

    function OrderDetailController(webservice, $stateParams, $rootScope, $state) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";
        vm.printOrder = printOrder;

        vm.menu = [];
        vm.subTotal = 0;
        vm.customerName = "";
        vm.tableId = 0;
        vm.type = 0;
        vm.comment = "";
        vm.kotNumber;
        vm.tax = 0;
        vm.serviceCharge = 0;
        vm.payment = 0
        vm.discount = 0;


        initOrderTable($stateParams.orderId);

        function initOrderTable(orderId) {
            webservice.call($rootScope.baseURL + "/order/get_order_from_id/" + orderId, "get").then(function (response) {

                vm.backendData = response.data;
                vm.tableId = vm.backendData.tableId;
                vm.customerName = vm.backendData.customerName;
                vm.subTotal = vm.backendData.amount;
                vm.orderId = vm.backendData.orderId;
                vm.orderTime = vm.backendData.orderTime;
                vm.comment = vm.backendData.comment;
                vm.type = vm.backendData.type;
                vm.kotNumber = vm.backendData.kotNumber;

                angular.forEach(vm.backendData.itemResourceList, function (item) {
                    var menuItem = {
                        "id": item.itemId,
                        "skuCode": item.skuCode,
                        "name": item.name,
                        "price": item.price,
                        "quantity": item.quantity,
                        "amount": item.price * item.quantity
                    };
                    vm.menu.push(menuItem);
                });

                webservice.call($rootScope.baseURL + "/payment/get_payment_from_order/" + orderId, "get").then(function (response) {
                    vm.subTotal = response.data.amount;
                    vm.tax = response.data.tax;
                    vm.serviceCharge = response.data.serviceCharge;
                    vm.discount = response.data.discount;
                    vm.total = response.data.totalAmount;
                });

            });
        }

        function printOrder() {
            var printContents = document.getElementById('printContent').innerHTML;
            var originalContents = document.body.innerHTML;

            document.body.innerHTML = printContents;
            window.print();
            document.body.innerHTML = originalContents;
        }


    }
})();

