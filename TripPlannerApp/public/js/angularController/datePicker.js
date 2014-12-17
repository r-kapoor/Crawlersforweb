inputModule.controller('DatepickerCtrl', function ($scope, $rootScope, formData) {
  $scope.today = function() {
    $scope.dt1 = new Date();
    $scope.dt2 = null;
  };
  $scope.today();

    $scope.heading = 'h5';

  $scope.clear = function () {
    $scope.dt1 = null;
    $scope.dt2 = 'Select';
  };

  $scope.startDateSelected = function() {
	  if($scope.dt2 !== null) {
		  $scope.dt2 = $scope.dt1;
	  }
  };
  $scope.startDateSelected();

  $scope.endDateSelected = function() {
        $scope.heading = 'h4';
        formData.setStartDate($scope.dt1);
        formData.setEndDate($scope.dt2);
        $rootScope.$emit('selectionDone');
  };

  // Disable weekend selection
  $scope.disabled = function(date, mode) {
    return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
  };

  //$scope.disabled = function(date, mode) {
	//    return ( mode === 'day' && ( date.getTime() < $scope.dt );
  //};

  $scope.toggleMin = function() {
    $scope.minDate = $scope.minDate ? null : new Date();
  };
  $scope.toggleMin();

  $scope.open = function($event,opened) {
    $event.preventDefault();
    $event.stopPropagation();
    $scope.minDate1 = $scope.dt1;
    if(opened === 'opened1')
    {
      $scope.opened2 = false;
    }
    else if(opened === 'opened2')
    {
      $scope.opened1 = false;
    };
    if ($scope[opened]) {
      $scope[opened] = false;
    }
    else {
      $scope[opened] = true;
    };
  };

  $scope.dateOptions = {
    formatYear: 'yy',
    startingDay: 1
  };

  $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
  $scope.format = $scope.formats[1];
});