// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * PlaceImage generated by hbm2java
 */
public class PlaceImage implements java.io.Serializable {

	private Integer imgId;
	private int placeId;
	private String imgUrl;

	public PlaceImage() {
	}

	public PlaceImage(int placeId, String imgUrl) {
		this.placeId = placeId;
		this.imgUrl = imgUrl;
	}

	public Integer getImgId() {
		return this.imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	public int getPlaceId() {
		return this.placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
	}

	public String getImgUrl() {
		return this.imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
