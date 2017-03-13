/**
 * Created by Buddhi on 2/18/2017.
 */

/**
 * Created by Buddhi on 2/11/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('UserSettingsController', UserSettingsController);

    UserSettingsController.$inject = ['webservice', '$rootScope'];

    function UserSettingsController(webservice, $rootScope) {
        var vm = this;
        $rootScope.baseURL = "http://localhost:8080/rest";

        vm.createUser = createUser;

        console.log("in settings");

        function createUser(username, password, retypepassword, name, type) {
            if (password == retypepassword) {
                if (parseInt(type) == 0) {
                    alert("Select a user type");
                } else {
                    var user = {
                        username: username,
                        password: password,
                        name: name,
                        type: parseInt(type)
                    };
                    webservice.call($rootScope.baseURL + '/user/register_user', "post", user).then(function (response) {
                        if (response.data.username == user.username) {
                            alert("User successfully created");
                        }
                    });
                }
            } else {
                alert("Passwords don't match");
            }
        }
    }
})();
