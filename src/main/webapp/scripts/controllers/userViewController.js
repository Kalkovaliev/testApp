/**
 * 
 */
'use strict';

storeApp
		.controller(
				'userViewController',
				[
						'$scope',
						'$log',
						'userService',
						'$timeout',
						'$window',
						'$translate',
						'$routeParams',
						'Register',
						function($scope, $log, userService, $timeout, $window,$translate,
								$routeParams,Register) {

							$scope.clearFields = clearFields;
							$scope.success = null;
							$scope.error = null;
							$scope.userLogin = $routeParams.login;
							var userLogin = $routeParams.login;

							if ($scope.user == null) {
								$scope.user = {};
							}

							$scope.loadUser = function(userLogin) {
								userService.get({
									login : userLogin
								}, function(data, httpResponse) {
									$scope.user = data;

								}, function(httpResponse) {
									if (httpResponse.status == 403) {
										$scope.error403 = "ERROR";
									}
									$scope.notAuthorized = false;
								});
							};

							if (userLogin != null) {
								$scope.loadUser(userLogin);
							}

							function clearFields() {
								$scope.registerAccount = {};

							}

							$scope.goBack = function() {
								$window.history.back();
							};
							
							
							$scope.saveUser = function() {
								if(userLogin != null) {
								userService.update($scope.user, 
											function(value, responseHeaders) {
												$scope.error = null;
												$scope.success = 'Successfully updated user:'+userLogin;
												$scope.user = userService.get({login:userLogin});
												$scope.clearMesssages();
											},
											function(httpResponse){
												$scope.success = null;
												if(httpResponse.status === 409) {
												
													$scope.error = "User exists!!";
												} else {
													$scope.error = "ERROR";
												
												}
												$scope.clearMesssages();
											});
								} else {
								
								userService.save($scope.user, 
											function(value, responseHeaders){
												$scope.error = null;
												$scope.success = 'Successfully added new User';
												$scope.clearMesssages();
											},
											function (httpResponse){
												$scope.success = null;
												if(httpResponse.status === 409) {
												
													$scope.error = "User exists!!";
												} else {
													$scope.error = "ERROR";
												}							
												$scope.clearMesssages();
											});
								}	
								
							}
			
					
							$scope.clearMesssages = function() {
								$timeout(function() {
									$scope.success = null;
									$scope.error = null;

								}, 10000);
							};

						} ]);
