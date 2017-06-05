/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrdersHistoryController', OrdersHistoryController);

    OrdersHistoryController.$inject = ['webservice', '$rootScope', '$state'];

    function OrdersHistoryController(webservice, $rootScope, $state) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.initOrderList = initOrderList;
        vm.routeToOrder = routeToOrder;
        vm.searchOrders = searchOrders;
        vm.changeType = changeType;
        vm.exportCSV = exportCSV;
        vm.cashierId = 0;


        vm.type = 2;

        initOrderList();
        loadCashiers();

        function initOrderList() {
            webservice.call($rootScope.baseURL + "/order/all_completed_orders", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

        function loadCashiers() {
            webservice.call($rootScope.baseURL + "/user/get_all_cashiers", "get").then(function (response) {
                vm.cashierList = response.data;
            });
        }


        function routeToOrder(orderId) {
            $state.go("order_detail", {'orderId': orderId});
        }


        function searchOrders() {
            var startDate = $('#reservation').data('daterangepicker').startDate.format("YYYY-MM-DD");
            var endDate = $('#reservation').data('daterangepicker').endDate.format("YYYY-MM-DD");


            console.log("start date: " + startDate);
            console.log("end date: " + endDate);
            webservice.call($rootScope.baseURL + "/order/get_orders_for_date_range/" + startDate + "/" + endDate + "/" + vm.type + "/" + vm.cashierId, "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }

        function changeType(type) {
            vm.type = type;
        }

        function exportCSV() {
            var csvData = [['KOT Number', 'Customer', 'Type', 'Date', 'Time', 'Amount']];
            angular.forEach(vm.completedOrders, function (value, key) {
                var data = [value.kotNumber, value.customerName];

                var type = '';
                if (value.type == 0) {
                    type = 'Dine In';
                } else if (value.type == 1) {
                    type = 'Take Away';
                }
                data.push(type);

                var time = new Date(value.orderTime);
                data.push(time.toLocaleDateString());
                data.push(time.getHours()+':'+time.getMinutes()+':'+time.getSeconds());

                data.push(value.amount);

                csvData.push(data);
            });

            // var pom = document.createElement('a');
            // var csvContent = csvData; //here we load our csv data
            // var blob = new Blob([csvContent], {type: 'text/csv;charset=utf-8;'});
            // var url = URL.createObjectURL(blob);
            // pom.href = url;
            // pom.setAttribute('download', 'foo.csv');
            // pom.click();

            var csvContent = "data:text/csv;charset=utf-8,";
            csvData.forEach(function(infoArray, index){

                var dataString = infoArray.join(",");
                csvContent += index < csvData.length ? dataString+ "\n" : dataString;

            });

            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "sales_report_output.csv");
            document.body.appendChild(link); // Required for FF

            link.click();
        }
    }
})();
