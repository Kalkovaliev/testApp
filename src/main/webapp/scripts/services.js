'use strict';

/* Services */

storeApp.factory('LanguageService', function($http, $translate, LANGUAGES) {
	return {
		getBy : function(language) {
			if (language == undefined) {
				language = $translate.storage().get('NG_TRANSLATE_LANG_KEY');
			}
			if (language == undefined) {
				language = 'en';
			}

			var promise = $http.get('/i18n/' + language + '.json').then(
					function(response) {
						return LANGUAGES;
					});
			return promise;
		}
	};
});

storeApp.factory('Register', function($resource) {
	return $resource('app/rest/register', {}, {});
});

storeApp.factory('Activate', function($resource) {
	return $resource('app/rest/activate', {}, {
		'get' : {
			method : 'GET',
			params : {},
			isArray : false
		}
	});
});

storeApp.factory('Account', function($resource) {
	return $resource('app/rest/account', {}, {});
});





storeApp.factory('orderProduct', [
		'$log',
		'$timeout',
		'$q',
		'$http',
		'$resource',
		function($log, $timeout, $q, $http, $resource) {
			var productItems = [];
			var productItemsIdSequence = 0;

			var service = {
				save : saveFn,
				update : updateFn,
				getById : getByIdFn,
				getAll : getAllFn,
				remove : removeFn

			};
			return service;

			function saveFn(groupEntity) {
				var entityForSave, invalidMessage;
				var deferred = $q.defer();

				if (groupEntity.number === undefined) {
					$timeout(function() {
						entityForSave = angular.copy(groupEntity);
						invalidMessage = validateGroup(entityForSave);
						if (invalidMessage === null) {
							entityForSave.number = ++productItemsIdSequence;
							entityForSave.totalPrice = entityForSave.price
									* entityForSave.quantity;
							productItems.push(entityForSave);
							$log.debug('saving', entityForSave);
							deferred.resolve(entityForSave);
						} else {
							$log.debug('saving invalid:', invalidMessage);
							deferred.reject({
								message : invalidMessage
							});
						}
					}, 100);
					return deferred.promise;
				} else {
					return updateFn(groupEntity);
				}
				$log.debug('in save fn');

			}

			function validateGroup(entity) {
				if (entity.name === null || entity.name === undefined
						|| typeof entity.name !== 'string'
						|| entity.name.length === 0) {
					return 'Invalid name for group';
				}
				return null;
			}

			function updateFn(groupEntity) {
				var deferred = $q.defer();
				productItems.push(groupEntity);
				deferred.resolve(groupEntity);
				return deferred.promise;

			}

			function getByIdFn(groupId) {
				var index;
				var deferred = $q.defer();

				$timeout(function() {
					$log.debug('get by id: ', groupId);
					index = groupId;
					if (index === -1) {
						deferred.resolve(null);
					} else {
						deferred.resolve(productItems[index]);
					}
				}, 100);
				return deferred.promise;

			}

			function getAllFn() {

				var deferred = $q.defer();
				$timeout(function() {
					$log.debug('getAll');
					deferred.resolve(angular.copy(productItems));
				}, 100);
				return deferred.promise;
			}

			function removeFn(groupEntity) {
				var deferred = $q.defer();
				$timeout(function() {
					var index = findIndexById(groupEntity.number);
					if (index !== -1) {
						productItems.splice(index, 1);
					}
					$log.debug('remove', groupEntity);
					deferred.resolve();
				}, 100);
				return deferred.promise;

			}

			function findIndexById(groupId) {
				var result = -1, item;

				$log.debug('get index by id: ', groupId);
				for (var i = 0; i < productItems.length; i++) {
					item = productItems[i];
					if (item.number === groupId) {
						result = i;
						break;
					}
				}
				return result;
			}

		} ]);



