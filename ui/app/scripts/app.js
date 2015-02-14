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
        'angular-storage'
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
            .state('fotos', {
            	url: "/fotos",
            	templateUrl: "views/fotos.html",
            	controller: function($scope, $http) {
            		$http.get('http://localhost:8080/hd/servicos/atendimento/fotos').success(function(data) {
            			$scope.modelos = data;
            		}).error(function(data) {
            			alert ('Não foi possível recuperar as fotos')
            		})
            	}
            })
    });
