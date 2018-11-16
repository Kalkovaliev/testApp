'use strict';

storeApp
		.controller(
				'productItemController',
				[
						'$scope',
						'$log',
						'companyService',
						'productService',
						'$timeout',
						'$window',
						'$routeParams',
						function($scope, $log, companyService,
								productService, $timeout, $window,
								$routeParams) {

							$scope.companies = [];
							$scope.clearFields = clearFields;
							$scope.success = null;
							$scope.error = null;
							$scope.product = {};

							var productId = $routeParams.id;

							if ($scope.product == null) {
								$scope.product = {};
							}

							$scope.loadCompanies = function loadCompanies() {

								companyService.getAll(function(data) {
									$scope.companies = data;
								});
							};

							$scope.loadCompanies();

							$scope.loadProduct = function(productId) {
								productService.get({
									id : productId
								}, function(data, httpResponse) {
									$scope.product = data;

								}, function(httpResponse) {
									if (httpResponse.status == 403) {
										$scope.error403 = "ERROR";
									}
									$scope.notAuthorized = false;
								});
							};

							if (productId != null) {
								$scope.loadProduct(productId);
							}

							$scope.saveProduct = function() {
								if (productId != null) {
									productService
											.update(
													$scope.product,
													function(value,
															responseHeaders) {
														$scope.error = null;
														$scope.success = 'Successfully updated product with id:'
																+ productId;
														$scope.product = productService
																.get({
																	id : productId
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

									productService
											.save(
													$scope.product,
													function(value,
															responseHeaders) {
														$scope.error = null;

														$scope.success = 'Successfully added new Product';
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

							$scope.goBack = function() {
								$window.history.back();
							};

							function clearFields() {

								$scope.product = {};
							}
							$scope.clearMesssages = function() {
								$timeout(function() {
									$scope.success = null;
									$scope.error = null;

								}, 10000);
							};

						} ]);
