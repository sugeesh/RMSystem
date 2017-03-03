/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('MenuManagementController', MenuManagementController);

    MenuManagementController.$inject = ['webservice', '$rootScope', '$q', '$state'];

    function MenuManagementController(webservice, $rootScope, $q, $state) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.getItemsForCategory = getItemsForCategory;
        vm.addCategoryToList = addCategoryToList;
        vm.removeCategoryFromList = removeCategoryFromList;
        vm.addItemToList = addItemToList;
        vm.moveCategoryUp = moveCategoryUp;
        vm.moveCategoryDown = moveCategoryDown;
        vm.updateCategoryOrder = updateCategoryOrder;
        vm.moveItemUp = moveItemUp;
        vm.moveItemDown = moveItemDown;
        vm.updateItemOrder = updateItemOrder;
        vm.removeItemFromList = removeItemFromList;
        vm.updateCategory = updateCategory;
        vm.loadUpdateForm = loadUpdateForm;

        vm.newCategoryName = '';
        vm.itemList = [];

        loadCategories();

        function loadCategories() {
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get", {}).then(function (response) {
                console.log(response.data);
                vm.categoryList = response.data.dataRows;
                vm.itemList = vm.categoryList[0].itemResourceList;
            });
        }

        function addCategoryToList(name, color) {
            if (name != undefined) {
                var newCategory = {
                    name: name,
                    colorCode: color,
                    priority: vm.categoryList.length
                };

                webservice.call($rootScope.baseURL + "/category/save_category", "post", newCategory).then(function (response) {
                    vm.categoryList.push(response.data);
                    console.log(vm.categoryList);
                });
            }
        }

        function removeCategoryFromList(category) {

            if (Object.keys(category.itemResourceList).length != 0) {
                alert("There are items for this category, can't remove category");
            } else {
                webservice.call($rootScope.baseURL + "/category/delete_category", "delete", "?id=" + category.categoryId).then(function (response) {
                    loadCategories();
                });
            }
        }


        function removeItemFromList(item) {
            webservice.call($rootScope.baseURL + "/item/delete_item", "delete", "?id=" + item.itemId).then(function (response) {
                console.log(response);
                loadCategories();
            });

        }

        function getItemsForCategory(id) {
            angular.forEach(vm.categoryList, function (value, key) {
                if (value.categoryId == id) {
                    vm.itemList = value.itemResourceList;

                    vm.selectedCategory = id;
                    console.log(vm.itemList);
                }
            });
        }

        function addItemToList(newItemName, newItemCategory, newItemPortion, newItemPrice, newItemSKUCode, newItemTAXCode, newItemComment) {

            if (newItemName != undefined && newItemCategory != undefined && newItemPrice != undefined) {
                var newItem = {
                    name: newItemName,
                    portion: newItemPortion,
                    categoryId: newItemCategory,
                    price: newItemPrice,
                    skuCode: newItemSKUCode,
                    taxCode: newItemTAXCode,
                    comment: newItemComment
                };

                webservice.call($rootScope.baseURL + "/item/save_item", "post", newItem).then(function (response) {
                    loadCategories();
                    console.log(response);
                });
            } else {
                alert("Please correctly fill the form");
            }
        }

        /*function moveCategoryUp(id) {
         for (var i = 1; i < Object.keys(vm.categoryList).length; i++) {
         if (vm.categoryList[i].categoryId == id) {
         var temp = vm.categoryList[i];
         vm.categoryList[i] = vm.categoryList[i - 1];
         vm.categoryList[i - 1] = temp;

         vm.categoryList[i - 1].priority = i - 1;
         vm.categoryList[i].priority = i;

         var promises = [];
         promises.push(webservice.call($rootScope.baseURL + "/category/update_category_priority", "post", vm.categoryList[i - 1]));
         promises.push(webservice.call($rootScope.baseURL + "/category/update_category_priority", "post", vm.categoryList[i]));

         $q.all(promises).then(function (response) {
         console.log(response);
         });
         }
         }
         }*/

        function moveCategoryUp(id) {
            for (var i = 0; i < Object.keys(vm.categoryList).length; i++) {
                if (i == id) {
                    var temp = vm.categoryList[i];
                    vm.categoryList[i] = vm.categoryList[i - 1];
                    vm.categoryList[i - 1] = temp;

                }
            }
        }

        function moveCategoryDown(id) {
            for (var i = 0; i < Object.keys(vm.categoryList).length; i++) {
                if (i == id) {
                    var temp = vm.categoryList[i];
                    vm.categoryList[i] = vm.categoryList[i + 1];
                    vm.categoryList[i + 1] = temp;

                }
            }
        }

        function updateCategoryOrder() {
            var request = [];
            for (var i = 0; i < Object.keys(vm.categoryList).length; i++) {
                var cat = {"categoryId": vm.categoryList[i].categoryId, "priority": i};
                request.push(cat);
            }
            webservice.call($rootScope.baseURL + "/category/update_priority", "put", request).then(function (response) {
                console.log("Output" + JSON.stringify(response));
            });

        }


        function moveItemUp(id) {
            for (var i = 0; i < Object.keys(vm.itemList).length; i++) {
                if (i == id) {
                    var temp = vm.itemList[i];
                    vm.itemList[i] = vm.itemList[i - 1];
                    vm.itemList[i - 1] = temp;
                    vm.itemList[i].priority = vm.itemList[i - 1].priority;
                    vm.itemList[i - 1].priority = vm.itemList[i].priority;
                    console.log(i + "  " + i - 1);
                    break;
                }
            }
        }

        function moveItemDown(id) {
            for (var i = 0; i < Object.keys(vm.itemList).length; i++) {
                if (i == id) {
                    var temp = vm.itemList[i];
                    vm.itemList[i] = vm.itemList[i + 1];
                    vm.itemList[i + 1] = temp;
                    console.log(i + "  " + i + 1);
                    vm.itemList[i].priority = vm.itemList[i + 1].priority;
                    vm.itemList[i + 1].priority = vm.itemList[i].priority;
                    break;
                }
            }
        }

        function updateItemOrder() {
            var request = [];
            for (var i = 0; i < Object.keys(vm.itemList).length; i++) {
                var ite = {"itemId": vm.itemList[i].itemId, "priority": i, "active": vm.itemList[i].active};
                request.push(ite);
            }
            webservice.call($rootScope.baseURL + "/item/update_priority", "put", request).then(function (response) {
                console.log("Output" + JSON.stringify(response));
            });

        }

        function updateCategory() {
            var request = {
                categoryId: vm.updCatId,
                name: vm.catUpdateName,
                colorCode: vm.catUpdateColor
            };
            webservice.call($rootScope.baseURL + "/category/update_category", "put", request).then(function (response) {
                loadCategories();
            });

        }

        function loadUpdateForm(category) {
            vm.updCatId = category.categoryId;
            vm.catUpdateName = category.name;
            vm.catUpdateColor = category.colorCode;
        }

    }
})();

