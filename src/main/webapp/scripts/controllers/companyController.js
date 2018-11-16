/**
 * 
 */
'use strict'

storeApp
		.controller(
				'companyController',
				[
						'$scope',
						'$log',
						'companyService',
						'$timeout',
						function($scope, $log,
								companyService, $timeout) {

							// $scope.search = search;
							$scope.clear = clear;
							$scope.companies = [];
							$scope.company = {};
							$scope.cities = [];
							$scope.errorNoData = null;
							$scope.currentPage = 1;
							$scope.itemsPerPage = 10;
							$scope.maxSize = 10;
							$scope.companyObj = {};

							$scope.companyName = "";
							$scope.companyCity = {};

							$scope.loadCities = function loadCities() {
								companyService.getCities(function(data) {
									$scope.cities = data;
								});

							};

							$scope.loadPage = function() {
								companyService
										.paged(
												{
													companyName : ($scope.companyName ? $scope.companyName
															: ""),
													cityId : ($scope.companyCity.id ? $scope.companyCity.id
															: 0),
													count : $scope.itemsPerPage,
													page : $scope.currentPage

												},
												function(response) {
													$scope.companies = response.content;
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

							$scope.loadCities();

							$scope.pageChanged = function() {
								$scope.loadPage();

							}
							function clear() {

								$scope.companyName = null;
								$scope.companyCity = {};
								$scope.loadPage();
							}

							$scope.deleteCompany = function(companyId) {
								companyService.remove({
									id : companyId
								}, function(responseHeaders) {

									$scope.saveOkMsg = 'Company with id:'
											+ companyId + ' is removed';
									$scope.clearMesssages();
									$scope.loadPage();
								}, function(httpResponse) {

									$scope.saveErrMsg = null;
									if (httpResponse.status == 409) {
										$scope.saveErrMsg = "ERROR";
									} else {
										$scope.saveErrMsg = "ERROR";
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
