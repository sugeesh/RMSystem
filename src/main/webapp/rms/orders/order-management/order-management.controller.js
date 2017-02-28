/**
 * Created by Buddhi on 2/11/2017.
 */


//  TODO add correct status//
//  TODO add orderId and Comments//
//  TODO itemId  //
//  TODO add to queue use KOT word//
//  TODO Item walata active field 1ka//
//  TODO Item delete kranna beri exception hanlde
//  TODO Commit karala exception handling//


(function () {
    'use strict';
    'use strict';

    angular.module('myApp').controller('OrderManagementController', OrderManagementController);

    OrderManagementController.$inject = ['webservice', '$rootScope', '$state', '$sessionStorage'];

    function OrderManagementController(webservice, $rootScope, $state, $sessionStorage) {
        var vm = this;
        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        //variables

        vm.initCategoriesList = initCategoriesList;
        vm.addToTable = addToTable;
        vm.calculateAmountAndSubTotal = calculateAmountAndSubTotal;
        vm.changeQuantity = changeQuantity;
        vm.saveOrder = saveOrder;
        vm.removeItem = removeItem;
        vm.removeAllItems = removeAllItems;
        vm.addItemByCode = addItemByCode;
        vm.changeType = changeType;
        vm.calculateBalance = calculateBalance;
        vm.calculateTotal = calculateTotal;
        vm.setKOTNumber = setKOTNumber;

        vm.menu = [];
        vm.subTotal = 0;
        vm.customerName = "";
        vm.tableId = 0;
        vm.type = 0;
        vm.comment = "";
        vm.pendingOrderCount = 0;
        vm.servedOrderCount = 0;
        vm.subTotal = 0;
        vm.tax = 0;
        vm.serviceCharge = 0;
        vm.discount = 0;
        vm.payment = 0;
        vm.orderTime = new Date();
        vm.kotNumber = "";

        var user = $sessionStorage.getObject('user');
        console.log(user);
        if(user == ""){
            $state.go('login');
        }

        initCategoriesList();
        setPendingOrderCount();
        setServedOrderCount();
        setKOTNumber();

        /** This function will get all the categories and their items */
        function initCategoriesList() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get").then(function (response) {
                vm.categoriesList = response.data.dataRows;
                console.log(response.data.dataRows[0].categoryId);

            });

        }

        function setKOTNumber() {
            webservice.call($rootScope.baseURL + "/order/get_next_kot", "get").then(function (response) {
              vm.kotNumber = response.data.kotNumber;
            });

        }

        function hasItem(itemId) {
            for (var i = 0; i < Object.keys(vm.categoriesList).length; i++) {
                for (var j = 0; j < Object.keys(vm.categoriesList[i].itemResourceList).length; j++) {
                    if (vm.categoriesList[i].itemResourceList[j].skuCode.toString() == itemId) {
                        return vm.categoriesList[i].itemResourceList[j];
                    }
                }
            }
            return null;
        }

        function addItemByCode(itemId) {
            var itemByCode = hasItem(itemId);

            if (itemByCode != null) {
                addToTable(itemByCode);
            } else {
                alert('No item for this code.');
            }
        }

        function addToTable(item) {
            // check if the item exist
            var checker = false;
            var len = vm.menu.length;
            for (var i = 0; i < len; i++) {
                if (vm.menu[i].id == item.itemId) {
                    checker = true;
                    break;
                }
            }
            if (!checker) {
                var menuItem = {
                    "id": item.itemId,
                    "skuCode": item.skuCode,
                    "name": item.name,
                    "price": item.price,
                    "quantity": 1,
                    "amount": item.price
                };
                vm.menu.push(menuItem);
                vm.calculateAmountAndSubTotal();
            } else {
                alert("You have already added this item to the order.");
            }
        }

        function calculateAmountAndSubTotal() {
            var subTotal = 0;
            angular.forEach(vm.menu, function (value) {
                value.amount = value.price * value.quantity;
                subTotal += value.amount;
            });
            vm.subTotal = subTotal;
        }

        function changeQuantity() {
            calculateAmountAndSubTotal();
        }

        function saveOrder() {
            if (!isNaN(vm.subTotal)) {
                var sendObj = {};
                sendObj.orderTime = new Date();
                sendObj.amount = vm.subTotal;
                sendObj.customerName = vm.customerName;
                sendObj.tableId = vm.tableId;
                sendObj.type = vm.type;
                sendObj.comment = vm.comment;
                var itemResourceList = [];
                angular.forEach(vm.menu, function (value) {
                    var item = {};
                    item.itemId = value.id;
                    item.name = value.name;
                    item.quantity = value.quantity;
                    item.price = value.price;
                    itemResourceList.push(item);
                });
                sendObj.itemResourceList1 = itemResourceList;
                // console.log(sendObj);
                webservice.call($rootScope.baseURL + "/order", "post", sendObj).then(function (response) {
                    alert("KOT issued with KOT number  " + response.data.kotNumber);
                    $state.go("pending_orders");
                });
            }
        }

        function removeItem(item) {
            var len = vm.menu.length;
            for (var i = len - 1; i >= 0; i--) {
                if (vm.menu[i].id == item.id) {
                    vm.menu.splice(i, 1);
                    break;
                }
            }
            calculateAmountAndSubTotal();
        }

        function removeAllItems() {
            vm.menu = [];
            calculateAmountAndSubTotal();
        }

        function changeType(type) {
            vm.type = type;
        }

        function setPendingOrderCount() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrderCount = response.data.dataRows.length;
            });
        }

        function setServedOrderCount() {
            webservice.call($rootScope.baseURL + "/order/all_served_orders", "get").then(function (response) {
                vm.servedOrderCount = response.data.dataRows.length;
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


    }

})();

