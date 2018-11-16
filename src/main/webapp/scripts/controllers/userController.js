/**
 * 
 */
'use strict'

storeApp.controller('userController', [ '$scope', '$log', 'userService',
		'$timeout', function($scope, $log, userService, $timeout) {

			// $scope.search = search;
			$scope.clear = clear;
			$scope.users = [];
			$scope.errorNoData = null;
			$scope.currentPage = 1;
			$scope.itemsPerPage = 10;
			$scope.maxSize = 10;

			$scope.userName = "";
			$scope.userEmail = "";

			$scope.loadPage = function() {
				userService.paged({
					userName : ($scope.userName ? $scope.userName : ""),
					userEmail : ($scope.userEmail ? $scope.userEmail : ""),
					count : $scope.itemsPerPage,
					page : $scope.currentPage

				}, function(response) {
					$scope.users = response.content;
					$scope.totalItems = response.totalElements;
					if ($scope.totalItems == 0) {
						$scope.errorNoData = 'OK';
					} else
						$scope.errorNoData = null;

				}, function(httResponse) {

				});

			}

			$scope.search = function() {
				$scope.loadPage();
			}

			$scope.loadPage();

			$scope.pageChanged = function() {
				$scope.loadPage();

			}
			function clear() {

				$scope.userName = "";
				$scope.userEmail = "";
				$scope.loadPage();
			}
			

			$scope.deleteUser = function(login) {
				userService.remove({
					login : login
				}, function(responseHeaders) {

					$scope.success = 'User:'
							+ login + ' is removed';
					$scope.clearMesssages();
					$scope.loadPage();
				}, function(httpResponse) {

					$scope.error = null;
					if (httpResponse.status == 409) {
						$scope.error = "ERROR";
					} else {
						$scope.error = "ERROR";
					}
					$scope.clearMesssages();
				});
			};

			$scope.clearMesssages = function() {
				$timeout(function() {
					$scope.saveErrMsg = null;
					$scope.saveOkMsg = null;

				}, 10000);
			};

		} ]);
