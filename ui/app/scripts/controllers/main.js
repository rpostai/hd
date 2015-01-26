'use strict';

/**
 * @ngdoc function
 * @name hdApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the hdApp
 */
angular.module('hdApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
