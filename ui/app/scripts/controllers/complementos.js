/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function ComplementosController($scope, $http, store) {
	
  $scope.quantidade = {};
  $scope.valorTotal = 0;

  $scope.verComplementos = function() {
	  var atendimentoSalvo = store.get('atendimento');
	  $http.get("http://localhost:8080/hd/servicos/atendimento/complementos/"+atendimentoSalvo.id).success(function(data) {
		  $scope.dados = data;
		  $scope.valorTotal = 0;
		  $scope.dados.forEach(function(item,index,array) {
			  $scope.quantidade[item] = 0;
		  });
		  
		  $http.get("http://localhost:8080/hd/servicos/atendimento/complementosatendimento/"+atendimentoSalvo.id).success(function(data) {
			 data.forEach(function(item,index,array) {
				 $scope.quantidade[item.complemento.id] = item.quantidade;
				 $scope.valorTotal = $scope.valorTotal + (item.quantidade * item.valor);
			 });
		  });
		  
		  
	  }).error(function(data) {
		 alert('Erro ao resgatar complementos'); 
	  });
  }
  
 
  $scope.adicionar = function(item,valor) {
	  var atendimentoSalvo = store.get('atendimento');
	  var quantidade = $scope.quantidade[item];
	  if (quantidade > 0) {
		  $http.post("http://localhost:8080/hd/servicos/atendimento/complementossalvar/"+atendimentoSalvo.id+"/"+item+"/"+quantidade).success(function(data) {
			  $scope.valorTotal = data;
			  alert("Complemento adicionado com sucesso!")
		  }).error(function(data) {
			 alert('Erro ao salvar complementos'); 
		  });
	  }
  }
  
  $scope.init = function() {
	  $scope.verComplementos();
  }
  
  $scope.init();

}
