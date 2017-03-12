/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('SalesReportController', SalesReportController);

    SalesReportController.$inject = [];

    function SalesReportController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();
