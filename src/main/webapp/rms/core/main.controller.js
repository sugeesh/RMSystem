/**
 * Created by Buddhi on 2/18/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('MainController', MainController);

    MainController.$inject = ['$state', '$location'];

    function MainController($state, $location) {
        var vm = this;

        console.log($location);

        $state.go('dashboard');

        // var loggedInUser = $sessionStorage.getItem('loggedInUser');
        // var loggedInUser = {};
        // if(loggedInUser == null){
        //     $state.go('login');
        // }else{
        //     $state.go('register')
        // }
    }
})();

