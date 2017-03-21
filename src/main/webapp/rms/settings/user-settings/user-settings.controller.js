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
        vm.openUserModal = openUserModal;
        vm.updateUser = updateUser;

        vm.usersList = [];

        console.log("in settings");

        getAllUserDetails();

        function updateUser() {
            if (vm.selectedPassword == vm.selectedRetypePassword) {
                var user = {
                    id: vm.selectedId,
                    name: vm.selectedName,
                    username: vm.selectedUsername,
                    password: vm.selectedPassword,
                    nic: vm.selectedNic,
                    type: vm.selectedType,
                    telephone: vm.selectedTelephone
                };

                webservice.call($rootScope.baseURL + '/user/update_user/', 'post', user).then(function (response) {
                    console.log(response);

                    alert("User Saved successfully.");

                    getAllUserDetails();
                });
            } else {
                alert("Passwords mismatch.");
            }

        }

        function openUserModal(user) {
            vm.selectedId = user.id;
            vm.selectedName = user.name;
            vm.selectedUsername = user.username;
            vm.selectedNic = user.nic;
            vm.selectedTelephone = user.telephone;
            vm.selectedPassword = user.password;
            vm.selectedRetypePassword = user.password;
            vm.selectedType = user.type;

            $('#openOrderModal').openModal();
        }

        function getAllUserDetails() {
            webservice.call($rootScope.baseURL + '/user/get_all_users', 'get').then(function (response) {
                vm.usersList = response.data;
            });
        }

        function createUser(username, password, retypepassword, name, type, nic, telephone) {
            if (password == retypepassword) {
                if (parseInt(type) == 0) {
                    alert("Select a user type");
                } else {
                    var user = {
                        id: vm.selectedId,
                        username: username,
                        password: password,
                        name: name,
                        type: parseInt(type),
                        nic: nic,
                        telephone: telephone
                    };
                    webservice.call($rootScope.baseURL + '/user/register_user', "post", user).then(function (response) {
                        if (response.data.username == user.username) {
                            alert("User successfully created");

                            getAllUserDetails();
                        }
                    });
                }
            } else {
                alert("Passwords don't match");
            }
        }
    }
})();
