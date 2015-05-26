(function(){

	angular.module('ExpenseTrackerApp', ['ngRoute', 'ui.chart'])

	.controller('MainController', function($scope, $route, $routeParams, $location, $http, $timeout) {

		$scope.$route = $route;
		$scope.$location = $location;
		$scope.$routeParams = $routeParams;

		$scope.errorMessage = '';
		$scope.warningMessage = '';
		$scope.successMessage = '';

		$scope.email = 'abc@abc.com';
		$scope.password = 'a';
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

		$scope.login = function() {

			$http.post('/users/login', {email: $scope.email, password: $scope.password})
			.success(function(data, status, headers, config) {
				$scope.user = $scope.email;
				$scope.showSuccess('Welcome ' + $scope.email);
				$http.defaults.headers.common.Authorization = data;
			})
			.error(function(data, status, headers, config) {
				$scope.showError(data);
			});
		}

		$scope.logout = function() {
			$scope.user = '';
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
				$scope.$parent.email = $scope.email;
				$scope.$parent.showSuccess(data, '/');
			})
			.error(function(data, status, headers, config) {
				$scope.$parent.showError(data);
			});
		}
	})

	.controller('ExpenseController', function($scope, $http) {

		$scope.date = new Date();

		$scope.add = function() {
			$http.post(
				'/expenses', 
				{amount: $scope.amount, date: $scope.date.toISOString(), note: $scope.note})
			.success(function(data, status, headers, config) {
				$scope.$parent.showSuccess(data);
				$scope.date = new Date();
				$scope.amount = null;
				$scope.note = "";
			})
			.error(function(data, status, headers, config) {
				$scope.$parent.showError(data);
			});
		}
	})

	.controller('ReportController', function($scope, $http) {

		$scope.expenses = [];

		$scope.$parent.warningMessage = "Loading...";

		$http.get('/expenses')
		.success(function(data, status, headers, config) {
			$scope.$parent.warningMessage = "";
			$scope.expenses = data;
		})
		.error(function(data, status, headers, config) {
			$scope.$parent.warningMessage = "";
			$scope.$parent.showError(data);
		});
	})

	.controller('GraphController', function($scope, $http) {

		$scope.expenses = [];

		$scope.chartOptions = { 
			//title: 'Spendings',
			series:[{renderer: jQuery.jqplot.BarRenderer}],
			axesDefaults: {
				tickRenderer: jQuery.jqplot.CanvasAxisTickRenderer,
				tickOptions: {
					angle: -30,
					fontSize: '10pt'
				}
			},
			axes: {
				xaxis: {
					renderer: jQuery.jqplot.CategoryAxisRenderer
				},
				yaxis: {
					autoscale: true
				}
			}
		};

		$scope.$parent.warningMessage = "Loading...";

		$http.get('/expenses/graph/monthly')
		.success(function(data, status, headers, config) {
			$scope.$parent.warningMessage = '';
			$scope.expenses = data;
		})
		.error(function(data, status, headers, config) {
			$scope.$parent.warningMessage = '';
			$scope.$parent.showError(data);
		});
	})

	.config(function($routeProvider, $locationProvider) {
		
		$routeProvider
			.when('/', {
				templateUrl: '/main'
			})
			.when('/signup', {
				templateUrl: '/signup',
				controller: 'SignupController'
			})
			.when('/expense', {
				templateUrl: '/expense',
				controller: 'ExpenseController'
			})
			.when('/report', {
				templateUrl: '/report',
				controller: 'ReportController'
			})
			.when('/graph', {
				templateUrl: '/graph',
				controller: 'GraphController'
			});

		$locationProvider.html5Mode({
			enabled: true,
			requireBase: false
		});
	});
})();