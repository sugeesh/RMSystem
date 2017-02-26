/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('IncomeReportsController', IncomeReportsController);

    IncomeReportsController.$inject = [];

    function IncomeReportsController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();
