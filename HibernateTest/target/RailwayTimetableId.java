// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * RailwayTimetableId generated by hbm2java
 */
public class RailwayTimetableId implements java.io.Serializable {

	private int trainNo;
	private String stationCode;
	private Date arrivalTime;
	private Date departureTime;
	private int distanceCovered;
	private byte day;
	private byte route;

	public RailwayTimetableId() {
	}

	public RailwayTimetableId(int trainNo, String stationCode,
			int distanceCovered, byte day, byte route) {
		this.trainNo = trainNo;
		this.stationCode = stationCode;
		this.distanceCovered = distanceCovered;
		this.day = day;
		this.route = route;
	}

	public RailwayTimetableId(int trainNo, String stationCode,
			Date arrivalTime, Date departureTime, int distanceCovered,
			byte day, byte route) {
		this.trainNo = trainNo;
		this.stationCode = stationCode;
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTime;
		this.distanceCovered = distanceCovered;
		this.day = day;
		this.route = route;
	}

	public int getTrainNo() {
		return this.trainNo;
	}

	public void setTrainNo(int trainNo) {
		this.trainNo = trainNo;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public int getDistanceCovered() {
		return this.distanceCovered;
	}

	public void setDistanceCovered(int distanceCovered) {
		this.distanceCovered = distanceCovered;
	}

	public byte getDay() {
		return this.day;
	}

	public void setDay(byte day) {
		this.day = day;
	}

	public byte getRoute() {
		return this.route;
	}

	public void setRoute(byte route) {
		this.route = route;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RailwayTimetableId))
			return false;
		RailwayTimetableId castOther = (RailwayTimetableId) other;

		return (this.getTrainNo() == castOther.getTrainNo())
				&& ((this.getStationCode() == castOther.getStationCode()) || (this
						.getStationCode() != null
						&& castOther.getStationCode() != null && this
						.getStationCode().equals(castOther.getStationCode())))
				&& ((this.getArrivalTime() == castOther.getArrivalTime()) || (this
						.getArrivalTime() != null
						&& castOther.getArrivalTime() != null && this
						.getArrivalTime().equals(castOther.getArrivalTime())))
				&& ((this.getDepartureTime() == castOther.getDepartureTime()) || (this
						.getDepartureTime() != null
						&& castOther.getDepartureTime() != null && this
						.getDepartureTime()
						.equals(castOther.getDepartureTime())))
				&& (this.getDistanceCovered() == castOther.getDistanceCovered())
				&& (this.getDay() == castOther.getDay())
				&& (this.getRoute() == castOther.getRoute());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getTrainNo();
		result = 37
				* result
				+ (getStationCode() == null ? 0 : this.getStationCode()
						.hashCode());
		result = 37
				* result
				+ (getArrivalTime() == null ? 0 : this.getArrivalTime()
						.hashCode());
		result = 37
				* result
				+ (getDepartureTime() == null ? 0 : this.getDepartureTime()
						.hashCode());
		result = 37 * result + this.getDistanceCovered();
		result = 37 * result + this.getDay();
		result = 37 * result + this.getRoute();
		return result;
	}

}
