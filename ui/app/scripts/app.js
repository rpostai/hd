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
    .config(function ($stateProvider,$urlRouterProvider) {

        $urlRouterProvider.otherwise("/home");

        $stateProvider
            .state('home', {
                url: "/home",
                templateUrl: "views/main.html"
            })
            .state('atendimento', {
                url: "/atendimento",
                templateUrl: "views/atendimento.html"
            })
            .state('modelos', {
                url: "/modelos",
                templateUrl: "views/modelos.html"
            })
    });
