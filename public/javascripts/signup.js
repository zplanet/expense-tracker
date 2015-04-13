(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', function($scope, $http) {

		$scope.email = '';
		$scope.password1 = '';
		$scope.password2 = '';

		$scope.signup = function() {
			console.log($scope.email);
		}
	}]);
})();