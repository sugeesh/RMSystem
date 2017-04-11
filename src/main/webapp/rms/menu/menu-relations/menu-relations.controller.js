/**
 * Created by Buddhi on 4/6/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('MenuRelationsController', MenuRelationsController);

    MenuRelationsController.$inject = ['$rootScope', 'webservice', '$q'];

    function MenuRelationsController($rootScope, webservice, $q) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.changeItemListInCombo = changeItemListInCombo;
        vm.changeItemListInList = changeItemListInList;
        vm.loadMainListForItem = loadMainListForItem;
        vm.addItemToRelation = addItemToRelation;
        vm.removeItemFromRelation = removeItemFromRelation;

        vm.mainList = [];
        // vm.primaryItemsList = [];

        initCategoriesList();

        function removeItemFromRelation() {
            var selectedPrimary = JSON.parse(vm.selectedPrimaryItem);

            if(selectedPrimary == undefined){
                alert("Please select an item first");
            }else{
            // webservice.call($rootScope.baseURL + "/itemrelation/remove_item_relation/" + vm.mItem.itemId + "/" + selectedPrimary.itemId, "delete").then(function (response) {
            //     var temp = [];
            //
            //     for (var i = 0; i < Object.keys(vm.primaryItemsList).length; i++) {
            //         if (vm.primaryItemsList[i].itemId != selectedPrimary.itemId) {
            //             temp.push(vm.primaryItemsList[i]);
            //         }
            //     }
            //
            //     vm.primaryItemsList = temp;
            // });
            }
        }

        function addItemToRelation() {
            if (vm.mainItem == undefined) {
                alert("Please select an item from the left pane first");
            } else {
                var selectedSecondary = JSON.parse(vm.selectedSecondaryItem);

                var itemExists = true;

                for (var i = 0; i < Object.keys(vm.primaryItemsList).length; i++) {
                    if (vm.primaryItemsList[i].itemId == selectedSecondary.itemId) {
                        itemExists = true;
                        break;
                    }
                    itemExists = false;
                }

                if (itemExists == false) {
                    webservice.call($rootScope.baseURL + "/itemrelation/add_new_item_relation/" + vm.mItem.itemId + "/" + selectedSecondary.itemId, "post").then(function (response) {
                        vm.primaryItemsList.push(selectedSecondary);
                    });
                } else {
                    alert("The item already exists.");
                }
            }
        }

        function initCategoriesList() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get").then(function (response) {
                vm.categoriesList = response.data.dataRows;
                console.log(response.data.dataRows[0].categoryId);
            });
        }

        function changeItemListInCombo() {
            vm.parentItem = JSON.parse(vm.changedParentItem);
            vm.parentItemsList = vm.parentItem.itemResourceList;

            console.log(vm.parentItemsList);
        }

        function changeItemListInList() {
            vm.childItem = JSON.parse(vm.changedChildItem);
            vm.childItemsList = vm.childItem.itemResourceList;
        }

        function loadMainListForItem() {
            console.log(vm.mainItem);
            vm.mItem = JSON.parse(vm.mainItem);

            webservice.call($rootScope.baseURL + "/itemrelation/get_child_items_for_item/" + vm.mItem.itemId, "get").then(function (response) {
                console.log(response);

                vm.mItem.childItems = response.data.dataRows;
                vm.primaryItemsList = vm.mItem.childItems;
            });

        }

    }
})();
