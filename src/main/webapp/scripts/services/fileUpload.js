storeApp.service('fileUpload', [ '$http', '$route','$q','$timeout', function($http, $route, $q,$timeout) {
	this.uploadFileToUrl = function(file, uploadUrl,company_id, $scope) {
		var deferred = $q.defer();
		if (file === undefined) {
			$scope.noFile = true;
			return false;
		}
		
		
		var fd = new FormData();
		fd.append('file', file);
		fd.append('company', company_id);
		$http.post(uploadUrl, fd, {
			transformRequest : angular.identity,
			
			headers : {
				'Content-Type' : undefined
			}
		}).success(function(data, status) {
		
			//$scope.uploadSuccess = true;
		//	$scope.saveReport = data;
			deferred.resolve(data,status);
			
		}).error(function(data,status) {
			if (status === 415) {
				$scope.wrongFileType = true;
			} else if (status === 417) {
				$scope.noFile = true;
			} else if (status === 400) {
				$scope.noFile = true;
			}
		
			deferred.reject(status);
		});
		return deferred.promise;
	}
} ]);
