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
            qz.websocket.connect().then(function () {
                console.log("Connected to the qz service.");
                var today = new Date();
                var dd = today.getDate();
                var mm = today.getMonth() + 1; //January is 0!
                var yyyy = today.getFullYear();
                if (dd < 10) {
                    dd = '0' + dd
                }
                if (mm < 10) {
                    mm = '0' + mm
                }
                today = mm + '/' + dd + '/' + yyyy;

                qz.printers.find("EPSON").then(function (printer) {
                    console.log("Printer with name " + printer + " found.");

                    var config = qz.configs.create(printer);

                    var menuText = '';
                    angular.forEach(vm.menu, function (value) {
                        menuText += value.name + '\t' + value.quantity + '\t' + value.amount;
                    });

                    var data = [
                        '\n',
                        '\n',
                        '\n',
                        'DATE: ' + today + '\n',
                        'TABLE NO.: ' + vm.tableId + '\n',
                        'KOT NO.: ' + vm.kotNumber + '\n',
                        '- - - - - - - - - - - - - - - - - - - -\n',
                        'NAME\t\tQTY\tAMOUNT\n',
                        '- - - - - - - - - - - - - - - - - - - -\n',
                        menuText + '\n',
                        '- - - - - - - - - - - - - - - - - - - -\n',
                        'TOTAL\t\t\t:' + vm.subTotal + '\n',
                        'DISCOUNT\t\t:' + vm.discount + '\n',
                        'TAX\t\t\t: ' + vm.tax + '\n',
                        'SERVICE CHARRGES\t: ' + vm.serviceCharge + '\n',
                        'TOTAL\t\t\t: ' + vm.total + '\n',
                        '- - - - - - - - - - - - - - - - - - - -\n',
                        'Meepura Chef, No.340, Colombo Road, Negombo\n',
                        'TEL: 0114238238\n\n',
                        'THANK YOU, COME AGAIN\n',
                        '\n',
                        '\n',
                        '\n',
                        '\n',
                        '\n',
                        '\n',
                        '\n'];

                    qz.print(config, data).then(function (response) {
                        console.log(response);
                        console.log("Print Command Issued!");
                        $window.location.reload();
                    }).catch(function (e) {
                        console.error(e);
                    });
                });
            });
        }
    }
})();

