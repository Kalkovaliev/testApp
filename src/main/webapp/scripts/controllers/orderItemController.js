/**
 * 
 */
'use strict';

storeApp.controller('orderItemController',['$scope','$log','productService','orderProduct','orderService','$timeout','$routeParams','$window', function ($scope,$log,productService,orderProduct,orderService,$timeout,$routeParams,$window) {
	   
	  
	$scope.update = save;
	$scope.saveOrder = saveOrder;
	$scope.productsToShow = [];
	$scope.product = {};
	$scope.orderProducts = [];
	$scope.clearFields = clearFields;
	$scope.remove = remove;
	$scope.edit = edit;
	$scope.saveOkMsg = null;
	
	    
	   loadProducts();
	   clearFields();
	   $scope.goBack = function() {
	   if($scope.products!=null)
	   {
	  	 for(var i=0;i<$scope.products.length;i++)
	  	 {
	   	 orderProduct.remove($scope.products[i]);
	   		
	   	  }
	   }
		 $window.history.back();
			
	};
	
			
	    
	    function loadProducts(){
	    	
			productService.getAll(function(data)
			{ $scope.productsToShow = data;
	
			// pagination();
			});
	    	
	    }
	
	    function loadOrderProducts() {
	        orderProduct.getAll().then(function (data) {
	        
	          $scope.products = data;
	         
	        });
	      }
	    
	    
	    function save() {
	      var promise = orderProduct.save($scope.product);
	      promise.then(successCallback, errorCallback);
	      function successCallback(data) {
	      loadOrderProducts();
		  clearFields();
	      }
	      function errorCallback(data) {
	    
	      }
	    }
	    
	    function saveOrder(){
	  
	    	   $scope.saveOkMsg = null;
	    	   $scope.saveErrMsg = null;
	    	   var order = {};
	    	   var niza= [];
	    	   
	    	
	    	var	promise = orderService.saveOrder($scope.order);
	   	    promise.then(successCallback, errorCallback);
	  	     function successCallback(data) {
	  	     $scope.saveOkMsg = "Order with id " + data.id + " is created";
	  	      order = data;
	  	      for(var i=0;i<$scope.products.length;i++)
	  	      {
	    		   var pom = {};
	    		   pom.product_id = $scope.products[i].id;
	    		   pom.order_id = order.id;
	    		   pom.quantity = $scope.products[i].quantity;
	    		   pom.totalprice = $scope.products[i].totalPrice;
	   
	    		   var promise2 = orderService.addOrderProducts(pom);
		  	        promise2.then(successCallback1,errorCallback1);
		  	        function successCallback1(data1){
		  	        $scope.orderProducts[i] =  data1;
		  	     		  	        			  	        	
		  	        }
		  	        
		  	      function errorCallback1(data1){
		  	  		$scope.saveErrMsg = "Invalid while adding orderProducts to the database due to: " + data1.message;
		  	        }
		  
	    	   }
	  	
	  	    	  	                          }
	  	      
	  	      function errorCallback(data) {
	  	    	  $scope.saveErrMsg = "invalid while adding order in database: " + data.message;
	  	      }
	  	      
	  	 
	    	
	    }

	    function clearFields() {
	      $scope.product = {};
	    	}

	    function edit(entity) {
	      $scope.product = entity;
	      orderProduct.remove(entity).then(function () {
	        	loadOrderProducts();
	        });
	   	    }

	    function remove(entity) {
	    	orderProduct.remove(entity).then(function () {
	        	loadOrderProducts();
	        });
	    }
	  

}]);
