// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * CityConnectivityRailway generated by hbm2java
 */
public class CityConnectivityRailway implements java.io.Serializable {

	private int cityId;
	private double connectivity;

	public CityConnectivityRailway() {
	}

	public CityConnectivityRailway(int cityId, double connectivity) {
		this.cityId = cityId;
		this.connectivity = connectivity;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public double getConnectivity() {
		return this.connectivity;
	}

	public void setConnectivity(double connectivity) {
		this.connectivity = connectivity;
	}

}
