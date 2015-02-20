'use strict';

/**
 * @ngdoc overview
 * @name hdApp
 * @description
 * # hdApp
 *
 * Main module of the application.
 */
var app = angular
    .module('hdApp', [
        'ngResource',
        'ui.router',
        'angular-storage',
        'ui.bootstrap',
        'ui.grid'
    ])
    .config(function ($stateProvider,$urlRouterProvider) {

        $urlRouterProvider.otherwise("/home");

        $stateProvider
            .state('home', {
                url: "/home",
                templateUrl: "views/main.html"
            })
            .state('layout', {
            	abstract: true,
            	templateUrl: "views/layout.html"
            })
            .state('layout.atendimento', {
                url: "/atendimento",
                templateUrl: "views/atendimento.html"
            })
            .state('layout.atendimentoretorno', {
                url: "/atendimento/{atendimentoOriginal}",
                templateUrl: "views/atendimento.html"
            })
            .state('layout.modelos', {
                url: "/modelos",
                templateUrl: "views/modelos.html"
            })
            .state('layout.modelo', {
                url: "/modelo/{modelo}",
                templateUrl: "views/modelo.html",
                controller: 'ModeloController'
            })
            .state('layout.fotos', {
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
            .state('layout.orcamentos', {
            	url: '/orcamentos',
            	templateUrl: 'views/orcamentos.html'
            })
            .state('layout.atendimentos', {
            	url: '/consultaatendimentos',
            	templateUrl: 'views/atendimentos.html'
            })
    });
