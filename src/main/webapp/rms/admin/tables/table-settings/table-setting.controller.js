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


        vm.loadTables = loadTables;
        vm.addTables = addTables;

        loadTables();

        function loadTables() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/table/all_tables", "get", {}).then(function (response) {
                console.log(response.data);
                vm.tableList = response.data;
                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
            });
        }

        function updateTable(tableId) {
            var sendObj = {"tableId":vm.orderId};
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

