/**
 * Created by Buddhi on 2/13/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['webservice', '$rootScope', '$state', '$interval'];

    function DashboardController(webservice, $rootScope, $state, $interval) {
        var vm = this;

        setDateTime();

        function setDateTime() {
            var tick = function() {
                vm.time = Date.now();
            };
            tick();
            $interval(tick, 1000);
        }
    }
})();

