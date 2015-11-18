'use strict';

var app = angular.module('location', [
	'ngRoute','rentControllers']);

app.config(['$routeProvider',
	function($routeProvider) {
		$routeProvider.
			when('/renting', {
				templateUrl: 'partials/menu.html'
			}).
			when('/renting/rent', {
				templateUrl: 'partials/rent.html',
				controller: 'rentController'
			}).
			when('/renting/getBack', {
				templateUrl: 'partials/getBack.html',
				controller: 'getBackController'
			}).
			otherwise({
				redirectTo: '/renting'
			});
}]);
