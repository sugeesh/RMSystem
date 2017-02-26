/**
 * Created by Buddhi on 2/14/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrderPaymentController', OrderPaymentController);

    OrderPaymentController.$inject = ['webservice', '$stateParams', '$rootScope', '$state', '$sessionStorage'];

    function OrderPaymentController(webservice, $stateParams, $rootScope, $state, $sessionStorage) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.calculateTotal = calculateTotal;
        vm.calculateBalance = calculateBalance;
        vm.completeOrder = completeOrder;
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

        var user = $sessionStorage.getObject('user');
        console.log(user);
        if(user == ""){
            $state.go('login');
        }

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

            });
        }

        function calculateTotal() {
            return Number(vm.subTotal) + Number(vm.subTotal*(vm.tax / 100)) + Number(vm.subTotal*(vm.serviceCharge/100)) - Number(vm.discount);
        }

        function calculateBalance() {
            if ((Number(vm.payment) - calculateTotal()) > 0) {
                return Number(vm.payment) - calculateTotal();
            } else {
                return 0;
            }
        }

        function completeOrder() {
            if (!isNaN(vm.subTotal)) {
                var sendObj = {};

                sendObj.date = new Date();
                sendObj.type = 0;
                sendObj.amount = vm.subTotal;
                sendObj.tax = vm.tax;
                sendObj.serviceCharge = vm.serviceCharge;
                sendObj.discount = vm.discount;
                sendObj.orderId = $stateParams.orderId;
                sendObj.totalAmount = calculateTotal();

                webservice.call($rootScope.baseURL + "/payment", "post", sendObj).then(function (response) {
                    alert("Order Completed.");
                    $state.go("pending_orders");
                });
            }
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

