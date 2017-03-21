/**
 * Created by Buddhi on 2/18/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', '$rootScope', 'webservice', '$cookies'];

    function LoginController($state, $rootScope, webservice, $cookies) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.signInUser = signInUser;

        function signInUser(username, password) {
            var user = {
                username: username,
                password: password,
                name: "",
                type: 0
            };

            $rootScope.isLoading = true;
            webservice.call($rootScope.baseURL + '/user/login_user', "post", user).then(function (response) {
                console.log(response.data);
                if (response.data.userId != null) {
                    if (response.data.type == 1) {
                        $cookies.put('userType', 'ADMIN');
                    } else if (response.data.type == 2) {
                        $cookies.put('userType', 'CASHIER');
                    } else if (response.data.type == 3) {
                        $cookies.put('userType', 'WAITER');
                    } else if (response.data.type == 4) {
                        $cookies.put('userType', 'KITCHEN');
                    }
                    $cookies.put('userId', response.data.userId);
                    $cookies.put('userName', response.data.name);
                    $state.go('dashboard');
                } else {
                    // alert("Username or Password is wrong.");
                    $rootScope.isLoading = false;
                    vm.error = "Username and password mismatch";
                }
            });
        }

    }
})();
