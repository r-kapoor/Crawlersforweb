// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * BusBoardingsId generated by hbm2java
 */
public class BusBoardingsId implements java.io.Serializable {

	private int busId;
	private String boardingPoint;
	private String boardingLandmark;
	private String boardingAddress;
	private Date boardingTime;

	public BusBoardingsId() {
	}

	public BusBoardingsId(int busId, String boardingPoint,
			String boardingLandmark, String boardingAddress, Date boardingTime) {
		this.busId = busId;
		this.boardingPoint = boardingPoint;
		this.boardingLandmark = boardingLandmark;
		this.boardingAddress = boardingAddress;
		this.boardingTime = boardingTime;
	}

	public int getBusId() {
		return this.busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getBoardingPoint() {
		return this.boardingPoint;
	}

	public void setBoardingPoint(String boardingPoint) {
		this.boardingPoint = boardingPoint;
	}

	public String getBoardingLandmark() {
		return this.boardingLandmark;
	}

	public void setBoardingLandmark(String boardingLandmark) {
		this.boardingLandmark = boardingLandmark;
	}

	public String getBoardingAddress() {
		return this.boardingAddress;
	}

	public void setBoardingAddress(String boardingAddress) {
		this.boardingAddress = boardingAddress;
	}

	public Date getBoardingTime() {
		return this.boardingTime;
	}

	public void setBoardingTime(Date boardingTime) {
		this.boardingTime = boardingTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof BusBoardingsId))
			return false;
		BusBoardingsId castOther = (BusBoardingsId) other;

		return (this.getBusId() == castOther.getBusId())
				&& ((this.getBoardingPoint() == castOther.getBoardingPoint()) || (this
						.getBoardingPoint() != null
						&& castOther.getBoardingPoint() != null && this
						.getBoardingPoint()
						.equals(castOther.getBoardingPoint())))
				&& ((this.getBoardingLandmark() == castOther
						.getBoardingLandmark()) || (this.getBoardingLandmark() != null
						&& castOther.getBoardingLandmark() != null && this
						.getBoardingLandmark().equals(
								castOther.getBoardingLandmark())))
				&& ((this.getBoardingAddress() == castOther
						.getBoardingAddress()) || (this.getBoardingAddress() != null
						&& castOther.getBoardingAddress() != null && this
						.getBoardingAddress().equals(
								castOther.getBoardingAddress())))
				&& ((this.getBoardingTime() == castOther.getBoardingTime()) || (this
						.getBoardingTime() != null
						&& castOther.getBoardingTime() != null && this
						.getBoardingTime().equals(castOther.getBoardingTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getBusId();
		result = 37
				* result
				+ (getBoardingPoint() == null ? 0 : this.getBoardingPoint()
						.hashCode());
		result = 37
				* result
				+ (getBoardingLandmark() == null ? 0 : this
						.getBoardingLandmark().hashCode());
		result = 37
				* result
				+ (getBoardingAddress() == null ? 0 : this.getBoardingAddress()
						.hashCode());
		result = 37
				* result
				+ (getBoardingTime() == null ? 0 : this.getBoardingTime()
						.hashCode());
		return result;
	}

}
