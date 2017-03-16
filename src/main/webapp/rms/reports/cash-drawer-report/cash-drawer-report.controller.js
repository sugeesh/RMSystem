/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('CashDrawerReportController', CashDrawerReportController);

    CashDrawerReportController.$inject = ['webservice', '$rootScope', '$q'];

    function CashDrawerReportController(webservice, $rootScope, $q) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.getCashDrawerData = getCashDrawerData;
        vm.addActualCash = addActualCash;
        vm.addStartingCash = addStartingCash;
        vm.submitCashDrawerReport = submitCashDrawerReport;

        vm.comment = '';

        getCashDrawerData();

        function submitCashDrawerReport() {
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

            var bc = vm.balanceChange;
            var ac = vm.actualCash;
            var c = vm.comment;
            var eotdb = vm.endOfTheDayBalance;
            var ob = vm.ordersBalance;
            var sc = vm.startingCash;

            var cashdrawer = {
                balanceChange: bc,
                actualCash: ac,
                comment: c,
                endOfTheDayBalance: eotdb,
                ordersBalance: ob,
                startingCash: sc
            };

            webservice.call($rootScope.baseURL + '/cashdrawer/save_cash_drawer', 'post', cashdrawer).then(function (response) {
                console.log(response);
            });
        }

        function addStartingCash() {
            var total = vm.fiveThousandStarting * 5000 + vm.twoThousandStarting * 2000 + vm.oneThousandStarting * 1000 + vm.fiveHundredStarting * 500 + vm.twoHundredStarting * 200 + vm.oneHundredStarting * 100 + vm.fiftyStarting * 50 + vm.twentyStarting * 20 + vm.tenStarting * 10;
            console.log(total);
            vm.startingCash = total;
        }

        function addActualCash() {
            var total = vm.fiveThousandActual * 5000 + vm.twoThousandActual * 2000 + vm.oneThousandActual * 1000 + vm.fiveHundredActual * 500 + vm.twoHundredActual * 200 + vm.oneHundredActual * 100 + vm.fiftyActual * 50 + vm.twentyActual * 20 + vm.tenActual * 10;
            console.log(total);
            vm.actualCash = total;
        }

        function getCashDrawerData() {
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

            var d = new Date();
            var y = new Date(d.setDate(d.getDate() - 1));

            var dd = y.getDate();
            var mm = y.getMonth() + 1; //January is 0!
            var yyyy = y.getFullYear();
            if (mm < 10) {
                mm = '0' + mm;
            }
            if (dd < 10) {
                dd = '0' + dd;
            }
            var yesterday = yyyy + "-" + mm + "-" + dd;
            console.log(yesterday);

            var date = $('#reservation').data('daterangepicker').startDate.format("YYYY-MM-DD");

            var promises = [];
            promises.push(webservice.call($rootScope.baseURL + "/order/get_all_orders_for_date/" + date, "get"));
            promises.push(webservice.call($rootScope.baseURL + '/cashdrawer/get_cash_drawer_for_date/' + date, 'get'));

            $q.all(promises).then(function (response) {
                console.log(response);
                var orders = response[0].data.dataRows;
                var cashdrawer = response[1].data;

                if (cashdrawer != "") {
                    var oAmount = 0;
                    angular.forEach(orders, function (order, key) {
                        var payment = order.paymentDetails;
                        oAmount += payment.amount;
                    });
                    vm.ordersBalance = oAmount;

                    vm.enabletext = false;
                    var cd = cashdrawer.data;

                    vm.startingCash = cd.startingCash;
                    vm.ordersBalance = cd.ordersBalance;
                    vm.endOfTheDayBalance = cd.endOfTheDayBalance;
                    vm.actualCash = cd.actualCash;
                    vm.balanceChange = cd.balanceChange;
                    vm.comment = cd.comment;
                } else if (date == yesterday) {
                    var promises = [];
                    promises.push(webservice.call($rootScope.baseURL + "/order/get_all_orders_for_date/" + today, "get"));
                    promises.push(webservice.call($rootScope.baseURL + '/cashdrawer/get_cash_drawer_for_date/' + yesterday, 'get'));

                    $q.all(promises).then(function (response) {
                        console.log(response);
                        var orders = response[0].data.dataRows;
                        var cashdrawer = response[1].data;

                        var oAmount = 0;
                        angular.forEach(orders, function (order, key) {
                            var payment = order.paymentDetails;
                            oAmount += payment.amount;
                        });
                        vm.ordersBalance = oAmount;

                        vm.enabletext = false;
                        var cd = cashdrawer.data;

                        vm.startingCash = cd.startingCash;

                    });
                }


            });


            // } else {
            //     webservice.call($rootScope.baseURL + '/cashdrawer/get_cash_drawer_for_date/' + date, 'get').then(function (response) {
            //         vm.enabletext = false;
            //         var cd = response.data;
            //
            //         vm.startingCash = cd.startingCash;
            //         vm.ordersBalance = cd.ordersBalance;
            //         vm.endOfTheDayBalance = cd.endOfTheDayBalance;
            //         vm.actualCash = cd.actualCash;
            //         vm.balanceChange = cd.balanceChange;
            //         vm.comment = cd.comment;
            //     });
            // }
        }
    }
})();
