/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function OrcamentosController($scope, $http, store) {

  $scope.verTodosOrcamentos = function() {
	  var atendimentoSalvo = store.get('atendimento');
	  $http.get("http://localhost:8080/hd/servicos/atendimento/orcamentos/"+atendimentoSalvo.id).success(function(data) {
		  $scope.orcamentos = data;
	  }).error(function(data) {
		 alert('Erro ao resgatar orcamentos'); 
	  });
  }
  
  $scope.init = function() {
	  $scope.verTodosOrcamentos();
  }
  
  $scope.init();

}
