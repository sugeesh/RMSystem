/**
 * Created by Buddhi on 2/12/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('MenuManagementController', MenuManagementController);

    MenuManagementController.$inject = ['webservice', '$rootScope', '$q', '$state','$filter'];

    function MenuManagementController(webservice, $rootScope, $q, $state,$filter) {
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
        loadKitchen();

        function loadCategories() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/category/all_categories_with_items", "get", {}).then(function (response) {
                console.log(response.data);
                vm.categoryList = response.data.dataRows;
                vm.itemList = vm.categoryList[0].itemResourceList;
                vm.itemList = $filter('orderBy')(vm.itemList, 'priority');
                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
            });
        }

        function loadKitchen() {
            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + "/kitchen/all_kitchen", "get", {}).then(function (response) {
                console.log(response.data);
                vm.kitchenList = response.data;

                $rootScope.isLoading = false;
            }).catch(function () {
                $rootScope.isLoading = false;
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
                    $('#addCategoryModal').modal('hide');
                });
            } else {
                alert("Please give a name for the category.");
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
                $('#myModal').modal('hide');
            });

        }

        function getItemsForCategory(id) {
            angular.forEach(vm.categoryList, function (value, key) {
                if (value.categoryId == id) {
                    vm.itemList = value.itemResourceList;
                    vm.itemList = $filter('orderBy')(vm.itemList, 'priority');
                    vm.selectedCategory = id;
                    console.log(vm.itemList);
                }
            });
        }

        function addItemToList(newItemName, newItemCategory, newItemPortion, newItemPrice, newItemSKUCode, newItemTAXCode, newItemComment, newItemKitchen, newItemTakeAway) {

            if (newItemName == undefined) {
                alert("Please correctly fill the name field.");
            } else if (newItemCategory == undefined) {
                alert("Please choose a category field.");
            } else if (newItemPrice == undefined) {
                alert("Please correctly fill the price field.");
            } else if (newItemKitchen == undefined) {
                alert("Please choose a kitchen field.");
            } else {
                var newItem = {
                    name: newItemName,
                    portion: newItemPortion,
                    categoryId: newItemCategory,
                    price: newItemPrice,
                    skuCode: newItemSKUCode,
                    taxCode: newItemTAXCode,
                    comment: newItemComment,
                    kitchenId: newItemKitchen
                };

                if (newItemTakeAway) {
                    newItem.isTakeAway = 1;
                } else {
                    newItem.isTakeAway = 0;
                }

                webservice.call($rootScope.baseURL + "/item/save_item", "post", newItem).then(function (response) {
                    loadCategories();
                    alert("Item Saved.");
                    $('#addItemModal').modal('hide');

                    /*$('#addItemModal').on('hidden.bs.modal', function (e) {
                     $(this)
                     .find("input,textarea,select")
                     .val('')
                     .end()
                     .find("input[type=checkbox], input[type=radio]")
                     .prop("checked", "")
                     .end();
                     })*/
                });

                /*                if(newItemDineIn){
                 var newItemObj = jQuery.extend({}, newItem);
                 newItemObj.isTakeAway = 0;
                 webservice.call($rootScope.baseURL + "/item/save_item", "post", newItemObj).then(function (response) {
                 loadCategories();
                 console.log(response);
                 });
                 }*/
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
                    var temp2 = vm.itemList[i].priority;
                    vm.itemList[i].priority = vm.itemList[i - 1].priority;
                    vm.itemList[i - 1].priority = temp2;
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
                    var temp2 = vm.itemList[i].priority;
                    vm.itemList[i].priority = vm.itemList[i + 1].priority;
                    vm.itemList[i + 1].priority = temp2;
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
                $('#myModal').modal('hide');
            });

        }

        function updateCategory() {
            if (vm.catUpdateName != "") {
                var request = {
                    categoryId: vm.updCatId,
                    name: vm.catUpdateName,
                    colorCode: vm.catUpdateColor
                };
                webservice.call($rootScope.baseURL + "/category/update_category", "put", request).then(function (response) {
                    loadCategories();
                    $('#updateCategoryModal').modal('hide');
                });
            } else {
                alert("Please give a name for the category.");
            }
        }

        function loadUpdateForm(category) {
            vm.updCatId = category.categoryId;
            vm.catUpdateName = category.name;
            vm.catUpdateColor = category.colorCode;
        }


    }
})();