storeApp
		.factory(
				'AdminPanelService',
				[
						'$log',
						'$timeout',
						'$q',
						'$http',
						'$resource',
						function($log, $timeout, $q, $http, $resource) {
							var groupsList = [];
							var groupIdSequence = 0;

							var service = {
								save : saveFn,
								update : updateFn,
								getById : getByIdFn,
								getAll : getAllFn,
								remove : removeFn
							};

							return service;

							function removeFn(groupEntity) {
								var deferred = $q.defer();

								var id = groupEntity.id;
								if (id !== -1) {
									groupsList.splice(id, 1);
									// brisenje na element

									var promise = $http
											.post('app/rest/removeFirma/' + id)
											.then(
													function(response) {
														deferred
																.resolve(response.data);
													},
													function(errResponse) {
														console
																.log('Error while removing Firm:'
																		+ errResponse.Text);
														deferred
																.reject(errResponse);
													});
								}
								$log.debug('remove', groupEntity);
								// deferred.resolve();
								return deferred.promise;

							}

							function getAllFn() {
								var promise = $http
										.get('app/rest/getAllFirms')
										.then(
												function(response) {
													groupIdSequence = response.data.length;
													return response.data;
												},
												function errorCallback(response) {
													// called asynchronously if
													// an error occurs
													// or server returns
													// response with an error
													// status.
													promise = response;
												});
								return promise;
							}
							function saveFn(groupEntity) {
								var entityForSave, invalidMessage;
								var deferred = $q.defer();
								var firm = groupEntity;

								if (groupEntity.id === undefined) {
									$timeout(
											function() {
												entityForSave = angular
														.copy(groupEntity);
												invalidMessage = validateGroup(firm);
												if (invalidMessage === null) {
													$http
															.post(
																	'app/rest/insertFirm',
																	firm)
															.then(
																	function(
																			response) {
																		deferred
																				.resolve(response.data);
																	},
																	function(
																			errResponse) {
																		console
																				.log('Error while inserting firm:'
																						+ errResponse.Text);
																		deferred
																				.reject(errResponse);
																	});

													// entityForSave.id =
													// ++groupIdSequence;
													// groupsList.push(entityForSave);
													// $log.debug('saving',
													// entityForSave);
													// deferred.resolve(entityForSave);
												} else {
													$log.debug(
															'saving invalid:',
															invalidMessage);
													deferred
															.reject({
																message : invalidMessage
															});
												}
											}, 100);
									return deferred.promise;
								} else {
									return updateFn(groupEntity);
								}
								$log.debug('in save fn');

							}

							function validateGroup(entity) {
								if (entity.name === null
										|| entity.name === undefined
										|| typeof entity.name !== 'string'
										|| entity.name.length === 0) {
									return 'Invalid name for firm';
								}
								return null;
							}

							function updateFn(groupEntity) {

								var deferred = $q.defer();
								if (groupEntity.id === undefined) {
									return saveFn(groupEntity);
								} else {
									$timeout(
											function() {
												$http
														.post(
																'app/rest/updateFirm',
																groupEntity)
														.then(
																function(
																		response) {
																	deferred
																			.resolve(response.data);

																	// angular.extend(response.data,groupEntity);
																},
																function(
																		errResponse) {
																	console
																			.log('Error while updating Firm:'
																					+ errResponse.Text);
																	deferred
																			.reject(errResponse);
																});
											}, 100);
									return deferred.promise;
									// getByIdFn(groupEntity.id)
									// .then(function (savedEntity) {
									// angular.extend(savedEntity, groupEntity);
									// $log.debug("merged entity", savedEntity);
									// $log.debug('updating', savedEntity);
									// deferred.resolve(savedEntity);
									// });

									// return deferred.promise;
								}

							}

							function getByIdFn(groupId) {
								var index;
								var deferred = $q.defer();

								$timeout(function() {
									$log.debug('get by id: ', groupId);
									index = findIndexById(groupId);
									if (index === -1) {
										deferred.resolve(null);
									} else {
										deferred.resolve(groupsList[index]);
									}
								}, 100);
								return deferred.promise;

							}

							/*
							 * function getAllFn() {
							 * 
							 * var deferred = $q.defer(); $log.debug('getAll');
							 * $resource('app/rest/getAllFirms', {}, { 'get': {
							 * method: 'GET', params: {}, isArray: true} })
							 * deferred.resolve(angular.copy(groupsList));
							 * 
							 * return deferred.promise; }
							 */

							// function removeFn(groupEntity) {
							// var deferred = $q.defer();
							// $timeout(function () {
							// var index = findIndexById(groupEntity.id);
							// if (index !== -1) {
							// groupsList.splice(index, 1);
							// //brisenje na element
							// $http,delete('app/rest/removeFirma/{id}',index)
							// .then(
							// function (response) {
							// deferred.resolve(response.data);
							// },
							// function(errResponse){
							// console.log('Error while Deleting
							// Firm:'+errResponse.Text);
							// deferred.reject(errResponse);
							// });
							//          
							// }
							// $log.debug('remove', groupEntity);
							// deferred.resolve();
							// }, 100);
							// return deferred.promise;
							//
							// }
							function findIndexById(groupId) {
								var result = -1, item;

								$log.debug('get index by id: ', groupId);
								for (var i = 0; i < groupsList.length; i++) {
									item = groupsList[i];
									if (item.id === groupId) {
										result = i;
										break;
									}
								}
								return result;
							}

						} ]);

