/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function EventosController($scope, $http, store, $q, $stateParams) {

  $scope.getTodosEventos = function() {
	  var deferred = $q.defer();
	  
  	  $http.get("/servicos/atendimento/eventos/").success(function(data) {
  		  $scope.eventos = data;
  		  deferred.resolve();
  	  }).error(function(data) {
  		  alert('Erro ao consultar eventos');
  		deferred.reject();
  	  })
	  	
	  return deferred.promise;	
  }
  
  $scope.carregaConvites = function(evento) {
	  $http.get("/servicos/atendimento/eventos/"+evento+"/convites").success(function(data) {
		  $scope.convites = data;
	  }).error(function(data) {
		 alert('Erro ao consultar convites do evento'); 
	  });
  }
  
  $scope.getDetalhe = function() {
	  var codigoConvite = $stateParams.codigo;
	  var evento = $stateParams.evento;
	  
	  $http.get("/servicos/atendimento/eventos/"+evento+"/convites/"+codigoConvite).success(function(data) {
		  $scope.convite = data;
	  }).error(function(data) {
		 alert('Erro ao consultar detalhe do convites do evento'); 
	  });
	  
  }
  
  $scope.init = function() {
	  $scope.getTodosEventos().then(function() {
		  if ($scope.eventos != null && $scope.eventos.length > 0) {
			$scope.carregaConvites($scope.eventos[0].id);
		  }
  	  });
  }
  
  $scope.init();

}
