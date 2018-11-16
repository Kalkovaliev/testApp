/**
 * 
 */

'use strict';
storeApp
		.controller(
				'productController',
				[
						'$scope',
						'$rootScope',
						'$log',
						'productService',
						'companyService',
						'fileUpload',
						'$timeout',
						'$modal',
				
						'AuthenticationSharedService',
						function($scope, $rootScope, $log, productService,
								companyService,fileUpload, $timeout,$modal,
								AuthenticationSharedService) {

							$scope.clear = clear;
							$scope.products = [];
							$scope.product = {};
							$scope.companies = [];
							$scope.searchObject = {};
							$scope.success = null;
							$scope.error = null;

							$scope.isAuthorized = $rootScope.isAuthorized;

							$scope.productObj = {};

							$scope.rowCollection = [];
							$scope.displayedCollection = [];
							$scope.totalItems = 1;
							$scope.currentPage = 1;
							$scope.itemsPerPage = 10;
							$scope.maxSize = 10;
							$scope.productName = "";
							$scope.ProductCompany = {};

							// csv types
							$scope.getHeader = function() {
								return [ "Number", "Name", "Company_ID",
										"Description", "Available" ]
							};
							$scope.tableData = [];

							$scope.loadCompanies = function loadCompanies() {

								companyService.getAll(function(data) {
									$scope.companies = data;

								});

							}

							$scope.checkRoles = function(role) {
								return AuthenticationSharedService
										.isAuthorized(role);
							}

							$scope.loadPage = function() {
								productService
										.paged(
												{
													productName : ($scope.productName ? $scope.productName
															: ""),
													companyId : ($scope.ProductCompany.id ? $scope.ProductCompany.id
															: 0),
													count : $scope.itemsPerPage,
													page : $scope.currentPage

												},
												function(response) {
													$scope.products = response.content;
													$scope.totalItems = response.totalElements;

													if ($scope.products.length > 0) {
														fillCSV($scope.products);
													}

													if ($scope.totalItems == 0) {
														$scope.errorNoData = 'OK';
													} else {

														$scope.errorNoData = null;
													}

												}, function(httresponse) {

												});

							}
							function fillCSV(products) {
								var no = 0;
								for (var i = 0; i < products.length; i++) {
									$scope.tableData
											.push({
												'Number' : ++no,
												'Name' : products[i].name,
												'Company_ID' : products[i].company.id,
												'Description' : products[i].description,
												'Available' : products[i].available
											});
								}

							}
							$scope.search = function() {
								$scope.loadPage();
							}
							$scope.loadPage();

							$scope.loadCompanies();

							$scope.pageChanged = function() {
								$scope.loadPage();

							}

							$scope.clearMesssages = function() {
								$timeout(function() {
									$scope.success = null;
									$scope.error = null;

								}, 10000);
							};

							function clear() {

								$scope.productName = null;
								$scope.ProductCompany = {};
								$scope.loadPage();

							}

							$scope.deleteProduct = function(productId) {
								productService.remove({
									id : productId
								}, function(responseHeaders) {

									$scope.success = 'Product with id:'
											+ productId + ' is removed';
									$scope.clearMesssages();
									$scope.loadPage();
								}, function(httpResponse) {

									$scope.error = null;
									if (httpResponse.status == 409) {
										$scope.error = "ERROR";
									} else {
										$scope.error = "ERROR";
									}
									$scope.clearMesssages();
								});
							};
							
							$scope.ReportModalContent = function(size) {
								$scope.initModalBack(size);
							};

							$scope.initModalBack = function(size) {

								var modalInstance = $modal.open({
									animation : $scope.animationsEnabled,
									templateUrl : 'ReportModalContent.html',
									controller : 'ReportModalCtrl',
									size : size,
									resolve : {
										companies : function() {
											return $scope.companies;
										},
									}

								});

								modalInstance.result.then(function(selectedItem) {
									$scope.goBack();
									// console.log($rootScope.riskCategory);
								}, function() {
									// console.log("dialog close dismiss close ");
								});
							};
							$scope.clearMesssages = function() {
								$timeout(function() {
									$scope.saveErrMsg = null;
									$scope.saveOkMsg = null;

								}, 10000);
							};

							

						}
						]);



storeApp.controller('ReportModalCtrl',function($scope,$modalInstance, companies,fileUpload,$window) {	
	
	$scope.company = {};
//	$scope.save = save;
	$scope.companiesToShow = companies;
	
		
	$scope.clearFields = function(){
		$scope.company = {};
	}
	  $scope.uploadFile = function(){
          var file = $scope.myFile;
          var uploadUrl = "/store/app/rest/product/savedata";
          fileUpload.uploadFileToUrl(file, uploadUrl,$scope.company.id).then(successCallBack,errorCallback);
       };
       function successCallBack(data,responseHeaders){
    	   if(data!==null){
    		   var errors = "";
    		   for(var i=0;i<data.length;i++)
    			   {
    			   	errors += data[i].errorName+"\n";
    			 
    			   }
    		 $scope.saveErrMsg = errors;
    	   }
    	   
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
       
	$scope.cancel = function() {
		$window.location.reload();
		$modalInstance.dismiss('cancel');
		   
	};
/*	function save(){
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
		}*/
	
});
