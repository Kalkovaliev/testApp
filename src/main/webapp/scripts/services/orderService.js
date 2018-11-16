/**
 * 
 */
'use strict';


storeApp.factory('orderService',['$log','$timeout','$q','$http','$resource',function($log,$timeout,$q,$http,$resource) {
	var companiesList = [];
	var companyIdSequence = 0;

	
	var service = {
		
		saveOrder: saveOrderFn,
		remove : removeFn,
		getOrders:getOrdersFn,
		update:updateFn,
		addOrderProducts:addOrderProductsFn,
			
	};
	return service;
	
	

	
	function addOrderProductsFn(orderItem){
		var promise = $http.post('app/rest/orderProducts/insertOrderProducts',orderItem).then(function(response){
			return response.data;
			
		},function errorCallback(response) {
	        // called asynchronously if an error occurs
	        // or server returns response with an error status.
	    	promise = response;
	      }
			
		);
		  return promise;
		
		
	}
	
	
	function getOrdersFn(){
		var promise = $http.get('app/rest/order/getOrders').then(function(response){
			
			return response.data;
			
		},function errorCallback(response){
			promise = response;
			
		});
		
		return promise;
	}
	
	   function validateName(entity) {
		      if (entity === null
		        || entity === undefined
		        || typeof entity !== 'string'
		        || entity.length === 0) {
		        return 'Invalid name for firm';
		      }
		      return null;
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
	
	function saveOrderFn(orderEntity){
		  var entityForSave, invalidMessage;
	      var deferred = $q.defer();
	      var order = orderEntity;
	      
	      if (orderEntity.id === undefined) {
	    	  $timeout(function () {
	          entityForSave = angular.copy(orderEntity);
	          invalidMessage = validateName(entityForSave.description);
	          if (invalidMessage === null) {
	         $http.post('app/rest/order/insertOrder',order)
	              .then(
	              function (response) {
	                  deferred.resolve(response.data);
	              },
	              function(errResponse){
	                  console.log('Error while inserting Order:'+errResponse.Text);
	                  deferred.reject(errResponse); 
	              }
	          );
	         
//	           entityForSave.id = ++groupIdSequence;
//	           groupsList.push(entityForSave);
//	          $log.debug('saving', entityForSave);
//	          deferred.resolve(entityForSave);
	          } else {
	            $log.debug('saving invalid:', invalidMessage);
	            deferred.reject({
	              message: invalidMessage
	            });
	          }
	    	  }, 100);
	        return deferred.promise;
	      } 
	      else {
	       return updateFn(orderEntity);
	      }
	      $log.debug('in save fn');
		
		
	}
	
	function updateFn(companyEntity){
		 var deferred = $q.defer();
	      if (companyEntity.id === undefined) {
	        return saveFn(companyEntity);
	      } else {
	    	  $timeout(function () {
	      	  $http.post('app/rest/company/updateCompany',companyEntity)
	          .then(
	          function(response) {
	              deferred.resolve(response.data);
	           	                  },
	          function(errResponse){
	              console.log('Error while updating Company:'+errResponse.Text);
	              deferred.reject(errResponse); 
	          }
	          						);
	    	  						}, 100);
	      	   return deferred.promise;
	         
	      			}

		}
	
	 function removeFn(order){

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
	    
	
}]);