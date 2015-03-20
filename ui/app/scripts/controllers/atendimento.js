/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function AtendimentoController($scope, $http, store,$rootScope, $state) {

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
			    ima: null,
			    cliche: null,
			    quantidadeStrass: 0
			  };
  }
  
  $rootScope.$on("atendimentoNovo", function(args) {
	  $scope.iniciarAtendimento();
  })
  

  $scope.getDadosAtendimento = function() {
	  $http.get("/servicos/calculadora/atendimento").success(
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
				  $scope.cliches = data.cliches;
			  	}).error(
			  			function (data, status, headers, config) {
			  				alert("Erro ao recuperar informações para o atendimento");
			  	})
  }
  
  $scope.fechar = function() {
	  $scope.atendimento = store.get("atendimento").id;
	  
	  $http.post("/servicos/atendimento/fechar/"+$scope.atendimento).success(function(data) {
		  alert("Venda fechada com sucesso");
	  }).error(function(data) {
		 alert('Erro ao calcular preço. Procure o administrador do sistema'); 
	  });
  }
  
  $scope.calcular = function() {
	  
	  $scope.atendimento = store.get("atendimento").id;
	  
	  $http.post("/servicos/calculadora/calcular/"+$scope.atendimento, $scope.orcamento).success(function(data) {
		  $scope.precoPorUnidade = data.valorUnidade;
		  $scope.precoItensPorPedido = data.valorItemsPorPedido;
		  $scope.precoTotal = data.valorTotal;
		  $scope.precoCalculadoConvites = data.valorTotalConvites;
		  
		  $scope.precoPorUnidadePrazo = data.valorUnidadePrazo;
		  $scope.precoItensPorPedidoPrazo = data.valorItemsPorPedidoPrazo;
		  $scope.precoTotalPrazo = data.valorTotalPrazo;
		  $scope.precoCalculadoConvitesPrazo = data.valorTotalConvitesPrazo;
		  
		  $scope.opcoesParcelamento = data.opcoesParcelamento;
		  
	  }).error(function(data) {
		 alert('Erro ao calcular preço. Procure o administrador do sistema'); 
	  });
  }
  
  $scope.adicionarOrcamento = function() {
	  $http.post("/servicos/calculadora/calcular", $scope.orcamento).success(function(data) {
		  $scope.precoPorUnidade = data.precoFinal;
	  }).error(function(data) {
		 alert('Erro ao calcular preço. Procure o administrador do sistema'); 
	  });
  }
  
  
  $scope.verTodosOrcamentos = function() {
	  var atendimento = store.get("atendimento");
	  if (atendimento != null) {
		  $state.go("layout.orcamentos",  {atendimento: atendimento.id});  
	  }
  }
  
  $scope.enviarPorEmail = function() {
	  var atendimento = store.get("atendimento");
	  if (atendimento != null) {
		  $http.get("/servicos/atendimento/enviaremail/"+atendimento.id).success(function(data) {
			  alert('Email enviado com sucesso!')
		  }).error(function(data) {
			  alert('Erro ao enviar email!')
		  });
	  }
  }
  
  $scope.init = function() {
	  $scope.iniciarAtendimento();
	  $scope.getDadosAtendimento();
  }
  
  $scope.init();

}
