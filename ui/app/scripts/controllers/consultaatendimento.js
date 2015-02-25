/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function ConsultaAtendimentoController($scope, $http, store, $rootScope, $state) {

	$scope.consultar = function() {
		$http.post("/servicos/atendimento/consulta",$scope.consulta).success(
			function(data, status, headers, config) {
				$scope.dados = data;

				$scope.gridOptions = {
					data : 'dados'
				};
	
			}).error(function(data, status, headers, config) {
				alert("Erro ao recuperar informações para o atendimento");
			});
	}
	
	$scope.init = function() {
		$scope.consulta = {
				numero : "",
				nome : "",
				data : ""
			}		
	}
	
	$scope.init();

}
