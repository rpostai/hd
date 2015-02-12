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
        'ui.router',
        'angular-storage',
        'angular-carousel'
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
            .state('modelo', {
                url: "/modelo/{modelo}",
                templateUrl: "views/modelo.html",
                controller: 'ModeloController'
            })
    });
