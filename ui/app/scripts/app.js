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
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.router'
  ])
  .config(function ($routeProvider,$stateProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/about', {
        templateUrl: 'views/about.html',
        controller: 'AboutCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });

    $stateProvider
      .state('home', {
        url: "/home",
        templateUrl: "partials/main.html"
      })


  });
