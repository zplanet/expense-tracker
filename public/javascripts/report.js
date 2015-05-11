(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.expenses = [];
		$scope.errorMessage = '';

		$scope.warningMessage = "Loading...";

		$http.get('/expenses')
		.success(function(data, status, headers, config) {
			$scope.expenses = data;
			$scope.warningMessage = "";
		})
		.error(function(data, status, headers, config) {
			$scope.warningMessage = "";
			$scope.errorMessage = data;
			$timeout(function(){$scope.errorMessage = '';}, 2000);
		});
	}]);
})();