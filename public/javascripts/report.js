(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.expenses = [];
		$scope.errorMessage = '';
		$scope.successMessage = '';

		$http.get('/expenses')
		.success(function(data, status, headers, config) {
			//$scope.successMessage = data;
			//$timeout(function(){$scope.successMessage = '';}, 2000);
			$scope.expenses = data;
		})
		.error(function(data, status, headers, config) {
			//$scope.errorMessage = data;
			//$timeout(function(){$scope.errorMessage = '';}, 2000);
		});
	}]);
})();