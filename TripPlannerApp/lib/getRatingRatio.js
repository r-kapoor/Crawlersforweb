/**
 * New node file
 */

function getRatingRatio(conn,cities,callback)
{
	var num_city=cities.length;
	var connection=conn.conn();
	connection.connect();
	var ratio= Array.apply(null, new Array(num_city)).map(Number.prototype.valueOf, 0);
	var subQuery='';
	var queryString='SELECT CityID, CityName, Rating FROM City WHERE CityName IN (' + connection.escape(cities) + ');';
	var totalRating=0;
	//var approxDates=[];
	
	connection.query(queryString, function(err, rows, fields) {
		if (err)
		{
			throw err;
		}
	    else{
	    	for (var i in rows) {
				totalRating+=parseFloat(rows[i].Rating);
				console.log("totalRating:"+totalRating);
	    	}
	    	
	    	for(var j in rows)
	    	{
	    			ratio[cities.indexOf(rows[j].CityName)]=((parseFloat(rows[j].Rating)/totalRating));
	    	}
	    	for(var i in ratio)
	    	{
	    		console.log("ratio:"+i+":"+ratio[i]);
	    	}	
	    	
	  }
		callback(null,ratio);
});
	connection.end();
}

module.exports.getRatingRatio=getRatingRatio;