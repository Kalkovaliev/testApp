/**
 * 
 */
'use strict';

storeApp.controller('orderViewController',['$scope','$log','orderViewService','productService','$routeParams','$timeout', '$window','$modal', function ($scope,$log,orderViewService,productService,$routeParams,$timeout, $window,$modal) {
	
	$scope.order = {};
	$scope.productsToShow = [];
	$scope.products = [];
	$scope.product = {};
	$scope.edit = edit;
	$scope.remove = remove;
	$scope.orderProductID = null;
	$scope.clearFields = clear;
	
	$scope.id = $routeParams.id;
	var id = $routeParams.id;
	orderViewService.getOrder(id).then(function(response){
		 $scope.order = response;
	
	 });
	
	
	
	$scope.update = function update(){
		  var pom = {};
		  pom.orderProduct_id = $scope.orderProductID;
		  pom.order_id = $scope.order.id;
		  pom.product_id = $scope.product.id;
		  pom.quantity = $scope.product.quantity;
		  pom.totalprice = $scope.product.quantity * $scope.product.price;
		  if(pom.orderProduct_id!==null){
	  orderViewService.updateOrderProduct(pom).then(successCallBack,errorCallback);
		 
	       function successCallBack(value, responseHeaders){
	    		$scope.saveOkMsg = 'Order Product with id:'
					+  pom.orderProduct_id + ' is updated';
			$scope.clearMesssages();
			loadOrderProducts();
	        }
	        
	      function errorCallback(httpResponse){
	 		$scope.saveErrMsg = null;
			if (httpResponse.status == 409) {
				$scope.saveErrMsg = "ERROR";
			} else {
				$scope.saveErrMsg = "ERROR";
			}
			$scope.clearMesssages();
	      }
		  }
		  else
			  {
				$scope.saveErrMsg = "You cannot update this product because it is not in the order";
				$scope.clearMesssages();
			  }
		
	  }
	$scope.updateOrder =  function updateOrder(){
		
  		orderViewService.updateOrder($scope.order).then(successCallBack,errorCallback);
 		function successCallBack(data){
     	$scope.saveOkMsg = "Order with id "+ data.id + " is updated";
        loadOrderProducts();  		  	
        $scope.clearMesssages();
        }
        
 		function errorCallback(data){
  		$scope.saveErrMsg = "Invalid while updating orderto the database due to: " + data.message;
        }
    
  }

	$scope.goBack = function() {
		$window.history.back();
	};
	
		
	  
    function loadProducts(){
    	
		productService.getAll(function(data)
		{ $scope.productsToShow = data;

		// pagination();
		});
    	
    }

	  
	  loadProducts();
	
	  function loadOrderProducts(){
		orderViewService.getOrderProducts(id).then(function(data)
		{
			$scope.products = data;	
			
		});
		
	}
	  
	  loadOrderProducts();
	
	  function edit(object){
		  $scope.product = object.product;
		  $scope.product.quantity = object.quantity; 
		  $scope.orderProductID = object.id;
	  }
	  function clear(){
		  $scope.product = {};
		}

	  
	  function remove(object){
		  		
		  		orderViewService.removeOrderProduct(object).then(successCallBack,errorCallback);
		 		function successCallBack(data){
		     	$scope.saveOkMsg = "OrderProduct with id "+ object.id + " is successfully removed";
		        loadOrderProducts();
		        $scope.clearMesssages();
		        }
		        
		 		function errorCallback(data){
		  		$scope.saveErrMsg = "Invalid while removing orderProduct to the database due to: " + data.message;
		        }
		 		
		  		
	  }
	
	  
		$scope.openConfirmBackModal = function(size) {
			$scope.initModalBack(size);
				};
	
		
		$scope.initModalBack = function(size) {

			var modalInstance = $modal.open({
				animation : $scope.animationsEnabled,
				templateUrl : 'ConfirmBackModalContent.html',
				controller : 'ConfirmBackCtrl',
				size : size,
				resolve : {
					productsToShow : function(){
						return $scope.productsToShow;
					},
				order_id : function(){ 
					return $scope.id;
				},
				}
								
			});

			modalInstance.result.then(function(selectedItem) {
				$scope.goBack();
//				console.log($rootScope.riskCategory);
			}, function() {
//				console.log("dialog close dismiss close ");
			});
		};
		$scope.clearMesssages = function() {
			$timeout(function() {
				$scope.saveErrMsg = null;
				$scope.saveOkMsg = null;

			}, 10000);
		};
		
}]);



storeApp.controller('ConfirmBackCtrl',function($scope,$modalInstance, productsToShow,order_id,orderService,$window) {	
	
	$scope.product = {};
	$scope.save = save;
	$scope.productsToShow = productsToShow;
	$scope.orderProducts = {};
	$scope.order_id = order_id;
	
	$scope.clearFields = function(){
		$scope.product = {};
	}
	
	// sendCommand;
	/*$scope.ok = function() {
		// sendCommand;
		$modalInstance.close();
	};*/
	$scope.cancel = function() {
		$window.location.reload();
		$modalInstance.dismiss('cancel');
		   
	};
	function save(){
	 var pom = {};
	   pom.product_id = $scope.product.id;
	   pom.order_id = order_id;
	   pom.quantity = $scope.product.quantity;
	   pom.totalprice = $scope.product.price * $scope.product.quantity;

	   var promise = orderService.addOrderProducts(pom);
        promise.then(successCallback,errorCallback);
        function successCallback(data){
        $scope.orderProducts =  data;
    	$scope.saveOkMsg = " Product with id  " + pom.product_id + " is added successfully";
    	$scope.product = {};
    
        }
        
      function errorCallback(data){
  		$scope.saveErrMsg = "Invalid while adding orderProducts to the database due to: " + data.message;
        }
		}
	
});