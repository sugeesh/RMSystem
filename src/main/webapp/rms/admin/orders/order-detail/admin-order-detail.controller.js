/**
 * Created by Buddhi on 2/14/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('AdminOrderDetailController', AdminOrderDetailController);

    AdminOrderDetailController.$inject = ['webservice', '$stateParams', '$rootScope', '$state', '$scope'];

    function AdminOrderDetailController(webservice, $stateParams, $rootScope, $state, $scope) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";
        vm.printOrder = printOrder;
        vm.approveOrder = approveOrder;
        vm.addItemsModal = addItemsModal;
        vm.addItem = addItem;

        vm.menu = [];
        vm.subTotal = 0;
        vm.customerName = "";
        vm.tableId = 0;
        vm.type = 0;
        vm.comment = "";
        vm.kotNumber = 0;
        vm.tax = 0;
        vm.serviceCharge = 0;
        vm.payment = 0;
        vm.discount = 0;
        vm.voidOrder =false ;
        vm.openOrder = false;

        // For the add Item
        vm.newItemName = "";
        vm.newItemPrice = 0;
        vm.categoryList = [];


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
                vm.isVoid = vm.backendData.voidOrder;
                vm.kotNumber = vm.backendData.kotNumber;
                vm.type = vm.backendData.type;
                vm.kotNumber = vm.backendData.kotNumber;
                vm.voidOrder = vm.backendData.voidOrder;
                vm.openOrder = vm.backendData.openOrder;


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

        function approveOrder() {
            var sendObj = {"orderId":vm.orderId,"voidOrder":vm.voidOrder,"openOrder":vm.openOrder,"state":"PENDING"};

            webservice.call($rootScope.baseURL + "/order/approve_waiting_order/", "put",sendObj).then(function (response) {
                $state.go("admin_waiting_orders");
                alert("Order Approved.");
            });
        }

        function addItemsModal(menuItem){
            vm.newItemName = menuItem.name;
            vm.newItemPrice = menuItem.price;
            loadCategories();
            $("#addItemModal").modal();
        }

        function loadCategories() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get", {}).then(function (response) {
                vm.categoryList = response.data.dataRows;
            });
        }


        function addItem(newItemName, newItemCategory, newItemPortion, newItemPrice, newItemSKUCode, newItemTAXCode, newItemComment) {

            if (newItemName != undefined && newItemCategory != undefined && newItemPrice != undefined) {
                var newItem = {
                    name: newItemName,
                    portion: newItemPortion,
                    categoryId: newItemCategory,
                    price: newItemPrice,
                    skuCode: newItemSKUCode,
                    taxCode: newItemTAXCode,
                    comment: newItemComment
                };

                webservice.call($rootScope.baseURL + "/item/save_item", "post", newItem).then(function (response) {
                    alert("Item Saved.");
                    vm.menu = [];

                    for (var i = 0; i < Object.keys(vm.backendData.itemResourceList).length; i++) {
                        var item = vm.backendData.itemResourceList[i];
                        if(item.itemId == -1){
                            item.itemId = -2;
                        }
                        var menuItem = {
                            "id": item.itemId,
                            "skuCode": item.skuCode,
                            "name": item.name,
                            "price": item.price,
                            "quantity": item.quantity,
                            "amount": item.price * item.quantity
                        };
                        vm.menu.push(menuItem);
                    }
                });
            } else {
                alert("Please correctly fill the form");
            }
        }
    }
})();

