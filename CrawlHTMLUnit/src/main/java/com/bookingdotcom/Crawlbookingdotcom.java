package com.bookingdotcom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.hibernate.SessionFactory;

import GlobalClasses.HtmlUnitWebClient;

import com.dataTransferObject.BookingdotComDto;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * @author rajat
 * Sample MainLink : "http://www.booking.com/hotel/in/the-leela-palace-kempinski-new-delhi.en-gb.html?checkin=2014-05-05;checkout=2014-05-07;"
 */

public class Crawlbookingdotcom extends HtmlUnitWebClient {

	private static int num = -1;
	private static int flag = 0;//to know loop enters first time in getMainLinks() method
	private static String baseUrl = "http://www.booking.com";
	private static ArrayList<BKDCURL> mainLinks = new ArrayList<BKDCURL>();
	//private static String mainUrls = "";
	//private static String mainUrlsFile = "ConfigFiles/bookingdotcom/priceCheckingUrls.txt";
	private static String hotelUrlsFile = "ConfigFiles/bookingdotcom/allHotelsUrls.txt";
	private static String exceptionUrlsFile = "target/bookingdotcom/cityUrlException.txt";
	
	public static void getMainLinks(BKDCURL bkdcUrl,SessionFactory sessionFactory) throws Exception
	{	
		String exceptionUrls="";
		try{
		HtmlPage page=WebClient(bkdcUrl.link);

		DomElement hotelListInnerArea = page.getFirstByXPath("//div[@id='hotellist_inner']");
		DomElement numArea = page.getFirstByXPath("//ul[@class='x-list']");

		if((getFlag()==0)&&(numArea!=null)&&(numArea.hasChildNodes()))
		{
			DomElement numE=numArea.getLastElementChild();
			if((numE!=null)&&(numE.getTagName().contains("li")))
			{
				String number = numE.asText().trim();
				setNum(Integer.parseInt(number));
				System.out.println("num "+getNum());
			}
		}
		
		if((hotelListInnerArea!=null)&&(hotelListInnerArea.hasChildNodes()))
		{System.out.println("1");
			Iterator<DomElement> hotelListInnerIterator=hotelListInnerArea.getChildElements().iterator();
			while(hotelListInnerIterator.hasNext())
			{
				BookingdotComDto bookingdotComDto = new BookingdotComDto();
				DomElement hotelListInnerElement=hotelListInnerIterator.next();
				if((hotelListInnerElement!=null)&&(hotelListInnerElement.hasChildNodes()))
				{System.out.println("2");
					Iterator<DomElement> hotelListInnerElementIterator=hotelListInnerElement.getChildElements().iterator();
					while(hotelListInnerElementIterator.hasNext())
					{
						DomElement hotelContentElement=hotelListInnerElementIterator.next();
						
						if((hotelContentElement!=null)&&(hotelContentElement.getAttribute("class").contains("sr_item_photo"))&&(hotelContentElement.hasChildNodes()))
						{System.out.println("3");
							Iterator<DomElement> hotelContentItr=hotelContentElement.getChildElements().iterator();
							while(hotelContentItr.hasNext())
							{
								DomElement hotelContentE=hotelContentItr.next();
								if((hotelContentE!=null)&&(hotelContentE.getTagName().contains("a"))&&(hotelContentE.getAttribute("class").contains("sr_item_photo_link")))
								{
									DomElement imgE=hotelContentE.getFirstElementChild();
									if((imgE!=null)&&(imgE.getAttribute("class").contains("hotel_image")))
									{System.out.println("5");
										String hotelImageLink=imgE.getAttribute("src");
										bookingdotComDto.setPhotoLink(hotelImageLink);
										//System.out.println("hotelImage:"+hotelImageLink);
									}
								}
							}	
						}
						if((hotelContentElement!=null)&&(hotelContentElement.getAttribute("class").contains("sr_item_content"))&&(hotelContentElement.hasChildNodes()))
						{System.out.println("6");
								Iterator<DomElement> itemDetailsIterator=hotelContentElement.getChildElements().iterator();
								while(itemDetailsIterator.hasNext())
								{System.out.println("7");
									DomElement detailsE=itemDetailsIterator.next();
									if(detailsE!=null)
									{
										if(detailsE.getAttribute("class").contains("reviewFloater")&&(detailsE.hasChildNodes()))
										{System.out.println("8");
											Iterator<DomElement> detailsItr=detailsE.getChildElements().iterator();
											while(detailsItr.hasNext())
											{
												DomElement detailsitrElement=detailsItr.next();
												if(detailsitrElement!=null)
												{System.out
														.println("9");
													if((detailsitrElement.getTagName().contains("a"))&&(detailsitrElement.getAttribute("class").contains("big_review_score_detailed"))&&(detailsitrElement.hasChildNodes()))
													{System.out
															.println("10");
														Iterator<DomElement> ratingItr=detailsitrElement.getChildElements().iterator();
														while(ratingItr.hasNext())
														{
															DomElement ratingE=ratingItr.next();
															if(ratingE.getAttribute("class").contains("rating"))
															{
																String rating=ratingE.asText().trim();
																bookingdotComDto.setRating(Double.parseDouble(rating));
																System.out
																.println("11");
															}
														}													
														/*if((ratingE!=null)&&(ratingE.hasChildNodes()))
														{System.out
																.println("11");
															String rating=ratingE.getFirstElementChild().asText().trim();
															System.out
																	.println("x");
															bookingdotComDto.setRating(rating);
														}*/
									
														
													}
													if((detailsitrElement.getTagName().contains("span"))&&(detailsitrElement.getAttribute("class").contains("score_from_number_of_reviews")))
													{System.out
															.println("12");
														String num_review_string=detailsitrElement.asText().trim();
														//System.out
															//	.println(num_review_string);
														bookingdotComDto.setNumofreviews(Integer.parseInt(num_review_string.replaceAll("[^0-9]","")));					
													}
												}
											}
										}	
										if((detailsE.getTagName().contains("h3"))&&(detailsE.hasChildNodes()))
										{System.out.println("13");
											DomElement detailsEChd=detailsE.getFirstElementChild();
											if(detailsEChd.getTagName().contains("a"))
											{System.out.println("14");
												String href=detailsEChd.getAttribute("href");
												String hotelName=detailsEChd.asText().trim();
												bookingdotComDto.setHotelUrl(baseUrl+href);
												bookingdotComDto.setCity(bkdcUrl.city);
												bookingdotComDto.setName(hotelName);
												//System.out.println("hotelUrl:"+baseUrl+href);
											}
										}
										
										if((detailsE.getAttribute("class").contains("address"))&&(detailsE.hasChildNodes()))
										{System.out.println("15");
											DomElement localityE= detailsE.getFirstElementChild();
											if(localityE.getAttribute("class").contains("jq_tooltip district_link"))
											{System.out.println("16");
												String locality = localityE.asText().trim();
												bookingdotComDto.setLocality(locality);
												//System.out.println("locality "+locality);
												if(localityE.hasAttribute("data-coords"))
												{
													System.out
															.println("latLong:"+localityE.getAttribute("data-coords"));
													String latLong[]=localityE.getAttribute("data-coords").split(",");
													System.out
															.println("lat:"+latLong[0]);
													System.out
															.println("long:"+latLong[1]);
													bookingdotComDto.setLatitude(Double.parseDouble(latLong[0]));
													bookingdotComDto.setLongitude(Double.parseDouble(latLong[1]));
												}					
											}
										}
										if((detailsE.getAttribute("class").contains("room_details"))&&(detailsE.hasChildNodes()))
										{
											Iterator<DomElement> parentRoomDetailsItr=detailsE.getChildElements().iterator();
											while(parentRoomDetailsItr.hasNext())
											{
												DomElement childRoomeDetailElement=parentRoomDetailsItr.next();
												if((childRoomeDetailElement!=null)&&(childRoomeDetailElement.getAttribute("class").contains("group_more_less_wrapper")&&(childRoomeDetailElement.hasChildNodes())))
												{
													DomElement table=childRoomeDetailElement.getFirstElementChild();
													if((table!=null)&&(table.getAttribute("class").contains("featuredRooms"))&&(table.hasChildNodes()))
													{
														Iterator<DomElement> tableItr=table.getChildElements().iterator();
														while(tableItr.hasNext())
														{
															DomElement tableItrElement=tableItr.next();
															if((tableItrElement!=null)&&(tableItrElement.getTagName().contains("tbody"))&&(tableItrElement.hasChildNodes()))
															{
																DomElement trElement=tableItrElement.getFirstElementChild();
																if((trElement!=null)&&(trElement.getAttribute("class").contains("roomrow entire_row_clickable"))&&(trElement.hasChildNodes()))
																{
																	
																	Iterator<DomElement> roomDetailsItr=trElement.getChildElements().iterator();
																	while(roomDetailsItr.hasNext())
																	{
																		DomElement roomDetailsItrElement=roomDetailsItr.next();
																		if(roomDetailsItrElement!=null)
																		{
																			if(roomDetailsItrElement.getAttribute("class").contains("maxPersonsLeft")&&(roomDetailsItrElement.hasChildNodes()))
																			{
																				DomElement maxPersonE=roomDetailsItrElement.getFirstElementChild();
																				if((maxPersonE!=null)&&(maxPersonE.getAttribute("class").contains("sr_max_occupancy"))&&(maxPersonE.hasChildNodes()))
																				{
																					DomElement maxPersonEChild=maxPersonE.getFirstElementChild();
																					if((maxPersonEChild!=null)&&(maxPersonEChild.hasAttribute("data-title")))
																					{
																						String maxPersonDetails=maxPersonEChild.getAttribute("data-title");
																						bookingdotComDto.setMaxPersons(Integer.parseInt(maxPersonDetails.replaceAll("[^0-9]","")));
																					}
																				}
																				
																			}
																			if(roomDetailsItrElement.getAttribute("class").contains("roomName")&&(roomDetailsItrElement.hasChildNodes()))
																			{System.out
																					.println("17");
																				DomElement roomInnerElement=roomDetailsItrElement.getFirstElementChild();
																				if((roomInnerElement!=null)&&(roomInnerElement.hasChildNodes()))
																				{System.out
																						.println("18");
																					DomElement roomInnerNameE=roomInnerElement.getFirstElementChild();
																					if((roomInnerNameE!=null)&&(roomInnerNameE.getAttribute("class").contains("room_link")))
																					{System.out
																							.println("19");
																						String roomType=roomInnerNameE.asText().trim();
																						bookingdotComDto.setRoomType(roomType);
																					}
																				}
																			}
																			if(roomDetailsItrElement.getAttribute("class").contains("roomPrice sr_discount")&&(roomDetailsItrElement.hasChildNodes()))
																			{System.out
																					.println("20");
																				
																				DomElement priceElement=roomDetailsItrElement.getFirstElementChild();
																				if(priceElement!=null)
																				{System.out
																						.println("21");
																					if((priceElement.getAttribute("class").contains("smart_price_style"))&&(priceElement.hasChildNodes()))
																					{System.out
																							.println("22");
																						Iterator<DomElement> priceEItr=priceElement.getChildElements().iterator();
																						while(priceEItr.hasNext())
																						{
																							DomElement priceItrElement=priceEItr.next();
																							if((priceItrElement!=null)&&(priceItrElement.getAttribute("class").contains("strike-it-red_anim")))
																							{System.out
																									.println("23");
																							System.out
																									.println("test:"+priceItrElement);
																								String roomPrice=priceItrElement.asText().trim();
																								//bookingdotComDto.setPrice(Integer.parseInt(roomPrice.replaceAll("[^0-9]","")));
																								Scanner st = new Scanner(roomPrice);
																						        while (!st.hasNextDouble())
																						        {
																						            st.next();
																						        }
																						        double value = st.nextDouble();
																						        //System.out.println(value);
																						        int rmPrice=(int)value;
																								System.out.println("roomPrice:"+rmPrice);
																						        bookingdotComDto.setPrice(rmPrice);
																
																							}
																							else if((priceItrElement!=null)&&(priceItrElement.getAttribute("class").contains("price availprice")))
																							{
																								System.out
																										.println("24");
																								String roomPrice=priceItrElement.asText().trim();
																								//bookingdotComDto.setPrice(Integer.parseInt(roomPrice.replaceAll("[^0-9]","")));
																								Scanner st = new Scanner(roomPrice);
																						        while (!st.hasNextDouble())
																						        {
																						            st.next();
																						        }
																						        double value = st.nextDouble();
																						        //System.out.println(value);
																						        int rmPrice=(int)value;
																								System.out.println("roomPrice:"+rmPrice);
																						        bookingdotComDto.setPrice(rmPrice);
																							}
																						}
																					}
																				}			
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				if(bookingdotComDto.getName()!=null){
					//transferdata
					/*System.out.println("name:"+bookingdotComDto.getName());
					System.out.println("country:"+bookingdotComDto.getCountry());
					System.out.println("city:"+bookingdotComDto.getCity());
					System.out.println("locality:"+bookingdotComDto.getLocality());
					System.out.println("lat:"+bookingdotComDto.getLatitude());
					System.out.println("long:"+bookingdotComDto.getLongitude());
					System.out.println("rating:"+bookingdotComDto.getRating());
					System.out.println("reviews:"+bookingdotComDto.getNumofreviews());
					System.out.println("photoLink:"+bookingdotComDto.getPhotoLink());
					System.out.println("hotelUrl:"+bookingdotComDto.getHotelUrl());
					System.out.println("roomType:"+bookingdotComDto.getRoomType());
					System.out.println("price:"+bookingdotComDto.getPrice());
					System.out.println("maxPersons:"+bookingdotComDto.getMaxPersons());*/
					//source=1 signifies data is from booking.com
					bookingdotComDto.setSource(1);
					//Transfer data into database
					TransferDataBkdcHibernate.transferData(bookingdotComDto,sessionFactory);					
					}
				}
			}
	}
		catch(Exception e)
		{
			exceptionUrls=new Date()+"--"+bkdcUrl.city+"--"+bkdcUrl.link+"\n";
			System.out.println(bkdcUrl.link+",Error:"+e+",Error Message:"+e.getMessage());
			exceptionUrls+="Error:"+e+",Error Message:"+e.getMessage()+"\n";		
			FileOutputStream exception=new FileOutputStream(exceptionUrlsFile,true);
			@SuppressWarnings("resource")
			PrintStream exe=new PrintStream(exception);
			exe.append(exceptionUrls);
			exe.close();
			if(exceptionUrls.contains("UnknownHostException"))
			{	
				Thread.sleep(300000);//sleep for 5 min
			}	
		}
		
		if(getFlag()==0){
			getOtherPagesUrl(bkdcUrl,getNum(),sessionFactory);
		}
}
	
	public static void getOtherPagesUrl(BKDCURL url,int num,SessionFactory sessionFactory) throws Exception
	{
		int offset=20;
		setFlag(1);
		for(int i=1;i<num;i++)
		{
			String pageUrl = url.link+";"+"offset="+offset;
			System.out.println("pageUrl "+pageUrl);
			BKDCURL bookingLink= new BKDCURL();
			bookingLink.city=url.city;
			bookingLink.link=new URL(pageUrl);
			getMainLinks(bookingLink,sessionFactory);
			offset = offset+20;
		}
		setFlag(0);
		setNum(-1);
	}
	
	public static int getNum(){
		return num;
	}

	public static void setNum(int num) {
		Crawlbookingdotcom.num = num;
	}
	
	public static int getFlag() {
		return flag;
		//return 1;//only for exception ulrs
	}

	public static void setFlag(int flag) {
		Crawlbookingdotcom.flag = flag;
	}

	public static void main(String args[])throws Exception
	{
		/**
		 * Make the url for city
		 * Sample url generated:"http://www.booking.com/searchresults.en-us.html?checkin_year_month_monthday=2014-11-27;checkout_year_month_monthday=2014-11-28;city=-2097701;rows=20"
		 */
		UrlBuilder.cityUrlBuilder();
		
		//Extract data from exception urls
		/*String[] resources = {"com/hibernate/HotelsDetails.hbm.xml", "com/hibernate/City.hbm.xml"};
		SessionFactory sessionFactory=getHibernateSessionFactory(resources);
		Scanner in = new Scanner(new File(exceptionUrlsFile));
		
		while(in.hasNext())
		{
			String line= in.next();
			if(line.contains("--"))
			{
				String ln[]=line.split("--");
				System.out.println(ln[2]);
				URL url=new URL(ln[2]);
				BKDCURL bkdcUrl=new BKDCURL();
				bkdcUrl.city=ln[1];
				bkdcUrl.link=url;
				getMainLinks(bkdcUrl,sessionFactory);
			}
		}
		sessionFactory.close();
	*/
	}
}
