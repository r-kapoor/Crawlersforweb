// default package
// Generated Oct 14, 2014 11:08:41 PM by Hibernate Tools 3.4.0.CR1

/**
 * Restaurants generated by hbm2java
 */
public class Restaurants implements java.io.Serializable {

	private Integer restaurantId;
	private String name;
	private String address;
	private int cityId;
	private String phoneNo;
	private Integer costForTwo;
	private String description;
	private String cuisines;
	private float rating;
	private String locality;
	private int numOfVotes;
	private Boolean nonVeg;
	private Boolean homeDelivery;
	private Boolean dineIn;
	private Boolean ac;
	private Boolean bar;
	private int zomato;

	public Restaurants() {
	}

	public Restaurants(String name, int cityId, float rating, int numOfVotes,
			int zomato) {
		this.name = name;
		this.cityId = cityId;
		this.rating = rating;
		this.numOfVotes = numOfVotes;
		this.zomato = zomato;
	}

	public Restaurants(String name, String address, int cityId, String phoneNo,
			Integer costForTwo, String description, String cuisines,
			float rating, String locality, int numOfVotes, Boolean nonVeg,
			Boolean homeDelivery, Boolean dineIn, Boolean ac, Boolean bar,
			int zomato) {
		this.name = name;
		this.address = address;
		this.cityId = cityId;
		this.phoneNo = phoneNo;
		this.costForTwo = costForTwo;
		this.description = description;
		this.cuisines = cuisines;
		this.rating = rating;
		this.locality = locality;
		this.numOfVotes = numOfVotes;
		this.nonVeg = nonVeg;
		this.homeDelivery = homeDelivery;
		this.dineIn = dineIn;
		this.ac = ac;
		this.bar = bar;
		this.zomato = zomato;
	}

	public Integer getRestaurantId() {
		return this.restaurantId;
	}

	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCityId() {
		return this.cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getCostForTwo() {
		return this.costForTwo;
	}

	public void setCostForTwo(Integer costForTwo) {
		this.costForTwo = costForTwo;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCuisines() {
		return this.cuisines;
	}

	public void setCuisines(String cuisines) {
		this.cuisines = cuisines;
	}

	public float getRating() {
		return this.rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public int getNumOfVotes() {
		return this.numOfVotes;
	}

	public void setNumOfVotes(int numOfVotes) {
		this.numOfVotes = numOfVotes;
	}

	public Boolean getNonVeg() {
		return this.nonVeg;
	}

	public void setNonVeg(Boolean nonVeg) {
		this.nonVeg = nonVeg;
	}

	public Boolean getHomeDelivery() {
		return this.homeDelivery;
	}

	public void setHomeDelivery(Boolean homeDelivery) {
		this.homeDelivery = homeDelivery;
	}

	public Boolean getDineIn() {
		return this.dineIn;
	}

	public void setDineIn(Boolean dineIn) {
		this.dineIn = dineIn;
	}

	public Boolean getAc() {
		return this.ac;
	}

	public void setAc(Boolean ac) {
		this.ac = ac;
	}

	public Boolean getBar() {
		return this.bar;
	}

	public void setBar(Boolean bar) {
		this.bar = bar;
	}

	public int getZomato() {
		return this.zomato;
	}

	public void setZomato(int zomato) {
		this.zomato = zomato;
	}

}
