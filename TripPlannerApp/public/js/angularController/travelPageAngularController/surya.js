/**
 * Created by rkapoor on 26/02/15.
 */

routesModule.directive('postDest', ['$timeout', function($timeout) {
    return function($scope,$rootScope, element, $attrs) {
        //console.log("element:"+element.class);

        if ($scope.$last){
            $timeout(function (){
                if(element.class=="panel-reorderDest")
                {
                    //console.log("in panel-travel");
                    //console.log("scrollHeightTravel:"+$("#transcludeReorderPanel")[0].scrollHeight);
                    $scope.$emit('initialize-pane',"reorderDestPanel");
                }
            },1000);
        }
    };
}]);

routesModule.controller('suryaController', ['$scope', '$rootScope', '$http', '$q', '$location', '$cookies', 'orderedCities', function($scope, $rootScope, $http, $q, $location, $cookies, orderedCities) {

    $scope.reorderPanel=false;
    $scope.reorderList=true;
    $scope.draggableObjects = [];

    $scope.isDestinationsTabActive =  true;
    $scope.isMapTabActive = false;

    $scope.destinationActive = function(){
        $scope.isDestinationsTabActive = true;
        $scope.isMapTabActive = false;
    };

    $scope.mapActive = function(){
        $scope.isDestinationsTabActive = false;
        $scope.isMapTabActive = true;

    };

    var itineraryID;
    var reorderTimes = 0; //For MixPanel

    $scope.IntroOptions = {
        steps: [
            {
                element:  '#reorderPanelIntro',
                intro: "Drag and Drop To Reorder Destinations",
                position: "right"
            },
            {
                element: '#saveButton',
                intro: "Continue when done"
            }
        ],
        scrollToElement: false,
        showStepNumbers: false,
        exitOnOverlayClick: true,
        exitOnEsc:true,
        nextLabel: '<strong>NEXT!</strong>',
        prevLabel: '<span style="color:green">Previous</span>',
        skipLabel: 'Exit',
        doneLabel: 'Thanks'
    };

    var isTravelPanelOpened = false;

    var pathArray = [];
    var orderComparisonFactor = 1.4;
    $rootScope.$on('orderReceived', function onOrderReceived(event, data) {
        $scope.draggableObjects = orderedCities.getOrderedDestinationCities();
        $scope.originCity=orderedCities.getOriginCity();
        $scope.reorderList=false;
        $rootScope.$emit("plotCities");
        $rootScope.$emit('dataLoaded');
    });

    $scope.onDropComplete = function (index, obj, evt) {
        reorderTimes++;
        var otherObj = $scope.draggableObjects[index];
        var otherIndex = $scope.draggableObjects.indexOf(obj);
        $scope.draggableObjects[index] = obj;
        $scope.draggableObjects[otherIndex] = otherObj;

        var requestURL = "/reorderDestinations/"+itineraryID;
        var data  = {
            fromIndex: otherIndex,
            toIndex: index
        };

        console.log(JSON.stringify(data));

        $http({
            method: "PUT",
            url: requestURL,
            data: data
        })
            .then(
            function success(response){
                console.log('RESPONSE:'+JSON.stringify(response.data));
            },
            function error(response){
            }
        );

        var currentOrderWeight = 0;
        var originCityID=$scope.originCity.CityID;
        var weightArray=orderedCities.getWeightArray();
        var cityIDs = orderedCities.getCityIDs();
        var destinationsLength=$scope.draggableObjects.length;
        currentOrderWeight+=weightArray[cityIDs.indexOf(originCityID)][cityIDs.indexOf($scope.draggableObjects[0].CityID)];
        for(var i=1;i<destinationsLength;i++)
        {
            var cityIDStart=$scope.draggableObjects[i-1].CityID;
            var cityIDEnd=$scope.draggableObjects[i].CityID;
            var startIndex=cityIDs.indexOf(cityIDStart);
            var endIndex=cityIDs.indexOf(cityIDEnd);
            currentOrderWeight+=weightArray[startIndex][endIndex];
        }
         currentOrderWeight+=weightArray[cityIDs.indexOf($scope.draggableObjects[destinationsLength-1].CityID)][cityIDs.indexOf(originCityID)];
         if(orderedCities.getMinimumWeight()*orderComparisonFactor<currentOrderWeight)
         {
            $rootScope.$emit('showRecommendation','inEfficientOrder');
         }
         else
         {
            $rootScope.$emit('hideRecommendation','order');
         }

        $rootScope.$emit('plotCities');
    };

    $rootScope.$on('showRoutes',function onShowRoutes(event,data){
        //MixPanel Tracking
        mixPanelTrack('Submit Reorder', {
            "ReorderTimes": reorderTimes
        });
        $scope.reorderPanel = false;
        isTravelPanelOpened = true;
        $rootScope.$emit('showTravelPanel');
    });

    function showMultiCityIntro(){
        var visitedStatus = $cookies.get('visitedStatus');
        var currentDate = new Date();
        var options = {
            expires: new Date(currentDate.getTime() + 30*24*60*60*1000)
        };
        //console.log('visitedStatus:'+visitedStatus);
        if(visitedStatus != undefined && visitedStatus != null){
            //Cookie is present
            visitedStatus = JSON.parse(visitedStatus);
            //visitedStatus = JSON.parse(visitedStatus);//Double parsing as 1 parse returns string
            var multiCityIntro = visitedStatus.multiCityIntro;
            //console.log(typeof visitedStatus);
            //console.log(multiCityIntro);
            if(!(multiCityIntro != undefined && multiCityIntro)){
                //Multicity cookie not present
                $scope.multiCityIntroFunction();
                visitedStatus.multiCityIntro = true;
                $cookies.put('visitedStatus', JSON.stringify(visitedStatus), options);
            }
        }
        else{
            //Cookie is not present
            $scope.multiCityIntroFunction();
            $cookies.put('visitedStatus',JSON.stringify({
                multiCityIntro: true
            }),options);
        }

    }

    $rootScope.$on('guide',function onGuide(){
        if(!isTravelPanelOpened){
            $scope.multiCityIntroFunction();
        }
    });

    angular.element(window).ready(function () {
        //var currentURL = $location.absUrl();
        //var currentURL = $location.url();
        //console.log('url:'+currentURL);
        //pathArray = currentURL.split('?');
        //var destinations = getParameterByName('dsts').split(";");
        //if((pathArray.length>1) && (destinations.length>1)){
        $scope.reorderPanel = true;
        itineraryID = $location.absUrl().split('/')[4].replace(/[^0-9a-z]/g,"");
        $http.get('/getOptimizeOrder/'+itineraryID).success(function(data,status){
                orderedCities.setOrderedDestinationCities(data.trip);
                orderedCities.setOriginCity(data.origin);
                orderedCities.setWeightArray(data.weight);
                orderedCities.setMinimumWeight(data.minWeight);
                orderedCities.setPathArray(pathArray);
                orderedCities.setCityIDs(data.cityIDs);
                $rootScope.$emit('orderReceived');
                //MixPanel Timing
                mixPanelTimeEvent('Submit Reorder');
                showMultiCityIntro();
            }
        );
        //}
        //else if((pathArray.length>1) && (destinations.length==1))
        //{
        //    isTravelPanelOpened = true;
        //    will be taken care by sarathi
        //}
        //else {
        //    console.log('Page NOT FOUND');
        //}
    });

    function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
        return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }
}]);
