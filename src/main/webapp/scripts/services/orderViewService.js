/**
 * 
 */
'use strict'

storeApp.factory('orderViewService',['$log','$timeout','$q','$http','$resource',function($log,$timeout,$q,$http,$resource) {
	
	var service = {
	getOrder:getOrderFn,
	getOrderProducts:getOrderProductsFn,
	updateOrderProduct:updateOrderProductFn,
	removeOrderProduct:removeOrderProductFn,
	updateOrder:updateOrderFn,
	removeOrder:removeOrderFn,
	};
	
	return service;
	
	function getOrderFn(id){
		var promise = $http.get('app/rest/order/getOrder/'+id).then(function(response){
			return response.data;
			
		},function errorCallback(response) {
	        // called asynchronously if an error occurs
	        // or server returns response with an error status.
	    	promise = response;
	      }
			
		);
		  return promise;
		}
	
	function getOrderProductsFn(id){
		var promise = $http.get('app/rest/orderProducts/getOrderProducts/'+id).then(function(response){
			return response.data;
			
		},function errorCallback(response) {
	        // called asynchronously if an error occurs
	        // or server returns response with an error status.
	    	promise = response;
	      }
			
		);
		  return promise;
		
	}
	function updateOrderProductFn(orderItem)
	{
		var promise = $http.post('app/rest/orderProducts/updateOrderProducts',orderItem).then(function(response)
		{
		return response.data;
		},
		function errorCallback(response) 
		{
	        promise = response;
	    }
		);
		  return promise;
	}
	function removeOrderProductFn(object)
	{
		   var deferred = $q.defer();
		   var promise = $http.post('app/rest/orderProducts/removeOrderProducts/'+object.id).then(function(response){
	        deferred.resolve(response.data);
	            },	
	        function(errResponse){
	        console.log('Error while removing OrderProduct:'+errResponse.Text);
	        deferred.reject(errResponse); 
	            });
	                                 
	          $log.debug('remove', object);
	         //deferred.resolve();
	         return deferred.promise;

	   }
	function updateOrderFn(object)
	{
		var promise = $http.post('app/rest/order/updateOrder',object).then(function(response)
		{
		return response.data;
		},
		function errorCallback(response) 
		{
	        promise = response;
	    }
		);
		  return promise;
	}
	function removeOrderFn(object){
		 var deferred = $q.defer();
		   $timeout(function () {
		   var promise = $http.post('app/rest/order/removeOrder/'+object.id).then(function(response){
	        deferred.resolve(response.data);
	            },	
	        function(errResponse){
	        console.log('Error while removing OrderProduct:'+errResponse.Text);
	        deferred.reject(errResponse); 
	            });
	                                 
	          $log.debug('remove', object);
	         //deferred.resolve();
	}, 100);
	         return deferred.promise;
		
	}
		  
	
	
	
}]);
