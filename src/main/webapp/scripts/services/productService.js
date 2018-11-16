'use strict';

storeApp.factory('productService', [ '$resource', function($resource) {
	return $resource('/store/app/rest/product', {}, {
		paged : {
			url : '/store/app/rest/product/getProducts',
			method : 'GET',
			isArray : false

		},
		remove : {
			url : '/store/app/rest/product/removeProduct/:id',
			method : 'DELETE'

		},
		get : {
			url : '/store/app/rest/product/:id',
			method : 'GET',
			isArray : false
		},
		update : {
			url : '/store/app/rest/product/update',
			method : 'POST'
		},
		save : {
			url : '/store/app/rest/product/save',
			method : 'POST'
		},
		getAll : {
			url : '/store/app/rest/product/getAllProducts',
			method : 'GET',
			isArray: true
		}
	});
} ]);