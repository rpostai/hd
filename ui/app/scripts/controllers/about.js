'use strict';

/**
 * @ngdoc function
 * @name hdApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the hdApp
 */
angular.module('hdApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
