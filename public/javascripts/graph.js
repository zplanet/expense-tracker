(function(){
	angular.module('ExpenseTrackerApp', [])
	.controller('MainController', ['$scope', '$http', '$timeout', function($scope, $http, $timeout) {

		$scope.expensesMonthly = [];
		$scope.errorMessage = '';
		$scope.warningMessage = 'Loading...';

		$http.get('/expenses/monthly')
		.success(function(data, status, headers, config) {
			$scope.warningMessage = '';
			$scope.expensesMonthly = data;
		})
		.error(function(data, status, headers, config) {
			$scope.warningMessage = '';
			$scope.errorMessage = data;
			$timeout(function(){$scope.errorMessage = '';}, 2000);
		});
	}]);
})();

	
	// google.load("visualization", "1.1", {packages:["bar"]});
	
	// google.setOnLoadCallback(drawChart);
	
	// function drawChart() {

	// 	var json = <?php echo getGraphData(getUserId($_COOKIE[LOGIN_COOKIE_NAME])); ?>;

	// 	if (2 > json.length) {
	// 		$('h1').show();
	// 		return;
	// 	}

	// 	var data = google.visualization.arrayToDataTable(json);

	// 	var options = {
	// 		title: 'Monthly Expense',
	// 		legend: { position: 'none' },
	// 	};

	// 	var chart = new google.charts.Bar(document.getElementById('chart_div'));
	// 	chart.draw(data, options);
	// }

	// var over700 = true;

	// $(window).resize(function() {
	// 	if (window.matchMedia("(min-width: 700px)").matches) {
	// 		if (!over700) {
	// 			drawChart();
	// 			over700 = true;
	// 		}
	// 	}
	// 	else {
	// 		if (over700) {
	// 			drawChart();
	// 			over700 = false;
	// 		}
	// 	}
	// });
