/**
 * Created by Buddhi on 2/11/2017.
 */

(function () {
    'use strict';

    angular.module('myApp').controller('OrderUpdateController', OrderUpdateController);

    OrderUpdateController.$inject = ['webservice', '$rootScope', '$state', '$stateParams'];

    function OrderUpdateController(webservice, $rootScope, $state, $stateParams) {
        var vm = this;
        // console.log("World most important finding : " + $stateParams.orderId);
        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        //variables

        vm.initCategoriesList = initCategoriesList;
        vm.addToTable = addToTable;
        vm.calculateAmountAndSubTotal = calculateAmountAndSubTotal;
        vm.changeQuantity = changeQuantity;
        vm.updateOrder = updateOrder;
        vm.removeItem = removeItem;
        vm.removeAllItems = removeAllItems;
        vm.addItemByCode = addItemByCode;
        vm.changeType = changeType;
        vm.setPendingOrderCount = setPendingOrderCount;
        vm.setServedOrderCount = setServedOrderCount;

        vm.menu = [];
        vm.subTotal = 0;
        vm.customerName = "";
        vm.tableId = 0;
        vm.type = 0;
        vm.comment = "";
        vm.kotNumber;
        vm.pendingOrderCount = 0;
        vm.servedOrderCount = 0;

        initCategoriesList();
        setPendingOrderCount();
        setServedOrderCount();
        initOrderTable($stateParams.orderId);

        /** This function will get all the categories and their items */
        function initCategoriesList() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get").then(function (response) {

                vm.categoriesList = response.data.dataRows;
                console.log(response.data.dataRows[0].categoryId);

            });

        }

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

                console.log("BE" + vm.backendData);
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
                calculateAmountAndSubTotal();

            });
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

        function updateOrder() {
            if (!isNaN(vm.subTotal)) {
                var sendObj = {};
                sendObj.orderTime = vm.orderTime;
                sendObj.amount = vm.subTotal;
                sendObj.customerName = vm.customerName;
                sendObj.tableId = vm.tableId;
                sendObj.orderId = vm.orderId;
                sendObj.comment = vm.comment;
                sendObj.type = vm.type;
                sendObj.kotNumber = vm.kotNumber;

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
                console.log(sendObj);

                webservice.call($rootScope.baseURL + "/order/update_order/" + $stateParams.orderId, "put", sendObj).then(function (response) {
                    alert("KOT " + response.data.kotNumber + " Updated.");
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

        function addItemByCode(itemId) {
            var itemByCode = hasItem(itemId);

            if (itemByCode != null) {
                addToTable(itemByCode);
            } else {
                alert('No item for this code.');
            }
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

    }

})();

