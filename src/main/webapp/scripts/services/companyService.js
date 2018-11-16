'use strict';

storeApp.factory('companyService',['$resource',function($resource) {
	return $resource('/store/app/rest/company',{},{
				paged: {
						url:'/store/app/rest/company/getCompanies',
						method:'GET',
						isArray:false
					
				},
				remove : {
					url: '/store/app/rest/company/removeCompany/:id',
					method: 'DELETE'
					
				},
				get : {
					url: '/store/app/rest/company/:id',
					method : 'GET',
					isArray: false
				},
				update : {
					url: '/store/app/rest/company/update',
					method : 'POST'
				},
				save : {
					url: '/store/app/rest/company/save',
					method : 'POST'
				},
				getAll: 
					{
						url : '/store/app/rest/company/getAllCompanies',
						method : 'GET',
						isArray: true
					},
				getCities : {
						url : '/store/app/rest/city/getAllCities',
						method : 'GET',
						isArray : true
					
				}
		});
	}]);