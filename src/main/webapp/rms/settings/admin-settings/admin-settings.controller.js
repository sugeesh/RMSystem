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
        loadSChargeTakeAwaySettings();

        function saveSettings() {
            if(isNaN(vm.taxDineIn)){
                alert("Please fill a correct dine in tax value");
            }else if (isNaN(vm.serviceChargeDineIn)){
                alert("Please fill a correct dine in service charge value");
            }else if(isNaN(vm.taxTakeAway)){
                alert("Please fill a correct take away tax value");
            }else if(isNaN(vm.serviceChargeTakeAway)){
                alert("Please fill a correct take away service charge value");
            }else {
                if(vm.taxDineIn == undefined || vm.taxDineIn == ""){
                    vm.taxDineIn = 0;
                }
                if(vm.serviceChargeDineIn == undefined || vm.serviceChargeDineIn == ""){
                    vm.serviceChargeDineIn = 0;
                }
                if(vm.taxTakeAway == undefined || vm.taxTakeAway == ""){
                    vm.taxTakeAway = 0;
                }
                if(vm.serviceChargeTakeAway == undefined || vm.serviceChargeTakeAway == ""){
                    vm.serviceChargeTakeAway = 0;
                }

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
