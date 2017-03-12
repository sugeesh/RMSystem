/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('EndOfTheDayReportController', EndOfTheDayReportController);

    EndOfTheDayReportController.$inject = [];

    function EndOfTheDayReportController() {
        var vm = this;

        vm.getOrdersForDay = getOrdersForDay;

        function getOrdersForDay() {
            var today = $('#reservation').data('datepicker').date.format("YYYY-MM-DD");

            console.log("today: " + today);
            webservice.call($rootScope.baseURL + "/order/get_orders_for_date_range/" + today + "/" + today + "/2", "get").then(function (response) {
                vm.completedOrders = response.data.dataRows;
                vm.completedOrderCount = response.data.entries;
                console.log(response);
                //  vm.categoriesList = response.data.dataRows;
            });
        }
    }
})();