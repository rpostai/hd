/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function ModelosController($scope, $http, store) {

  $scope.getModelos = function() {
	  $http.get("http://localhost:8080/hd/servicos/atendimento/modelos").success(
			  function(data, status, headers, config) {
				  $scope.modelos = data;
			  	}).error(
			  			function (data, status, headers, config) {
			  				alert("Erro ao recuperar modelos");
			  	})
  }
  
  $scope.init = function() {
	  $scope.getModelos();
  }
  
  $scope.init();

}
