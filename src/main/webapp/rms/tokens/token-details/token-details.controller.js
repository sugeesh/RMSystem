/**
 * Created by Buddhi on 2/17/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('TokenDetailsController', TokenDetailsController);

    TokenDetailsController.$inject = ['$stateParams', 'webservice', '$rootScope','$state'];

    function TokenDetailsController($stateParams, webservice, $rootScope,$state) {
        var vm = this;

        $rootScope.baseURL = "http://localhost:8080/rest";
        vm.pendingOrderCount = 0;

        vm.tokenId = $stateParams.tokenId;
        vm.serveOrder = serveOrder;
        vm.setPendingOrderCount = setPendingOrderCount;

        initTokenDetails(vm.tokenId);
        setPendingOrderCount();

        function initTokenDetails(tokenId) {
            vm.menu = [];
            webservice.call($rootScope.baseURL + "/order/get_order_from_id/" + tokenId, "get").then(function (response) {
                vm.backendData = response.data;
                vm.tableId = vm.backendData.tableId;
                vm.customerName = vm.backendData.customerName;
                vm.orderId = vm.backendData.orderId;
                vm.orderTime = vm.backendData.orderTime;
                vm.type = vm.backendData.type;
                vm.comment = vm.backendData.comment;
                vm.kotNumber = vm.backendData.kotNumber;

                angular.forEach(vm.backendData.itemResourceList, function (item) {
                    var skuCode = item.skuCode;
                    if(item.itemId==-1){
                        skuCode = "OPEN ORDER";
                    }
                    var menuItem = {
                        "id": item.itemId,
                        "skuCode": skuCode,
                        "name": item.name,
                        "quantity": item.quantity,
                        "comment": item.comment
                    };
                    vm.menu.push(menuItem);
                });
            });
        }

        function serveOrder() {
            var sendObj = {"orderId":vm.orderId,"state":"COMPLETED"};

            webservice.call($rootScope.baseURL + "/order/update_status_order/", "put",sendObj).then(function (response) {
                $state.go("token_management");
                alert("Order Served.");
            });
        }


        function setPendingOrderCount() {
            webservice.call($rootScope.baseURL + "/order/all_pending_orders", "get").then(function (response) {
                vm.pendingOrderCount = response.data.dataRows.length;
            });
        }
}
})();
