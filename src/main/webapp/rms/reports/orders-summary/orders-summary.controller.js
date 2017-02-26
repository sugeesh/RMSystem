/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('OrdersSummaryController', OrdersSummaryController);

    OrdersSummaryController.$inject = [];

    function OrdersSummaryController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();
