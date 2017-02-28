/**
 * Created by Buddhi on 2/27/2017.
 */

(function () {
    'use strict';

    angular.module('myApp')
        .controller('BodyController', BodyController);

    BodyController.$inject = ['$state', '$rootScope', 'webservice'];

    function BodyController($state, $rootScope, webservice) {
        var vm = this;

        console.log("Buddhi");
    }
})();

