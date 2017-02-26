/**
 * Created by Buddhi on 2/18/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$state', '$rootScope', 'webservice', 'localStorageService', '$sessionStorage'];

    function LoginController($state, $rootScope, webservice, localStorageService, $sessionStorage) {
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

            webservice.call($rootScope.baseURL + '/user/login_user', "post", user).then(function (response) {
                console.log(response.data);
                if (response.data != "") {
                    // localStorageService.set("user", response.data, "sessionStorage");

                    $sessionStorage.putObject('user', response.data);
                    $state.go('dashboard');
                }else{
                    vm.error = "Username and password mismatch";
                }
            });
        }

    }
})();
