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

    OrderManagementController.$inject = ['webservice', '$rootScope', '$state', '$window', '$cookies'];

    function OrderManagementController(webservice, $rootScope, $state, $window, $cookies) {
        var vm = this;
        $rootScope.appURL = "http://localhost:8080";
        $rootScope.baseURL = "http://localhost:8080/rest";

        //variables

        vm.initCategoriesList = initCategoriesList;
        vm.addToTable = addToTable;
        vm.addOpenOrderToTable = addOpenOrderToTable;
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
        vm.printOrder = printOrder;
        vm.placeKOT = placeKOT;
        vm.openPaymentMethodModal = openPaymentMethodModal;
        vm.openOrderModel = openOrderModal;
        vm.setSelectedPaymentMethod = setSelectedPaymentMethod;
        vm.selectCategory = selectCategory;

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
        vm.paymentMethod = "Cash";

        vm.userName = $cookies.get('userName');

        vm.openOrder = false;

        vm.selectedCategoryItemList = [];

        initCategoriesList();
        setPendingOrderCount();
        setServedOrderCount();
        setKOTNumber();
        loadKitchen();
        loadTables();
        loadTaxSettings();
        loadSChargeSettings();

        function selectCategory(category) {
            vm.selectedCategoryItemList = category.itemResourceList;
        }

        /** This function will get all the categories and their items */
        function initCategoriesList() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get").then(function (response) {
                vm.categoriesList = response.data.dataRows;
                console.log(vm.categoriesList);
            });
        }

        function loadTaxSettings() {
            webservice.call($rootScope.baseURL + "/admin/tax", "get").then(function (response) {
                vm.tax = response.data;
            });
        }

        function loadSChargeSettings() {
            webservice.call($rootScope.baseURL + "/admin/s_charge", "get").then(function (response) {
                vm.serviceCharge = response.data;
            });
        }

        function loadKitchen() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/kitchen/all_kitchen", "get", {}).then(function (response) {
                console.log(response.data);
                vm.kitchenList = response.data;

                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
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
                webservice.call($rootScope.baseURL + "/itemrelation/get_child_items_for_item/" + item.itemId, "get").then(function (response) {
                    var menuItem = {
                        "id": item.itemId,
                        "skuCode": item.skuCode,
                        "name": item.name,
                        "price": item.price,
                        "quantity": 1,
                        "amount": item.price,
                        "kitchenId": item.kitchenId
                    };

                    vm.menu.push(menuItem);

                    var childItems = response.data.dataRows;
                    for (var i = 0; i < Object.keys(childItems).length; i++) {
                        var childItem = {
                            "id": childItems[i].itemId,
                            "skuCode": childItems[i].skuCode,
                            "name": childItems[i].name,
                            "price": childItems[i].price,
                            "quantity": 1,
                            "amount": childItems[i].price,
                            "kitchenId": childItems[i].kitchenId
                        };
                        vm.menu.push(childItem);
                    }

                    vm.calculateAmountAndSubTotal();
                });

            } else {
                alert("You have already added this item to the order.");
            }
        }

        function addOpenOrderToTable(newItemName, newUnitPrice, newQuantity, newItemComment, newItemKitchen) {
            if (newItemName != undefined && newQuantity != undefined && newUnitPrice != undefined && newItemKitchen != undefined) {
                var amount = Number(newUnitPrice) * (newQuantity);
                var menuItem = {
                    "id": -1,
                    "skuCode": "OPEN ORDER",
                    "name": newItemName,
                    "price": newUnitPrice,
                    "quantity": newQuantity,
                    "amount": amount,
                    "comment": newItemComment,
                    "kitchenId": newItemKitchen
                };
                vm.menu.push(menuItem);
                vm.calculateAmountAndSubTotal();
            } else {
                alert("Please correctly fill the form");
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
            var checkOpen = false;
            if (!isNaN(vm.subTotal) && (Object.keys(vm.menu).length > 0)) {
                var sendObj = {};
                sendObj.orderTime = new Date();
                sendObj.amount = vm.subTotal;
                sendObj.customerName = vm.customerName;
                sendObj.tableId = vm.tableId;
                sendObj.userId = $cookies.get('userId');
                sendObj.type = vm.type;
                sendObj.comment = vm.comment;
                sendObj.openOrder = false;
                sendObj.voidOrder = false;
                var itemResourceList = [];
                var paymentDetails = {};
                angular.forEach(vm.menu, function (value) {
                    var item = {};
                    item.itemId = value.id;
                    item.kitchenId = value.kitchenId;
                    if (value.id == -1) {
                        checkOpen = true;
                        sendObj.openOrder = true;
                    }
                    item.name = value.name;
                    item.quantity = value.quantity;
                    item.price = value.price;
                    item.comment = value.comment;
                    itemResourceList.push(item);
                });
                paymentDetails.amount = parseFloat(vm.subTotal);
                paymentDetails.tax = parseFloat(vm.tax);
                paymentDetails.serviceCharge = vm.serviceCharge;
                paymentDetails.discount = parseFloat(vm.discount);
                paymentDetails.totalAmount = parseFloat(calculateTotal());
                paymentDetails.date = Math.round((new Date()).getTime() / 1000);
                paymentDetails.type = 0;

                sendObj.paymentDetails1 = paymentDetails;
                sendObj.itemResourceList1 = itemResourceList;
                // console.log(sendObj);


                // If Open Order
                if (checkOpen) {
                    webservice.call($rootScope.baseURL + "/order/open_order", "post", sendObj).then(function (response) {
                        alert("KOT issued with KOT number  " + response.data.kotNumber);
                        $state.go("pending_orders");
                        printBillAndKOT();
                    });
                } else {
                    webservice.call($rootScope.baseURL + "/order", "post", sendObj).then(function (response) {
                        alert("KOT issued with KOT number  " + response.data.kotNumber);
                        $state.go("pending_orders");
                        printBillAndKOT();
                    });
                }
            } else {
                alert("Please correctly fill the details.");
            }
        }


        function printBillAndKOT() {
            //Part 1 - print the cashier bill = Printer 1
                /* Needed details
                 *  KOT number - vm.kotNumber
                 *  Table No - vm.tableId
                 *  Menu Items - vm.menu
                 * */
                // Print command for the cashier bill


            //Part 2 - Print the KOT
            var printer2Items = [];
            var printer3Items = [];
            angular.forEach(vm.menu, function (value) {
                var item = {};
                item.itemId = value.id;
                item.kitchenId = value.kitchenId;
                item.name = value.name;
                item.quantity = value.quantity;
                item.price = value.price;
                item.comment = value.comment;

                // Printer2 = If item kitchen is Night kade or take away then this
                if(value.kitchenId==1 || value.kitchenId==2 ){
                    printer2Items.push(item);

                }else if(value.kitchenId==3 || value.kitchenId==4 ) { // Printer3 =  If item kitchen is In House or Juise bar then this
                    printer3Items.push(item);
                }

            });

            // Now You can print the bill separately

            // Print printer 2 list
            if(Object.keys(printer2Items).length>0){
                // Print command to print items in printer2Items
            }

            // Print printer 3 list
            if(Object.keys(printer3Items).length>0){
                // Print command to print items in printer3Items
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

        function changeType(type) { // dinein = 0; takeaway = 1
            vm.menu = [];

            vm.type = type;
            console.log("type: " + type);
        }

        function setPendingOrderCount() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrderCount = response.data.dataRows.length;
            });
        }

        function setServedOrderCount() {
            webservice.call($rootScope.baseURL + "/order/all_completed_orders", "get").then(function (response) {
                vm.servedOrderCount = response.data.dataRows.length;
            });
        }

        function calculateTotal() {
            return Number(vm.subTotal) + Number(vm.subTotal * (vm.tax / 100)) + Number(vm.subTotal * (vm.serviceCharge / 100)) - Number(vm.discount);
        }

        function calculateBalance() {
            if ((Number(vm.payment) - calculateTotal()) > 0) {
                return Number(vm.payment) - calculateTotal();
            } else {
                return 0;
            }
        }

        function printOrder() {
            var checkOpen = false;
            if (!isNaN(vm.subTotal)) {
                var sendObj = {};
                sendObj.orderTime = new Date();
                sendObj.amount = vm.subTotal;
                sendObj.customerName = vm.customerName;
                sendObj.tableId = vm.tableId;
                sendObj.userId = $cookies.get('userId');
                sendObj.type = vm.type;
                sendObj.comment = vm.comment;
                sendObj.openOrder = false;
                sendObj.voidOrder = false;
                var itemResourceList = [];
                var paymentDetails = {};
                angular.forEach(vm.menu, function (value) {
                    var item = {};
                    item.itemId = value.id;
                    if (value.id == -1) {
                        checkOpen = true;
                        sendObj.openOrder = true;
                    }
                    item.name = value.name;
                    item.quantity = value.quantity;
                    item.price = value.price;
                    itemResourceList.push(item);
                });
                paymentDetails.amount = parseFloat(vm.subTotal);
                paymentDetails.tax = parseFloat(vm.tax);
                paymentDetails.serviceCharge = vm.serviceCharge;
                paymentDetails.discount = parseFloat(vm.discount);
                paymentDetails.totalAmount = parseFloat(calculateTotal());
                paymentDetails.date = Math.round((new Date()).getTime() / 1000);
                paymentDetails.type = 0;

                sendObj.paymentDetails1 = paymentDetails;
                sendObj.itemResourceList1 = itemResourceList;
                // console.log(sendObj);


                // If Open Order
                if (checkOpen) {
                    webservice.call($rootScope.baseURL + "/order/open_order", "post", sendObj).then(function (response) {
                        alert("KOT issued with KOT number  " + response.data.kotNumber);
                        $window.location.reload();
                        var printContents = document.getElementById('printContent').innerHTML;
                        var originalContents = document.body.innerHTML;

                        document.body.innerHTML = printContents;
                        window.print();
                        document.body.innerHTML = originalContents;

                    });
                } else {
                    webservice.call($rootScope.baseURL + "/order", "post", sendObj).then(function (response) {
                        alert("KOT issued with KOT number  " + response.data.kotNumber);
                        var printContents = document.getElementById('printContent').innerHTML;
                        console.log(printContents);
                        // var originalContents = document.body.innerHTML;
                        //
                        // document.body.innerHTML = printContents;
                        // window.print();
                        // document.body.innerHTML = originalContents;

                        qz.websocket.connect().then(function () {
                            console.log("Connected to the qz service.");

                            qz.printers.find("EPSON").then(function (printer) {
                                console.log("Printer with name " + printer + " found.");

                                var config = qz.configs.create(printer);

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
                                    'PAYMENT\t\t\t: ' + vm.payment + '\n',
                                    '- - - - - - - - - - - - - - - - - - - -\n',
                                    'Meepura Restaurant, Negombo\n',
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
                    });
                }
            }
        }

        function placeKOT() {
            if ((Object.keys(vm.menu).length > 0)) {

                vm.openOrder = false;
                angular.forEach(vm.menu, function (value) {
                    if (value.id == -1) {
                        vm.openOrder = true;
                    }
                });
                $("#submitOrderModal").modal();
            } else {
                alert("Please correctly fill the details.");
            }
        }

        function openPaymentMethodModal() {
            $("#paymentMethodModal").modal()
        }

        function openOrderModal() {
            $("#openOrderModal").modal()
        }

        function setSelectedPaymentMethod() {

        }

        function loadTables() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/table/all_tables_available", "get", {}).then(function (response) {
                console.log(response.data);
                vm.tableList = response.data;
                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
            });
        }
    }
})();

