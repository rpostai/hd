/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function AtendimentoController($scope, $http, store,$rootScope) {

  $scope.iniciarAtendimento = function() {
	  $scope.orcamento = {
			    modelo: null,
			    quantidade: 0,
			    papelEnvelope: null,
			    papelInterno: null,
			    impressaoEnvelope: null,
			    impressaoInterno:null,
			    fita:null,
			    laco: null,
			    renda: null,
			    impressaoNome: null,
			    serigrafiaInterno: null,
			    serigrafiaEnvelope: null,
			    hotstamp: null,
			    strass: null,
			    ima: null
			  };
  }
  
  $rootScope.$on("atendimentoNovo", function(args) {
	  $scope.iniciarAtendimento();
  })
  

  $scope.getDadosAtendimento = function() {
	  $http.get("http://localhost:8080/hd/servicos/calculadora/atendimento").success(
			  function(data, status, headers, config) {
				  $scope.modelos = data.modelos;
				  $scope.papeis = data.papeis;
				  $scope.impressoes = data.impressoes;
				  $scope.impressoesNome = data.impressoesNome;
				  $scope.rendas = data.rendas;
				  $scope.strass = data.strass;
				  $scope.serigrafias = data.serigrafia;
				  $scope.hotstamp = data.hotstamps;
				  $scope.imas = data.imas;
				  $scope.fitas = data.fitas;
				  $scope.lacos = data.lacos;
			  	}).error(
			  			function (data, status, headers, config) {
			  				alert("Erro ao recuperar informações para o atendimento");
			  	})
  }
  
  $scope.calcular = function() {
	  
	  $scope.atendimento = store.get("atendimento").id;
	  
	  $http.post("http://localhost:8080/hd/servicos/calculadora/calcular/"+$scope.atendimento, $scope.orcamento).success(function(data) {
		  $scope.precoPorUnidade = data.precoFinal;
	  }).error(function(data) {
		 alert('Erro ao calcular preço. Procure o administrador do sistema'); 
	  });
  }
  
  $scope.adicionarOrcamento = function() {
	  $http.post("http://localhost:8080/hd/servicos/calculadora/calcular", $scope.orcamento).success(function(data) {
		  $scope.precoPorUnidade = data.precoFinal;
	  }).error(function(data) {
		 alert('Erro ao calcular preço. Procure o administrador do sistema'); 
	  });
  }
  
  $scope.init = function() {
	  $scope.iniciarAtendimento();
	  $scope.getDadosAtendimento();
  }
  
  $scope.init();

}
