/**
 * Created by Buddhi on 4/6/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('MenuRelationsController', MenuRelationsController);

    MenuRelationsController.$inject = ['$rootScope', 'webservice'];

    function MenuRelationsController($rootScope, webservice) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.changeItemListInCombo = changeItemListInCombo;
        vm.changeItemListInList = changeItemListInList;
        vm.loadMainListForItem = loadMainListForItem;

        vm.mainList = [];

        initCategoriesList();

        function initCategoriesList() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get").then(function (response) {
                vm.categoriesList = response.data.dataRows;
                console.log(response.data.dataRows[0].categoryId);
            });
        }

        function changeItemListInCombo() {
            vm.parentItem = JSON.parse(vm.changedParentItem);
            vm.parentItemsList = vm.parentItem.itemResourceList;
        }

        function changeItemListInList() {
            vm.childItem = JSON.parse(vm.changedChildItem);
            vm.childItemsList = vm.childItem.itemResourceList;
        }

        function loadMainListForItem() {
            console.log(vm.mainItem);
            vm.mItem = JSON.parse(vm.mainItem);
            webservice.call($rootScope.baseURL + "/itemrelation/get_child_items_for_item/" + vm.mItem.itemId, "get").then(function (response) {
                vm.primaryItemsList = response.data.dataRows;
                console.log(vm.primaryItemsList);
            });
        }

    }
})();