storeApp.factory('Password', function($resource) {
	return $resource('app/rest/account/change_password', {}, {});
});

storeApp.factory('Sessions', function($resource) {
	return $resource('app/rest/account/sessions/:series', {}, {
		'get' : {
			method : 'GET',
			isArray : true
		}
	});
});

storeApp.factory('MetricsService', function($resource) {
	return $resource('metrics/metrics', {}, {
		'get' : {
			method : 'GET'
		}
	});
});

storeApp.factory('ThreadDumpService', function($http) {
	return {
		dump : function() {
			var promise = $http.get('dump').then(function(response) {
				return response.data;
			});
			return promise;
		}
	};
});

storeApp.factory('HealthCheckService', function($rootScope, $http) {
	return {
		check : function() {
			var promise = $http.get('health').then(function(response) {
				return response.data;
			});
			return promise;
		}
	};
});

storeApp.factory('LogsService', function($resource) {
	return $resource('app/rest/logs', {}, {
		'findAll' : {
			method : 'GET',
			isArray : true
		},
		'changeLevel' : {
			method : 'PUT'
		}
	});
});

storeApp.factory('AuditsService', function($http) {
	return {
		findAll : function() {
			var promise = $http.get('app/rest/audits/all').then(
					function(response) {
						return response.data;
					});
			return promise;
		},
		findByDates : function(fromDate, toDate) {
			var promise = $http.get('app/rest/audits/byDates', {
				params : {
					fromDate : fromDate,
					toDate : toDate
				}
			}).then(function(response) {
				return response.data;
			});
			return promise;
		}
	}
});

storeApp.factory('Session', function() {
	this.create = function(login, firstName, lastName, email, userRoles) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userRoles = userRoles;
	};
	this.invalidate = function() {
		this.login = null;
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		this.userRoles = null;
	};
	return this;
});

storeApp
		.factory(
				'AuthenticationSharedService',
				function($rootScope, $http, authService, Session, Account) {
					return {
						login : function(param) {
							var data = "j_username=" + param.username
									+ "&j_password=" + param.password
									+ "&_spring_security_remember_me="
									+ param.rememberMe + "&submit=Login";
							$http
									.post(
											'app/authentication',
											data,
											{
												headers : {
													"Content-Type" : "application/x-www-form-urlencoded"
												},
												ignoreAuthModule : 'ignoreAuthModule'
											})
									.success(
											function(data, status, headers,
													config) {
												Account
														.get(function(data) {
															Session
																	.create(
																			data.login,
																			data.firstName,
																			data.lastName,
																			data.email,
																			data.roles);
															$rootScope.account = Session;
															authService
																	.loginConfirmed(data);
														});
											})
									.error(
											function(data, status, headers,
													config) {
												$rootScope.authenticationError = true;
												Session.invalidate();
											});
						},
						valid : function(authorizedRoles) {

							$http
									.get(
											'protected/authentication_check.gif',
											{
												ignoreAuthModule : 'ignoreAuthModule'
											})
									.success(
											function(data, status, headers,
													config) {
												if (!Session.login) {
													Account
															.get(function(data) {
																Session
																		.create(
																				data.login,
																				data.firstName,
																				data.lastName,
																				data.email,
																				data.roles);
																$rootScope.account = Session;

																if (!$rootScope
																		.isAuthorized(authorizedRoles)) {
																	event
																			.preventDefault();
																	// user is not allowed
																	$rootScope
																			.$broadcast("event:auth-notAuthorized");
																}

																$rootScope.authenticated = true;
															});
												}
												$rootScope.authenticated = !!Session.login;
											})
									.error(
											function(data, status, headers,
													config) {
												$rootScope.authenticated = false;
											});
						},
						isAuthorized : function(authorizedRoles) {
							if (!angular.isArray(authorizedRoles)) {
								if (authorizedRoles == '*') {
									return true;
								}

								authorizedRoles = [ authorizedRoles ];
							}

							var isAuthorized = false;
							angular
									.forEach(
											authorizedRoles,
											function(authorizedRole) {
												var authorized = (!!Session.login && Session.userRoles
														.indexOf(authorizedRole) !== -1);

												if (authorized
														|| authorizedRole == '*') {
													isAuthorized = true;
												}
											});

							return isAuthorized;
						},
						logout : function() {
							$rootScope.authenticationError = false;
							$rootScope.authenticated = false;
							$rootScope.account = null;

							$http.get('app/logout');
							Session.invalidate();
							authService.loginCancelled();
						}
					};
				});
