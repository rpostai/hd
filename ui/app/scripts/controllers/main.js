'use strict';

angular.module('hdApp')
  .controller('MainController', function ($scope) {

    $scope.modelos = [];
    
    $scope.imagemNovidade = "";
    	
    $scope.imagemInformativo = "";

    $scope.init = function() {
      $scope.modelos = [
        "../images/img1.jpg",
        "../images/img2.jpg"
      ]
    }
    
    $scope.init();
  });
