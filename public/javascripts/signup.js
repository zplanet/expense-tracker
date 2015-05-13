(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.email = '';
		$scope.password1 = '';
		$scope.password2 = '';
		$scope.errorMessage = '';
		$scope.successMessage = '';

		$scope.signup = function() {

			if ($scope.password1 !== $scope.password2) {
				$scope.errorMessage = 'Password does not match the confirm password.';
				$timeout(function(){$scope.errorMessage = '';}, 2000);
				$scope.password1 = '';
				$scope.password2 = '';
				return;
			}

			$http.post('/users', {email: $scope.email, password: $scope.password1})
			.success(function(data, status, headers, config) {
				$scope.successMessage = data;
				$timeout(function(){document.location.href = '/';}, 2000);
			})
			.error(function(data, status, headers, config) {
				$scope.errorMessage = data;
				$timeout(function(){$scope.errorMessage = '';}, 2000);
			});
		}
	}]);
})();