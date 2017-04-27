/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TableManagementController', TableManagementController);

    TableManagementController.$inject = ['webservice', '$rootScope', '$q', '$state'];

    function TableManagementController(webservice, $rootScope, $q, $state) {
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

        function addTables(name) {
            if (name != undefined) {
                var newTable = {
                    name: name
                };
                webservice.call($rootScope.baseURL + "/table/", "post", newTable).then(function (response) {
                    vm.tableList.push(response.data);
                    console.log(vm.tableList);
                });
            }
        }

        function removeTable(item) {
            webservice.call($rootScope.baseURL + "/item/delete_item", "delete", "?id=" + item.itemId).then(function (response) {
                console.log(response);
                // loadCategories();
            });

        }

    }
})();

