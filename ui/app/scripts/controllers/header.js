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
			origemContato: null,
			observacao: null,
			dataEvento: null,
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
			var url = "/servicos/atendimento/iniciar";
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
							$scope.atendimento.observacao = data.observacao;
							$scope.atendimento.dataEvento = data.dataEvento;
							
						}
						
						$http.get('/servicos/atendimento/origem').success(function(data) {
							$scope.origensContato = data;
						});
						
						$scope.atendimento.label = 'Finalizar atendimento';
					});
		}
	}

	$scope.finalizarAtendimento = function() {
		if ($scope.atendimento.estado) {
			$scope.atendimentoSalvo.origemContato=$scope.atendimento.origemContato;
			$http.post(
					"/servicos/atendimento/finalizar",
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
		$scope.atendimentoSalvo.observacao = $scope.atendimento.observacao;
		
		if ($('#data').val() != null && $('#data').val() != "") {
			var split = $('#data').val().split('/');
			novadata = split[1] + "/" +split[0]+"/"+split[2];
			data = new Date(novadata);
			$scope.atendimentoSalvo.dataEvento = data;
		}
		
		$scope.atendimentoSalvo.origemContato = $scope.atendimento.origemContato;
		store.set('atendimento', $scope.atendimentoSalvo);
		$scope.marcarPessoa();
	}

	$scope.marcarPessoa = function() {
		if ($scope.atendimento.estado) {
			$http.post("/servicos/atendimento/atualizar",
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