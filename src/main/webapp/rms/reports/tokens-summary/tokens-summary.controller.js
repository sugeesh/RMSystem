/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TokensSummaryController', TokensSummaryController);

    TokensSummaryController.$inject = [];

    function TokensSummaryController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();