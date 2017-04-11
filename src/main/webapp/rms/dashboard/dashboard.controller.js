/**
 * Created by Buddhi on 2/13/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['webservice', '$rootScope', '$state', '$interval','$cookies'];

    function DashboardController(webservice, $rootScope, $state, $interval,$cookies) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        var colors = ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'];

        vm.orderDetailLabels = ["Dine in", "Take away"];
        vm.orderDetailData = [0, 0];

        vm.bestDeals = [];
        vm.bestDealsLabels = [];
        vm.bestDealsData = [];
        vm.addKitchen = addKitchen;

        setDateTime();
        loadKitchen();
        loadOrderDetails();


        function loadKitchen(){
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/kitchen/all_kitchen", "get", {}).then(function (response) {
                console.log(response.data);
                vm.kitchenList = response.data.dataRows;

                $rootScope.isLoading = false;
            }).catch(function(){
                $rootScope.isLoading = false;
            });
        }

        function loadOrderDetails() {
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1; //January is 0!
            var yyyy = today.getFullYear();
            if (mm < 10) {
                mm = '0' + mm;
            }
            if (dd < 10) {
                dd = '0' + dd;
            }
            var today = yyyy + "-" + mm + "-" + dd;

            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/order/get_all_orders_for_date/" + today, "get").then(function (response) {
                var orders = response.data.dataRows;
                console.log(orders);

                var diCount = 0;
                var taCount = 0;
                var itemList = [];
                angular.forEach(orders, function (order, key) {
                    if (order.type == 0) {
                        diCount++;
                    } else if (order.type == 1) {
                        taCount++;
                    }

                    angular.forEach(order.itemResourceList, function (item, key) {
                        if (itemList.length == 0) {
                            itemList.push({
                                id: item.itemId,
                                name: item.name,
                                quantity: item.quantity
                            });
                        } else {
                            for (var i = 0; i < Object.keys(itemList).length; i++) {
                                if (itemList[i].id == item.itemId) {
                                    itemList[i].quantity += item.quantity;
                                    continue;
                                }
                                if (i == Object.keys(itemList).length - 1) {
                                    itemList.push({
                                        id: item.itemId,
                                        name: item.name,
                                        quantity: item.quantity
                                    });
                                }
                            }
                        }
                    });
                });
                vm.orderDetailData[0] = diCount;
                vm.orderDetailData[1] = taCount;

                if (itemList.length > 0) {
                    for (var j = 0; j < 7; j++) {
                        vm.bestDeals.push({
                            name: itemList[j].name,
                            color: colors[j]
                        });
                        vm.bestDealsLabels.push(itemList[j].name);
                        vm.bestDealsData.push(itemList[j].quantity);
                    }
                } else {
                    vm.noData = true;
                }

                $rootScope.isLoading = false;
            }).catch(function (error) {
                $rootScope.isLoading = false;
            });
        }

        function setDateTime() {
            var tick = function () {
                vm.time = Date.now();
            };
            tick();
            $interval(tick, 1000);
        }

        function addKitchen(kitchenId) {
            $cookies.put('kitchenType', kitchenId);
        }
    }
})();

