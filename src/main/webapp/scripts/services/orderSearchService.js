
'use strict';


storeApp.factory('orderSearchService',['$resource',function($resource) {
	return $resource('/store/app/rest/order',{},{
				paged: {
						url:'/store/app/rest/order/getOrders',
						method:'GET',
						isArray:false
					
				},
				remove : {
					url: '/store/app/rest/order//removeCompany/:id',
					method: 'DELETE'
					
				},
				get : {
					url: '/store/app/rest/order/:id',
					method : 'GET',
					isArray: false
				},
				update : {
					url: '/store/app/rest/order/update',
					method : 'POST'
				},
				save : {
					url: '/store/app/rest/order/save',
					method : 'POST'
				},
				getAll: 
					{
						url : '/store/app/rest/company/getAllCompanies',
						method : 'GET',
						isArray: true
					}
		});
	}]);