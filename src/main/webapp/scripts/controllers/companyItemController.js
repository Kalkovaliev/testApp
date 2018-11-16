/**
 * 
 */
'use strict';

storeApp
		.controller(
				'companyItemController',
				[
						'$scope',
						'$log',
						'companyService',
						'$timeout',
						'$window',
						'$routeParams',
						function($scope, $log, companyService, $timeout,
								$window, $routeParams) {

							$scope.cities = [];
							$scope.clearFields = clearFields;
							$scope.success = null;
							$scope.error = null;
							var companyId = $routeParams.id;

							if ($scope.company == null) {
								$scope.company = {};
							}

							$scope.loadCompany = function(companyId) {
								companyService.get({
									id : companyId
								}, function(data, httpResponse) {
									$scope.company = data;

								}, function(httpResponse) {
									if (httpResponse.status == 403) {
										$scope.error403 = "ERROR";
									}
									$scope.notAuthorized = false;
								});
							};

							if (companyId != null) {
								$scope.loadCompany(companyId);
							}

							$scope.saveCompany = function() {
								if (companyId != null) {
									companyService
											.update(
													$scope.company,
													function(value,
															responseHeaders) {
														$scope.error = null;
														$scope.success = 'Successfully updated company with id:'
																+ companyId;
														$scope.company = companyService
																.get({
																	id : companyId
																});
														$scope.clearMesssages();
													},
													function(httpResponse) {
														$scope.success = null;
														if (httpResponse.status === 409) {

															$scope.error = "Company exists!!";
														} else {
															$scope.error = "ERROR";

														}
														$scope.clearMesssages();
													});
								} else {

									// $scope.doNotMatch = null;
									companyService
											.save(
													$scope.company,
													function(value,
															responseHeaders) {
														$scope.error = null;

														$scope.success = 'Successfully added new Company';
														$scope.clearMesssages();
													},
													function(httpResponse) {
														$scope.success = null;
														if (httpResponse.status === 409) {

															$scope.error = "Company exists!!";
														} else {
															$scope.error = "ERROR";
														}
														$scope.clearMesssages();
													});
								}

							}

							function clearFields() {
								$scope.company = {};
								$scope.company.city = {};

							}

							$scope.loadCities = function loadCities() {
								companyService.getCities(function(data) {
									$scope.cities = data;
								});

							};

							$scope.goBack = function() {
								$window.history.back();
							};

							$scope.loadCities();

							$scope.clearMesssages = function() {
								$timeout(function() {
									$scope.success = null;
									$scope.error = null;

								}, 10000);
							};

						} ]);
