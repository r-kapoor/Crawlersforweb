// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * FlightDetails generated by hbm2java
 */
public class FlightDetails implements java.io.Serializable {

	private Integer flightNumberId;
	private String airline;
	private String flightNumber;

	public FlightDetails() {
	}

	public FlightDetails(String airline, String flightNumber) {
		this.airline = airline;
		this.flightNumber = flightNumber;
	}

	public Integer getFlightNumberId() {
		return this.flightNumberId;
	}

	public void setFlightNumberId(Integer flightNumberId) {
		this.flightNumberId = flightNumberId;
	}

	public String getAirline() {
		return this.airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFlightNumber() {
		return this.flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

}
