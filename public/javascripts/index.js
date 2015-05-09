(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.email = 'abc@abc.com';
		$scope.password = 'a';
		$scope.errorMessage = '';
		$scope.successMessage = '';
		$scope.user = '';

		$scope.signin = function() {

			$http.get(
				'/users/' + $scope.email, 
				{headers: { 'Authorization': $scope.password }})
			.success(function(data, status, headers, config) {
				$scope.user = $scope.email;
				$scope.successMessage = 'Welcome ' + $scope.email;
				$timeout(function(){$scope.successMessage = '';}, 2000);
			})
			.error(function(data, status, headers, config) {
				$scope.errorMessage = data;
				$timeout(function(){$scope.errorMessage = '';}, 2000);
			});
		}
	}]);
})();