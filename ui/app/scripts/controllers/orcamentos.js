/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function OrcamentosController($scope, $http, store, $stateParams, $state) {
	
  $scope.verTodosOrcamentos = function(atendimento) {
	  // var atendimentoSalvo = store.get('atendimento');
	  $http.get("/servicos/atendimento/orcamentos/"+atendimento).success(function(data) {
		  $scope.orcamentos = data;
//		  data.forEach(function(item, index, array) {
//			  item.enviarEmail = true;
//		  });
		  $http.get("/servicos/atendimento/detalhe/"+atendimento).success(function(data) {
			 $scope.dados = data; 
		  });
		  
	  }).error(function(data) {
		  $scope.emails = [];
		 alert('Erro ao resgatar orcamentos'); 
	  });
  }
  
  $scope.init = function() {
	  $scope.emails = [];
	  var atendimento = $stateParams.atendimento;
	  $scope.verTodosOrcamentos(atendimento);
  }
  
  $scope.enviarPorEmail = function() {
	  var atendimento = $stateParams.atendimento;
	  if (atendimento != null) {
		  $http.get("/servicos/atendimento/enviaremail/"+atendimento+"/"+encodeURI($scope.enviarParaEmail)).success(function(data) {
			  alert('Email enviado com sucesso!')
		  }).error(function(data) {
			  alert('Erro ao enviar email!')
		  });
	  }
  }
  
  $scope.selecionarFotos = function() {
	  var atendimento = $stateParams.atendimento;
	  if (atendimento != null) {
		  $state.go("layout.selecaofotos",  {atendimento: atendimento});
	  }
  }
  
  $scope.atualizarEnviarEmail = function(orcamento) {
	  $http.post("/servicos/atendimento/atualizarenviaremail/"+orcamento.id+"/"+orcamento.enviarEmail).success(function(data) {
	  });
  }
  
  $scope.init();

}
