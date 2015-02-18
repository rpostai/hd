/**
 * 
 */

function HeaderController($scope, $http, store,$rootScope, $stateParams) {
	
	$scope.definirNovoAtendimento = function() {
		$scope.atendimento = {
			estado : false,
			numero : 0,
			pessoa1 : '',
			email1 : '',
			telefone1 : '',
			pessoa2 : '',
			telefone2 : '',
			email2 : '',
			label : !this.estado ? 'Iniciar atendimento'
					: 'Finalizar atendimento'
		}
		$rootScope.$broadcast("atendimentoNovo");
	}

	$scope.atendimentoSalvo = null;

	$scope.realizarAtendimento = function() {
		if (!$scope.atendimento.estado) {
			$scope.iniciarAtendimento();
		} else {
			$scope.finalizarAtendimento();
		}
	}

	$scope.iniciarAtendimento = function() {
		
		if (!$scope.atendimento.estado) {
			
			var atendimentoOriginal = $stateParams.atendimentoOriginal;
			var url = "http://localhost:8080/hd/servicos/atendimento/iniciar";
			url += atendimentoOriginal != undefined ? "/"+atendimentoOriginal : "";
			
			$http.post(url)
					.success(function(data) {
						$scope.atendimentoSalvo = data;
						store.set('atendimento', data);
						$scope.atendimento.numero = data.numero;
						$scope.atendimento.dataInicio = moment(new Date(data.dataInicio)).format('DD/MM/YYYY HH:mm:ss');
						$scope.atendimento.estado = true;
						
						if (atendimentoOriginal != undefined) {
							$scope.atendimento.pessoa1 = data.cliente1.nome;
							$scope.atendimento.pessoa2 = data.cliente2.nome;
							$scope.atendimento.telefone1 = data.cliente1.telefone;
							$scope.atendimento.telefone2 = data.cliente2.telefone;
							$scope.atendimento.email1 = data.cliente1.email;
							$scope.atendimento.email2 = data.cliente2.email;
							
						}
						
						
						$scope.atendimento.label = 'Finalizar atendimento';
					});
		}
	}

	$scope.finalizarAtendimento = function() {
		if ($scope.atendimento.estado) {
			$http.post(
					"http://localhost:8080/hd/servicos/atendimento/finalizar",
					$scope.atendimentoSalvo).success(function(data) {
				store.remove('atendimento');
				store.set('tempoUltimoAtendimento', data);
				$scope.tempoUltimoAtendimento = data;
				$scope.definirNovoAtendimento();
			})

		}
	}

	$scope.atualizarCliente = function() {
		if ($scope.atendimento.pessoa1 != ''
				|| $scope.atendimento.pessoa2 != '') {
			if ($scope.atendimento.pessoa1 != '') {
				if ($scope.atendimentoSalvo.cliente1 == null) {
					$scope.atendimentoSalvo.cliente1 = {
						nome : $scope.atendimento.pessoa1,
						email : $scope.atendimento.email1,
						telefone : $scope.atendimento.fone1,
						cpf : '',
						rg : ''
					}
				} else {
					$scope.atendimentoSalvo.cliente1.nome = $scope.atendimento.pessoa1;
					$scope.atendimentoSalvo.cliente1.email = $scope.atendimento.email1;
					$scope.atendimentoSalvo.cliente1.telefone = $scope.atendimento.fone1;
					$scope.atendimentoSalvo.cliente1.cpf = '';
					$scope.atendimentoSalvo.cliente1.rg = '';
				}
			}
			if ($scope.pessoa2 != '') {
				if ($scope.atendimentoSalvo.cliente2 == null) {
					$scope.atendimentoSalvo.cliente2 = {
						nome : $scope.atendimento.pessoa2,
						email : $scope.atendimento.email2,
						telefone : $scope.atendimento.fone2,
						cpf : '',
						rg : ''
					}
				} else {
					$scope.atendimentoSalvo.cliente2.nome = $scope.atendimento.pessoa2;
					$scope.atendimentoSalvo.cliente2.email = $scope.atendimento.email2;
					$scope.atendimentoSalvo.cliente2.telefone = $scope.atendimento.fone2;
					$scope.atendimentoSalvo.cliente2.cpf = '';
					$scope.atendimentoSalvo.cliente2.rg = '';
				}
			}
		}
		store.set('atendimento', $scope.atendimentoSalvo);
		$scope.marcarPessoa();
	}

	$scope.marcarPessoa = function() {
		if ($scope.atendimento.estado) {
			$http.post("http://localhost:8080/hd/servicos/atendimento/atualizar",
					$scope.atendimentoSalvo).success(function(data) {

			})
		}
	}

	$scope.init = function() {
		
		$scope.definirNovoAtendimento();
		
		$scope.atendimentoSalvo = store.get('atendimento');
		if ($scope.atendimentoSalvo != null) {
			$scope.atendimento.numero = $scope.atendimentoSalvo.numero;
			$scope.atendimento.dataInicio =  moment(new Date($scope.atendimentoSalvo.dataInicio)).format('DD/MM/YYYY HH:mm:ss');
			$scope.atendimento.estado = true;
			$scope.atendimento.label = !$scope.atendimento.estado ? 'Iniciar atendimento'
					: 'Finalizar atendimento'

			if ($scope.atendimentoSalvo.cliente1 != null) {
				$scope.atendimento.pessoa1 = $scope.atendimentoSalvo.cliente1.nome;
				$scope.atendimento.email1 = $scope.atendimentoSalvo.cliente1.email;
				$scope.atendimento.fone1 = $scope.atendimentoSalvo.cliente1.telefone;	
			}			
			if ($scope.atendimentoSalvo.cliente2 != null) {
				$scope.atendimento.pessoa2 = $scope.atendimentoSalvo.cliente2.nome;
				$scope.atendimento.email2 = $scope.atendimentoSalvo.cliente2.email;
				$scope.atendimento.fone2 = $scope.atendimentoSalvo.cliente2.telefone;	
			}
		}
		$scope.tempoUltimoAtendimento = store.get('tempoUltimoAtendimento');
	}

	$scope.init();
}