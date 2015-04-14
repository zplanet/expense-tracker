(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.email = '';
		$scope.password1 = '';
		$scope.password2 = '';
		$scope.errorMessage = '';
		$scope.successMessage = '';

		$scope.signup = function() {

			$http.post(
				'/createuser', 
				{email: $scope.email, password1: $scope.password1, password2: $scope.password2})
			.success(function(data, status, headers, config) {
				$scope.successMessage = data;
				$timeout(function(){document.location.href = '/';}, 2000);
			})
			.error(function(data, status, headers, config) {
				if (510 === status) { // password mismatch
					$scope.password1 = '';
					$scope.password2 = '';
				}
				$scope.errorMessage = data;
				$timeout(function(){$scope.errorMessage = '';}, 2000);
			});
		}
	}]);
})();