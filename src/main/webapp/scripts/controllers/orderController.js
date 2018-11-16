'use strict';

storeApp
		.controller(
				'orderController',
				[
						'$scope',
						'$log',
						'orderViewService',
						'companyService',
						'orderService',
						'$timeout',
						'$filter',
						'orderSearchService',
						function($scope, $log, orderViewService,
								companyService, orderService, $timeout, $filter,orderSearchService) {

						
							$scope.remove = remove;
							$scope.orders = [];
							$scope.errorNoData = null;
							$scope.companies = [];
							$scope.orderObj = {};
							$scope.orderDate = null;
							$scope.currentPage = 1;
							$scope.itemsPerPage = 10;
							$scope.maxSize = 10;
						    $scope.searchProductName = "";
							$scope.searchCompany = {};
							
							$scope.loadCompanies = function loadCompanies() {

								companyService.getAll(function(data){
									$scope.companies=data;
									
								});
										
							}

							$scope.loadCompanies();
							
							$scope.loadPage = function(){
								orderSearchService.paged({
								
									productName : ($scope.searchProductName ? $scope.searchProductName : ""),
									companyId   : ($scope.searchCompany.id ? $scope.searchCompany.id : 0),
									orderDate   : ($scope.orderDate ? $filter('date')($scope.orderDate, 'yyyy-MM-dd') : ""),
									count       :  $scope.itemsPerPage,
									page        :  $scope.currentPage
									
								
								
								
								}, function (response) {
									$scope.orders = response.content;
									 $scope.totalItems = response.totalElements;
									  if($scope.totalItems == 0){
											$scope.errorNoData = 'OK';
										}
										else $scope.errorNoData = null;
								},
								function (httpResponse){
									
								});
							};
									
							$scope.loadPage();
							
							$scope.search = function() {
								$scope.loadPage();
							};
							
						
							
							$scope.clearMesssages = function(){
								$timeout(function () {
									 $scope.saveErrMsg = null;
									 $scope.saveOkMsg = null;
									  
						        }, 10000);		
							};

							
							$scope.clear = function(){
								$scope.searchProductName = null;
								$scope.searchCompany = {};
								$scope.orderDate = null;
								$scope.loadPage();
							}
						
							function remove(entity) {


								orderViewService
										.getOrderProducts(entity.id)
										.then(
												function(data) {
													if (data !== null) {
														for (var j = 0; j < data.length; j++) {
															orderViewService
																	.removeOrderProduct(
																			data[j])
																	.then(
																			function(
																					data) {

																			});
														}
														orderViewService
																.removeOrder(
																		entity)
																.then(
																		successCallBack,
																		errorCallback);
														function successCallBack(
																data) {
															$scope.saveOkMsg = "Order with id "
																	+ entity.id
																	+ " is successfully removed";
															$scope.loadPage();
															$scope.clearMesssages();
														}

														function errorCallback(
																data) {
															$scope.saveErrMsg = "Invalid while removing order to the database due to: "
																	+ data.message;
															$scope.clearMesssages();
														}
													} else {
														orderViewService
																.removeOrder(
																		entity)
																.then(
																		successCallBack,
																		errorCallback);
														function successCallBack(
																data) {
															$scope.saveOkMsg = "Order with id "
																	+ entity.id
																	+ " is successfully removed";
															$scope.loadPage();
															$scope.clearMesssages();
														}

														function errorCallback(
																data) {
															$scope.saveErrMsg = "Invalid while removing order to the database due to: "
																	+ data.message;
															$scope.clearMesssages();
														}

													}

												});

							}

							// Delot za paginacija
							function pagination() {

								$scope.setPage = function(pageNo) {
									$scope.currentPage = pageNo;
								};

								$scope.pageChanged = function() {
									$log.log('Page changed to: '
											+ $scope.currentPage);
								};

							}

							$scope.format = 'dd.MM.yyyy';
							$scope.dt = {};
							$scope.dt.isOpen = false;

							$scope.dateOptions = {
								formatYear : 'yy',
								startingDay : 1
							};

							$scope.openCalendarFrom = function($event) {
								$event.preventDefault();
								$event.stopPropagation();
								
							};

							$scope.openCalendarTo = function($event) {
								$event.preventDefault();
								$event.stopPropagation();
								$scope.dt.isOpen = true;
							};

						} ]);
