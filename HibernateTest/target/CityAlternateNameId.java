// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * CityAlternateNameId generated by hbm2java
 */
public class CityAlternateNameId implements java.io.Serializable {

	private int cityId;
	private String alternateName;

	public CityAlternateNameId() {
	}

	public CityAlternateNameId(int cityId, String alternateName) {
		this.cityId = cityId;
		this.alternateName = alternateName;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getAlternateName() {
		return this.alternateName;
	}

	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CityAlternateNameId))
			return false;
		CityAlternateNameId castOther = (CityAlternateNameId) other;

		return (this.getCityId() == castOther.getCityId())
				&& ((this.getAlternateName() == castOther.getAlternateName()) || (this
						.getAlternateName() != null
						&& castOther.getAlternateName() != null && this
						.getAlternateName()
						.equals(castOther.getAlternateName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCityId();
		result = 37
				* result
				+ (getAlternateName() == null ? 0 : this.getAlternateName()
						.hashCode());
		return result;
	}

}