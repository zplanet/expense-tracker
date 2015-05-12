(function(){

	google.load("visualization", "1.1", {packages:["bar"]});

	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', '$window', function($scope, $http, $timeout, $window) {

		$scope.errorMessage = '';
		$scope.chartStyle = $window.matchMedia("(min-width: 700px)").matches ? 'large-chart' : 'small-chart';
		$scope.chart = new google.charts.Bar($window.document.getElementById('chart'));

		$scope.drawChart = function() {

			$scope.warningMessage = 'Loading...';

			$http.get('/expenses/graph/monthly')
			.success(function(data, status, headers, config) {

				$scope.warningMessage = '';
				
				if (2 > data.length) {
					$scope.warningMessage = 'No Data';
					$timeout(function(){$scope.warningMessage = '';}, 2000);
					return;
				}
				
				var json = google.visualization.arrayToDataTable(data);

				$scope.chart.draw(json, {title: 'Monthly Expense', legend: { position: 'none' }});
			})
			.error(function(data, status, headers, config) {
				$scope.warningMessage = '';
				$scope.errorMessage = data;
				$timeout(function(){$scope.errorMessage = '';}, 2000);
			});
		}

		$window.addEventListener("resize", function() {
			if ($window.matchMedia("(min-width: 700px)").matches) {
				if ('small-chart' === $scope.chartStyle) {
					$scope.chartStyle = 'large-chart';
					$scope.drawChart();
				}
			}
			else {
				if ('large-chart' === $scope.chartStyle) {
					$scope.chartStyle = 'small-chart';
					$scope.drawChart();
				}
			}
		});

		google.setOnLoadCallback($scope.drawChart);
	}]);
})();