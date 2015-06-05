/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function OrcamentosFotosController($scope, $http, store, $stateParams, $state) {
	
	$scope.atendimento; 

	$scope.init = function() {
		var atendimento = $stateParams.atendimento;
		$scope.atendimento = atendimento;
		  $http.get("/servicos/atendimento/fotos/"+atendimento).success(function(data) {
			  $scope.fotos = data;
		  }).error(function(data) {
			  alert('Erro recuperar fotos para envio de email!')
		  });
	}
	
	$scope.adicionarFotoEmail = function() {
		var atendimento = $stateParams.atendimento;
		
		var fotos = [];
		$scope.fotos.filter(function(item, index, array) {
			return item.foto.enviar == true;
		}).forEach(function(item, index, array) {
				var obj = {
						id: item.id,
						nome: item.nome,
						caminhoFoto: item.foto.caminho
				}
				fotos.push(obj);	
		});
		
		$http.post("/servicos/atendimento/fotos/"+atendimento, fotos).success(function(data) {
			$state.go("layout.orcamentos",  {atendimento: atendimento.id});
		}).error(function(data) {
			  alert('Erro ao adicionar foto!')
		});
	}
  
  $scope.init();

}
