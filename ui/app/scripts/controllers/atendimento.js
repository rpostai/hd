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
  ];

  $scope.fitas = [
    {
      id: 1,
      nome: 'Cetim 3'
    },
    {
      id:2,
      nome: 'Cetim 5'
    }
  ];

  $scope.lacos = [
    {
      id: 1,
      nome: 'Laço Chanel Simples'
    },
    {
      id: 2,
      nome: 'Laço Chanel Duplo'
    }

  ];

  $scope.rendas = [
    {
      id: 1,
      nome: 'Renda Simples'
    }
  ]

  $scope.orcamento = {
    modelo: null,
    quantidade: 0,
    papelEnvelope: null,
    papelInterno: null,
    impressaoEnvelope: null,
    impressaoInterno:null,
    fita:null,
    laco: null,
    renda: null,
    impressaoNome: null,
    serigrafiaInterno: null,
    serigrafiaEnvelope: null,
    hotstamp: null,
    strass: null,
    ima: null,
    preco: 0
  };

  $scope.impressoesNome = [
    {
      id: 1,
      nome: 'Tag'
    }
  ]

  $scope.serigrafias = [
    {
      id: 1,
      nome: 'Serigrafia simples'
    }
  ]

  $scope.hotstamp = [
    {
      id:1,
      nome: 'Hot Stamp Prata'
    }
  ]

  $scope.strass = [
    {
      id: 1,
      nome: 'Strass'
    },
    {
      id: 2,
      nome: 'Meia Pérola'
    }
  ];

  $scope.imas = [
    {
      id: 1,
      nome: 'Ima simples'
    }
  ]

  $scope.getModelos = function() {
    return $scope.modelos;
  }

  $scope.getImpressoes = function() {
    return $scope.impressoes;
  }

  $scope.getPapeis = function() {
    return $scope.papeis;
  }

  $scope.getFitas = function() {
    return scope.fitas;
  }

  $scope.getLacos = function() {
    return scope.lacos;
  }

  $scope.getRendas = function() {
    return $scope.rendas;
  }

  $scope.getHotStamp = function() {
    return $scope.hotstamp;
  }

  $scope.getStrass = function() {
    return $scope.strass;
  }

}
