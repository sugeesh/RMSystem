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

        function getOrdersForDay() {
            var today = $('#reservation').data('daterangepicker').startDate.format("YYYY-MM-DD");

            console.log("today: " + today);
            webservice.call($rootScope.baseURL + "/order/get_orders_for_date/" + today + "/2", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }
    }
})();