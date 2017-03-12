/**
 * Created by Buddhi on 2/13/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['webservice', '$rootScope', '$state', '$interval'];

    function DashboardController(webservice, $rootScope, $state, $interval) {
        var vm = this;

        var colors = ['#97BBCD', '#DCDCDC', '#F7464A', '#46BFBD', '#FDB45C', '#949FB1', '#4D5360'];

        vm.orderDetailLabels = ["Dine in", "Take away"];
        vm.orderDetailData = [3, 5];

        vm.bestDealsLabels = ["Mongolian Nasigoreng", "Cream Soda 300ml", "Chicken Fried Rice", "Chicken Mixed Rice"];
        vm.bestDealsData = [1, 5, 3, 6];

        setDateTime();
        loadOrderDetails();

        function loadOrderDetails() {
            var today = new Date("yyyy-MM-dd");

            webservice.call($rootScope.baseURL + "/order/get_orders_for_date_range/" + today + "/" + today + "/2", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

        function setDateTime() {
            var tick = function () {
                vm.time = Date.now();
            };
            tick();
            $interval(tick, 1000);
        }
    }
})();

