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
        vm.updatingtableId = 0;
        vm.editTableName = "";

        vm.loadTables = loadTables;
        vm.addTables = addTables;
        vm.updateTableId = updateTableId;
        vm.updateTable = updateTable;
        vm.removeTable = removeTable;

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
                    $('#addTableModal').modal('hide');
                });
            }else{
                alert("please give a name for the table");
            }

        }

        function removeTable(tableId) {
            webservice.call($rootScope.baseURL + "/table/delete_table", "delete", "?id=" + tableId).then(function (response) {
                console.log(response);
                loadTables();
            });

        }

        function updateTableId(id,name) {
            console.log("Come");
            vm.updatingtableId = id;
            vm.editTableName = name;

        }

        function updateTable() {
            if (name != "") {
                var newTable = {
                    tableId: vm.updatingtableId,
                    name: vm.editTableName
                };
                webservice.call($rootScope.baseURL + "/table/edit_table", "put", newTable).then(function (response) {
                   loadTables();
                   console.log(vm.tableList);
                    $('#updateTableModal').modal('hide');
                });
            }else{
                alert("please give a name for the table");
            }
        }


    }
})();

