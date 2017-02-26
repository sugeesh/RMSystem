/**
 * Created by Buddhi on 2/16/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TokensHistoryController', TokensHistoryController);

    TokensHistoryController.$inject = [];

    function TokensHistoryController() {
        var vm = this;

        vm.name = 'Buddhi';
    }
})();
