/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function OrcamentosController($scope, $http, store, $stateParams) {

  $scope.verTodosOrcamentos = function(atendimento) {
	  // var atendimentoSalvo = store.get('atendimento');
	  $http.get("/servicos/atendimento/orcamentos/"+atendimento).success(function(data) {
		  $scope.orcamentos = data;
	  }).error(function(data) {
		 alert('Erro ao resgatar orcamentos'); 
	  });
  }
  
  $scope.init = function() {
	  var atendimento = $stateParams.atendimento;
	  $scope.verTodosOrcamentos(atendimento);
  }
  
  $scope.init();

}
