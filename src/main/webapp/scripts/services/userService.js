'use strict';

storeApp.factory('userService', [ '$resource', function($resource) {
	return $resource('/store/app/rest/users', {}, {
		paged : {
			url : '/store/app/rest/users/getUsers',
			method : 'GET',
			isArray : false

		},
		get : {
			url : '/store/app/rest/user/:login',
			method : 'GET',
			isArray : false
		},
		remove : {
			url : '/store/app/rest/user/removeUser/:login',
			method : 'DELETE'

		},
		save : {
			url : '/store/app/rest/user/saveUser',
			method : 'POST'
		

		},
		update : {
			url : '/store/app/rest/user/updateUser',
			method : 'POST'
	
			
		}
	});
} ]);