/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('CashDrawerReportController', CashDrawerReportController);

    CashDrawerReportController.$inject = [];

    function CashDrawerReportController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();
