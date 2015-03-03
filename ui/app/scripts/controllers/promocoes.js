/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function PromocoesController($scope, $http, store) {

  $scope.getTodasPromocoes = function() {
	  var atendimentoSalvo = store.get('atendimento');
	  $http.get("/servicos/atendimento/promocoes/").success(function(data) {
		  $scope.orcamentos = data;
	  }).error(function(data) {
		 alert('Erro ao consultar promoções'); 
	  });
  }
  
  $scope.init = function() {
	  $scope.getTodasPromocoes();
  }
  
  $scope.init();

}
