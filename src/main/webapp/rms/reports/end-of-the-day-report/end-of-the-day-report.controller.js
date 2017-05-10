/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('EndOfTheDayReportController', EndOfTheDayReportController);

    EndOfTheDayReportController.$inject = ['webservice', '$rootScope'];

    function EndOfTheDayReportController(webservice, $rootScope) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.getOrdersForDay = getOrdersForDay;

        vm.dineInCount = 0;
        vm.takeAwayCount = 0;
        vm.voidCount = 0;
        vm.ordersAmount = 0;
        vm.totalAmount = 0;

        getOrdersForDay();

        function getOrdersForDay() {
            var today = $('#reservation').data('daterangepicker').startDate.format("YYYY-MM-DD");

            console.log("today: " + today);
            webservice.call($rootScope.baseURL + "/order/get_all_orders_for_date/" + today, "get").then(function (response) {
                var orders = response.data.dataRows;
                vm.orderCount = response.data.entries;
                console.log(response);

                var diCount = 0;
                var taCount = 0;
                var vCount = 0;
                var oAmount = 0;
                var tAmount = 0;

                angular.forEach(orders, function (order, key) {
                    if (order.state != "VOIDED" && order.type == 0) {
                        diCount++;
                    } else if (order.state != "VOIDED" && order.type == 1) {
                        taCount++;
                    }
                    if (order.state == "VOIDED") {
                        vCount++;
                    }

                    var payment = order.paymentDetails;
                    oAmount += payment.amount;
                    tAmount += payment.totalAmount;
                });

                vm.dineInCount = diCount;
                vm.takeAwayCount = taCount;
                vm.voidCount = vCount;
                vm.ordersAmount = oAmount;
                vm.totalAmount = tAmount;
            });
        }
    }
})();