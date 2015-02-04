'use strict';

/**
 * @ngdoc function
 * @name hdApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the hdApp
 */
angular.module('hdApp', ['ui.router'])
  .controller('MainController', function ($scope) {
    $scope.modelos = [];

    $scope.init = function() {
      $scope.modelos = [
        "../images/img1.jpg",
        "../images/img2.jpg",
      ]
    }


  });
