'use strict';

var conn = require('../lib/database');
var getDestinationsAndStops = require('../lib/getDestinationsAndStops');
var isHotelRequired=require('../lib/isHotelRequired');
var getHotelData=require('../lib/getHotelData');
var getOrderedPlaces= require('../lib/getOrderedPlaces');
var async  = require('async');
var selectPlaces = require('../lib/selectPlaces');
var clone= require('../lib/UtilityFunctions/cloneJSON');
var getDayWisePlaces = require('../lib/getDayWisePlaces');
var getOptimizedItinerary = require('../lib/getOptimizedItinerary');

var tripNotPossibleResponse = function (res) {
    var model = {
        tripNotPossible: 1
    };
    res.json(model);
};


module.exports = function(app) {

	app.get('/planItinerary', function(req, res) {
        console.log('plan itinerary called');
		var connection=conn.conn();
		connection.connect();
		var travelData=JSON.parse(req.session.travelData);
		//console.log("-----------Got data from session-------------\n"+JSON.stringify(JSON.parse(travelData).withTaxiRome2rioData));
		var numOfPeople = travelData.numPeople;
		var totalBudget=travelData.userTotalbudget;
        var travelBudget = travelData.travelBudget;
        var minorTravelBudget = travelData.minorTravelBudget;
		var tastes  = travelData.tastes;

        var travelDataSet = false;
        if(travelData.withTaxiRome2rioData == undefined || travelData.withTaxiRome2rioData == null){
            travelData = travelData.withoutTaxiRome2rioData;
            travelDataSet = true;
        }
        else if(travelData.withoutTaxiRome2rioData == undefined || travelData.withoutTaxiRome2rioData == null){
            travelData = travelData.withTaxiRome2rioData;
            travelDataSet = true;
        }

        if((!travelDataSet) || travelData.isMajorDefault != 1){
            //There is some problem with the data
            console.log('There is some problem with the data. Aborting.');
            tripNotPossibleResponse(res);
        }

        else {
            console.log('plan itinerary called1');
            var destinationsAndStops=getDestinationsAndStops.getDestinationsAndStops(travelData);
            console.log('plan itinerary called2');
            var destinationsForPlaces=clone.clone(destinationsAndStops);
            isHotelRequired.isHotelRequired(destinationsAndStops);
            //console.log(JSON.stringify(destinationsAndStops));
            /*for(var i=0;i<destinationsAndStops.destinations.length;i++)
             {
             console.log("destinations:"+JSON.stringify(destinationsAndStops.destinations[i]));
             console.log("stops:"+JSON.stringify(destinationsAndStops.destinationsWiseStops[i]));
             }*/
            //console.log("LastStop:"+JSON.stringify(destinationsAndStops.destinationsWiseStops[destinationsAndStops.destinationsWiseStops.length-1]));


            async.parallel(
                [
                    function hotelDataIfHotelRequired(hotelDataCallback){
                        if(destinationsAndStops.numOfCitiesWhereHotelRequired>0)
                        {
                            console.log("TotalUserBudget:"+totalBudget);
                            var hotelBudget=(totalBudget-travelData.TravelBudget)/2;
                            getHotelData.getHotelData(destinationsAndStops,hotelBudget, numOfPeople,connection,hotelDataCallback);
                        }
                        else {
                            hotelDataCallback(null, destinationsAndStops);
                        }
                    },
                    function placesData(placesDataCallback){
                        getOrderedPlaces.getOrderedPlaces(destinationsForPlaces,tastes,connection,placesDataCallback);

                    }
                ],
                //callback
                function(err, results) {
                    connection.end();
                    var destinationAndStops = selectPlaces.selectPlaces(results[0], results[1]);
                    getDayWisePlaces.getDayWisePlaces(destinationAndStops);
                    var error = getOptimizedItinerary.getOptimizedItinerary(destinationAndStops);
                    if(error != undefined){
                        throw error;
                    }
                    destinationAndStops.userTotalbudget = totalBudget;
                    destinationAndStops.numPeople = numOfPeople;
                    destinationAndStops.tastes = tastes;
                    destinationAndStops.travelBudget = travelBudget;
                    destinationAndStops.minorTravelBudget = minorTravelBudget;

                    res.json(destinationAndStops);
                    var fs = require('fs');
                    fs.writeFile("AfterItineraryPlanning.txt", JSON.stringify(destinationAndStops), function(err) {
                        if (err) {
                            console.log(err);
                        } else {
                            console.log("The file was saved!");
                        }
                    });
                });
        }

	});//app.get
};
