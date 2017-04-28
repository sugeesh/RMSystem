/**
 * Created by Buddhi on 2/18/2017.
 */

/**
 * Created by Buddhi on 2/11/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('AdminSettingsController', AdminSettingsController);

    AdminSettingsController.$inject = ['webservice', '$rootScope'];

    function AdminSettingsController(webservice, $rootScope) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.tax = 0;
        vm.serviceCharge = 0;

        vm.saveSettings = saveSettings;

        loadTaxSettings();
        loadSChargeSettings();


        function saveSettings() {
            var sendObj = {"tax": parseFloat(vm.tax), "s_charge": parseFloat(vm.serviceCharge)};
            webservice.call($rootScope.baseURL + '/admin', 'post', sendObj).then(function (response) {
                alert("Settings Saved successfully.");
            });
        }

        function loadTaxSettings() {
            webservice.call($rootScope.baseURL + "/admin/tax", "get").then(function (response) {
                vm.tax = response.data;
            });
        }

        function loadSChargeSettings() {
            webservice.call($rootScope.baseURL + "/admin/s_charge", "get").then(function (response) {
                vm.serviceCharge = response.data;
            });
        }

    }
})();
