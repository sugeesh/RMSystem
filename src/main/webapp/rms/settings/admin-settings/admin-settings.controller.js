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

        vm.taxDineIn = 0;
        vm.serviceChargeDineIn = 0;
        vm.taxTakeAway = 0;
        vm.serviceChargeTakeAway = 0;

        vm.saveSettings = saveSettings;

        loadTaxDineInSettings();
        loadSChargeDineInSettings();
        loadTaxTakeAwaySettings();
        loadSChargeTakeAwaySettings()

        function saveSettings() {
            if(vm.taxDineIn == undefined || vm.taxDineIn == "" || isNaN(vm.taxDineIn)){
                alert("Please fill a correct tax value");
            }else if(vm.serviceChargeDineIn == undefined || vm.serviceChargeDineIn == "" || isNaN(vm.serviceChargeDineIn)){
                alert("Please fill a correct service charge value");
            }else if(vm.taxTakeAway == undefined || vm.taxTakeAway == "" || isNaN(vm.taxTakeAway)){
                alert("Please fill a correct tax value");
            }else if(vm.serviceChargeTakeAway == undefined || vm.serviceChargeTakeAway == "" || isNaN(vm.serviceChargeTakeAway)){
                alert("Please fill a correct service charge value");
            }else {
                var sendObj = {
                    "tax_dinein": parseFloat(vm.taxDineIn),
                    "s_charge_dinein": parseFloat(vm.serviceChargeDineIn),
                    "tax_takeaway": parseFloat(vm.taxTakeAway),
                    "s_charge_takeaway": parseFloat(vm.serviceChargeTakeAway)
                };
                webservice.call($rootScope.baseURL + '/admin', 'post', sendObj).then(function (response) {
                    alert("Settings Saved successfully.");
                });
            }
        }

        function loadTaxDineInSettings() {
            webservice.call($rootScope.baseURL + "/admin/taxdinein", "get").then(function (response) {
                vm.taxDineIn = response.data;
            });
        }

        function loadSChargeDineInSettings() {
            webservice.call($rootScope.baseURL + "/admin/s_chargedinein", "get").then(function (response) {
                vm.serviceChargeDineIn = response.data;
            });
        }

        function loadTaxTakeAwaySettings() {
            webservice.call($rootScope.baseURL + "/admin/taxtakeaway", "get").then(function (response) {
                vm.taxTakeAway = response.data;
            });
        }

        function loadSChargeTakeAwaySettings() {
            webservice.call($rootScope.baseURL + "/admin/s_chargetakeaway", "get").then(function (response) {
                vm.serviceChargeTakeAway = response.data;
            });
        }

    }
})();
