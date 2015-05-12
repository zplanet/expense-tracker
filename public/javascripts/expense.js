(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.date = new Date();
		$scope.errorMessage = '';
		$scope.successMessage = '';

		$scope.add = function() {
			$http.post(
				'/expenses', 
				{amount: $scope.amount, date: $scope.date.toISOString(), note: $scope.note})
			.success(function(data, status, headers, config) {
				$scope.successMessage = data;
				$timeout(function(){$scope.successMessage = '';}, 2000);

				$scope.date = new Date();
				$scope.amount = null;
				$scope.note = "";
			})
			.error(function(data, status, headers, config) {
				$scope.errorMessage = data;
				$timeout(function(){$scope.errorMessage = '';}, 2000);
			});
		}
	}]);
})();