'use strict';

/**
 * @ngdoc overview
 * @name hdApp
 * @description
 * # hdApp
 *
 * Main module of the application.
 */
angular
  .module('hdApp', [
    'ngResource',
    'ui.router'
  ])
  .config(function ($stateProvider) {
    $stateProvider
      .state('home', {
        url: "/home",
        templateUrl: "partials/main.html"
      })


  });
