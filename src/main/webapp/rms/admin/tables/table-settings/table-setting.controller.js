/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TableSettingController', TableSettingController);

    TableSettingController.$inject = ['webservice', '$rootScope', '$q', '$state'];

    function TableSettingController(webservice, $rootScope, $q, $state) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";


        vm.newTableName = '';
        vm.tableList = [];
        vm.tableAvailableCount = 0;
        vm.tableUnavailableCount = 0;


        vm.loadTables = loadTables;
        vm.updateTable = updateTable;

        loadTables();

        function loadTables() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/table/all_tables", "get", {}).then(function (response) {

                console.log(response.data);
                vm.tableList = response.data;

                angular.forEach(vm.tableList, function (value) {
                    if (value.availability == true) {
                        vm.tableAvailableCount+=1;
                    }else{
                        vm.tableUnavailableCount+=1;
                    }
                });

                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
            });
        }

        function updateTable(tableId) {
            var sendObj = {"tableId":tableId};
            webservice.call($rootScope.baseURL + "/table/update_table/","put",sendObj).then(function (response) {
                loadTables();
            });
        }

        function removeTable(item) {
            webservice.call($rootScope.baseURL + "/item/delete_item", "delete", "?id=" + item.itemId).then(function (response) {
                console.log(response);
                // loadCategories();
            });
        }

    }
})();

