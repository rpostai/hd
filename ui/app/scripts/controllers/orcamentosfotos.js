/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function OrcamentosFotosController($scope, $http, store, $stateParams, $state) {

	$scope.init = function() {
		var atendimento = $stateParams.atendimento;
		  $http.get("/servicos/atendimento/fotos/"+atendimento).success(function(data) {
			  $scope.fotos = data;
		  }).error(function(data) {
			  alert('Erro recuperar fotos para envio de email!')
		  });
	}
	
	$scope.adicionarFotoEmail = function() {
		var atendimento = $stateParams.atendimento;
		$http.post("/servicos/atendimento/fotos/"+atendimento, $scope.fotos).success(function(data) {
			$state.go("layout.orcamentos",  {atendimento: atendimento.id});
		}).error(function(data) {
			  alert('Erro ao adicionar foto!')
		});
	}
  
  $scope.init();

}
