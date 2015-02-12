/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function ModeloController($scope, $http, $stateParams) {

    $scope.carouselIndex = 0;

  $scope.getModelo = function() {
//	  $http.get("http://localhost:8080/hd/servicos/atendimento/modelos/"+$stateParams.modelo).success(
//			  function(data, status, headers, config) {
//				  $scope.modelo = data;
//			  	}).error(
//			  			function (data, status, headers, config) {
//			  				alert("Erro ao recuperar modelos");
//			  	})

      $scope.modelo = {
          id: 1,
          nome: 'Alessandra',
          foto: [
              'images/img1.jpg',
              'images/img2.jpg'
          ]
      }
  }

    $scope.init = function() {
        $scope.getModelo();
    }

    $scope.init();
  
}
