/**
 * Created by Buddhi on 2/18/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$state', '$rootScope', 'webservice'];

    function RegisterController($state, $rootScope, webservice) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.createUser = createUser;

        vm.name = 'Buddhi';

        function createUser(username, password, retypepassword, name, type) {
            vm.error = "";

            if (password == retypepassword) {
                var user = {
                    username: username,
                    password: password,
                    name: name,
                    type: parseInt(type)
                };
                webservice.call($rootScope.baseURL + '/user/register_user', "post", user).then(function (response) {
                    if (response.data.username == user.username) {
                        $state.go('login');
                    }
                });
            }else{
                vm.error = "Password mismatch";
            }
        }
    }
})();


