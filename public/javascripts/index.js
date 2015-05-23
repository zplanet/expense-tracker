(function(){
	angular.module('ExpenseTrackerApp', ['ngRoute'])
	.controller('MainController', function($scope, $route, $routeParams, $location, $http, $timeout) {

		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;

		$scope.errorMessage = '';
		$scope.successMessage = '';

		$scope.email = '';
		$scope.password = '';
		$scope.user = '';

		$scope.showError = function(msg) {
			$scope.errorMessage = msg;
			$timeout(function(){$scope.errorMessage = '';}, 2000);
		}

		$scope.showSuccess= function(msg, url) {
			$scope.successMessage = msg;
			$timeout(function(){
				$scope.successMessage = '';
				if ('undefined' !== typeof url) {
					$location.url(url);
				}
			}, 2000);
		}

		$scope.signin = function() {
			
			$http.post('/users/login', {email: $scope.email, password: $scope.password})
			.success(function(data, status, headers, config) {
				$scope.user = $scope.email;
				$scope.showSuccess('Welcome ' + $scope.email);
			})
			.error(function(data, status, headers, config) {
				$scope.showError(data);
			});
		}
	})
	.controller('SignupController', function($scope, $http) {

		$scope.email = '';
		$scope.password1 = '';
		$scope.password2 = '';

		$scope.signup = function() {

			if ($scope.password1 !== $scope.password2) {
				$scope.$parent.showError('Password does not match the confirm password.');
				$scope.password1 = '';
				$scope.password2 = '';
				return;
			}

			$http.post('/users', {email: $scope.email, password: $scope.password1})
			.success(function(data, status, headers, config) {
				$scope.$parent.showSuccess(data, '/');
			})
			.error(function(data, status, headers, config) {
				$scope.$parent.showError(data);
			});
		}
	})
	.config(function($routeProvider, $locationProvider) {
		$routeProvider
		.when('/', {
			templateUrl: '/main'
		})
		.when('/signup', {
			templateUrl: '/signup',
			controller: 'SignupController'
		});

		$locationProvider.html5Mode({
			enabled: true,
			requireBase: false
		});
	});
})();