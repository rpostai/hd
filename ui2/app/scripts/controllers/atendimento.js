/**
 * Created by rodrigo.postai on 06/02/2015.
 */
function AtendimentoController($scope) {

  $scope.modelos = [
    {
      id: 1,
      nome: 'Ana Médio'
    },
    {
      id:2,
      nome: 'Paula Grande'
    },
    {
      id:3,
      nome: 'Karine'
    }
  ];

  $scope.papeis = [
    {
      id: 1,
      nome: 'Opalina Linear 240 g'
    },
    {
      id: 2,
      nome: 'Color Plus Metalizado Aspen 250g'
    }
  ];

  $scope.impressoes = [
    {
      id: 1,
      nome: '1 Face PB'
    },
    {
      id:2,
      nome: '2 Faces PB'
    }
  ]

  $scope.orcamento = {
    modelo: null,
    quantidade: 0,
    papelEnvelope: null,
    papelInterno: null
  }

  $scope.getModelos = function() {
    return $scope.modelos;
  }

  $scope.getImpressoes = function() {
    return $scope.impressoes;
  }

  $scope.getPapeis = function() {
    return $scope.papeis;
  }

}
