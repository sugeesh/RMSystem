/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('CashDrawerReportController', CashDrawerReportController);

    CashDrawerReportController.$inject = ['webservice', '$rootScope', '$q','$route'];

    function CashDrawerReportController(webservice, $rootScope, $q,$route) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";


        vm.showTable = false;
        // vm.getCashDrawerData = getCashDrawerData;
        vm.addActualCash = addActualCash;
        vm.addStartingCash = addStartingCash;
        vm.submitCashDrawerReport = submitCashDrawerReport;
        vm.getCashDrawerDataForDateRange = getCashDrawerDataForDateRange;

        vm.fiveThousandStarting = 0;
        vm.twoThousandStarting = 0;
        vm.oneThousandStarting = 0;
        vm.fiveHundredStarting = 0;
        vm.twoHundredStarting = 0;
        vm.oneHundredStarting = 0;
        vm.fiftyStarting = 0;
        vm.twentyStarting = 0;
        vm.tenStarting = 0;
        vm.fiveThousandActual = 0;
        vm.twoThousandActual = 0;
        vm.oneThousandActual = 0;
        vm.fiveHundredActual = 0;
        vm.twoHundredActual = 0;
        vm.oneHundredActual = 0;
        vm.fiftyActual = 0;
        vm.twentyActual = 0;
        vm.tenActual = 0;

        vm.startingCash = 0;
        vm.ordersBalance = 0;
        vm.endOfTheDayBalance = 0;
        vm.actualCash = 0;
        vm.balanceChange = 0;
        vm.comment = '';
        vm.updatFlag = false;

        vm.cashDrawerId = -1;
        vm.calcBalance = calcBalance;


        // getCashDrawerData();

        initForm();

        function getCashDrawerDataForDateRange() {
            var startDate = $('#daterangewidget').data('daterangepicker').startDate.format("YYYY-MM-DD");
            var endDate = $('#daterangewidget').data('daterangepicker').endDate.format("YYYY-MM-DD");

            webservice.call($rootScope.baseURL + '/cashdrawer/get_cash_drawer_for_date_range/' + startDate + '/' + endDate, 'get').then(function (response) {
                vm.cashdrawerList = response.data;
                vm.showTable = true;
            });


        }

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

            if(vm.updatFlag){
                cashdrawer["id"] = vm.cashDrawerId;
                webservice.call($rootScope.baseURL + '/cashdrawer/update_cash_drawer', 'put', cashdrawer).then(function (response) {
                    console.log(response);
                    alert("Balance Updated");
                });
            }else{
                webservice.call($rootScope.baseURL + '/cashdrawer/save_cash_drawer', 'post', cashdrawer).then(function (response) {
                    console.log(response);
                    alert("Entry Added");
                });
            }

        }

        function addStartingCash() {
            var total = vm.fiveThousandStarting * 5000 + vm.twoThousandStarting * 2000 + vm.oneThousandStarting * 1000 + vm.fiveHundredStarting * 500 + vm.twoHundredStarting * 200 + vm.oneHundredStarting * 100 + vm.fiftyStarting * 50 + vm.twentyStarting * 20 + vm.tenStarting * 10;
            console.log(total);
            vm.startingCash = Number(total);

            vm.endOfTheDayBalance = Number(vm.startingCash) + Number(vm.ordersBalance);
            vm.balanceChange = Math.abs(Number(vm.endOfTheDayBalance) - Number(vm.actualCash));
        }

        function addActualCash() {
            var total = vm.fiveThousandActual * 5000 + vm.twoThousandActual * 2000 + vm.oneThousandActual * 1000 + vm.fiveHundredActual * 500 + vm.twoHundredActual * 200 + vm.oneHundredActual * 100 + vm.fiftyActual * 50 + vm.twentyActual * 20 + vm.tenActual * 10;
            console.log(total);
            vm.actualCash = Number(total);

            vm.balanceChange = Math.abs(Number(vm.endOfTheDayBalance) - Number(vm.actualCash));
        }

        function initForm(){
            webservice.call($rootScope.baseURL + "/cashdrawer/get_cash_drawer_for_today", "get").then(function (response) {
                if(response.data != ""){
                    vm.startingCash = Number(response.data.actualCash);
                    vm.cashDrawerId = response.data.id;
                    vm.updatFlag = true;
                }else{
                    webservice.call($rootScope.baseURL + "/cashdrawer/get_cash_drawer_for_yesterday", "get").then(function (response1) {
                        if(response1.data != ""){
                            vm.startingCash = Number(response1.data.actualCash);
                        }else {
                            vm.startingCash = 0;
                        }
                    });

                }
                loadOrderDetailsForToday();
            });

        }



        function loadOrderDetailsForToday() {
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


            webservice.call($rootScope.baseURL +"/order/get_all_orders_for_date/" + today, "get").then(function (response) {
                if (response.data != "") {
                    var orders = response.data.dataRows;
                    var oAmount = 0;
                    angular.forEach(orders, function (order) {
                        var payment = order.paymentDetails;
                        oAmount += payment.amount;
                    });
                    vm.ordersBalance = Number(oAmount);
                    vm.endOfTheDayBalance = Number(vm.startingCash) + Number(vm.ordersBalance);
                } else {
                    vm.ordersBalance =0;
                    vm.endOfTheDayBalance = Number(vm.startingCash) + Number(vm.ordersBalance);
                }
            });
        }

        function calcBalance() {
            vm.endOfTheDayBalance = Number(vm.startingCash) + Number(vm.ordersBalance);
            vm.balanceChange = Math.abs(Number(vm.endOfTheDayBalance)- Number(vm.actualCash));
        }

        /*function getCashDrawerData() {
            vm.startingCash = 0;
            vm.ordersBalance = 0;
            vm.endOfTheDayBalance = 0;
            vm.actualCash = 0;
            vm.balanceChange = 0;
            vm.comment = '';

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

            var date = $('#datepickerwidget').data('daterangepicker').startDate.format("YYYY-MM-DD");

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

                    var cd = cashdrawer;

                    vm.startingCash = cd.startingCash;
                    vm.ordersBalance = cd.ordersBalance;
                    vm.endOfTheDayBalance = cd.endOfTheDayBalance;
                    vm.actualCash = cd.actualCash;
                    vm.balanceChange = cd.balanceChange;
                    vm.comment = cd.comment;
                } else if (date == today) {
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

                        vm.startingCash = cashdrawer.actualCash;
                        vm.endOfTheDayBalance = vm.startingCash + vm.ordersBalance;
                        vm.balanceChange = Math.abs(vm.endOfTheDayBalance - vm.actualCash);

                    });
                } else if (cashdrawer == "") {
                    vm.startingCash = 0;
                    vm.ordersBalance = 0;
                    vm.endOfTheDayBalance = 0;
                    vm.actualCash = 0;
                    vm.balanceChange = 0;
                    vm.comment = '';
                }
            });
        }*/
    }
})();
