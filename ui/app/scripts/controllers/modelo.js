/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function ModeloController($scope, $http, $stateParams) {

    $scope.carouselIndex = 0;

  $scope.getModelo = function() {
	  $http.get("/servicos/atendimento/modelos/"+$stateParams.modelo).success(
			  function(data, status, headers, config) {
				  $scope.modelo = data;
			  	}).error(
			  			function (data, status, headers, config) {
			  				alert("Erro ao recuperar modelos");
			  	})

  }

    $scope.init = function() {
        $scope.getModelo();
    }

    $scope.init();
  
}
