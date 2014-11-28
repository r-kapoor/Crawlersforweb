package com.bookingdotcom;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import GlobalClasses.HtmlUnitWebClient;

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
	
	public static void getMainLinks(BKDCURL bkdcUrl) throws Exception
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
				//setNum(Integer.parseInt(number));
				setNum(1);
				System.out.println("num "+getNum());
			}
		}
		
		if((hotelListInnerArea!=null)&&(hotelListInnerArea.hasChildNodes()))
		{
			Iterator<DomElement> hotelListInnerIterator=hotelListInnerArea.getChildElements().iterator();
			int isLinkSet=0,isLocalitySet=0;
			while(hotelListInnerIterator.hasNext())
			{
				DomElement hotelListInnerElement=hotelListInnerIterator.next();
				DomElement itemContentElement=hotelListInnerElement.getLastElementChild();
				if((itemContentElement!=null)&&(itemContentElement.hasChildNodes()))
				{
					Iterator<DomElement> itemDetailsIterator=itemContentElement.getChildElements().iterator();
					BKDCURL bookingUrl = new BKDCURL();
					while(itemDetailsIterator.hasNext())
					{
						DomElement detailsE=itemDetailsIterator.next();

						if((detailsE!=null)&&(detailsE.getTagName().contains("h3"))&&(detailsE.hasChildNodes()))
						{
							DomElement detailsEChd=detailsE.getFirstElementChild();
							if(detailsEChd.getTagName().contains("a"))
							{
								isLinkSet=1;
								String href=detailsEChd.getAttribute("href");
								bookingUrl.country=bkdcUrl.country;
								bookingUrl.city=bkdcUrl.city;
								bookingUrl.link=new URL(baseUrl+href);
								//System.out.println("hotelUrl:"+baseUrl+href);
							}
						}
						
						if((detailsE!=null)&&(detailsE.getAttribute("class").contains("address"))&&(detailsE.hasChildNodes()))
						{
							isLocalitySet=1;
							DomElement localityE= detailsE.getFirstElementChild();
							if(localityE.getAttribute("class").contains("jq_tooltip district_link"))
							{
								String locality = localityE.asText().trim();
								bookingUrl.locality=locality;
								//System.out.println("locality "+locality);
							}
						}

						if((isLinkSet==1)&&(isLocalitySet==1))
						{
							System.out.println(bookingUrl.country+","+bookingUrl.city+","+bookingUrl.locality+","+bookingUrl.link);
							mainLinks.add(bookingUrl);
							isLinkSet=0;
							isLocalitySet=0;
						}
					}
				}
			}
		}
		
		if(getFlag()==0){
			getOtherPagesUrl(bkdcUrl,getNum());
		}
	}
		catch(Exception e)
		{
			exceptionUrls=new Date()+":"+bkdcUrl.link+"\n";
			System.out.println(bkdcUrl.link+",Error:"+e+",Error Message:"+e.getMessage());
			exceptionUrls+="Error:"+e+",Error Message:"+e.getMessage()+"\n";		
			FileOutputStream exception=new FileOutputStream(exceptionUrlsFile,true);
			@SuppressWarnings("resource")
			PrintStream exe=new PrintStream(exception);
			exe.append(exceptionUrls);
			exe.close();
		}
}
	
	public static void getOtherPagesUrl(BKDCURL url,int num) throws Exception
	{
		int offset=20;
		setFlag(1);
		for(int i=1;i<num;i++)
		{
			String pageUrl = url.link+";"+"offset="+offset;
			System.out.println("pageUrl "+pageUrl);
			BKDCURL bookingLink= new BKDCURL();
			bookingLink.city=url.city;
			bookingLink.country=url.country;
			bookingLink.locality="unknown";
			bookingLink.link=new URL(pageUrl);
			getMainLinks(bookingLink);
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
		//store all above city wise hotel urls into allHotelsUrls.txt
		String hotelUrl="";
		for(int j=0;j<mainLinks.size();j++)
		{
			BKDCURL link = mainLinks.get(j);
			//ExtractData.getData(link);
			hotelUrl=link.country+"&&&"+link.city+"&&&"+link.locality+"&&&"+link.link+"\n";
			FileOutputStream hotelUrlFile=new FileOutputStream(hotelUrlsFile,true);
			PrintStream pstream=new PrintStream(hotelUrlFile);
			pstream.append(hotelUrl);
			pstream.close();
			//System.out.println(mainUrls);
		}
		
		
		//For testing

		/*BKDCURL bookingTest= new BKDCURL();
		bookingTest.link=new URL("http://www.booking.com/searchresults.en-us.html?checkin_year_month_monthday=2014-05-18;checkout_year_month_monthday=2014-05-19;city=-2098033;rows=20;offset=20");
		bookingTest.country="India";
		bookingTest.city="Jaipur";
		getMainLinks(bookingTest);
		*/
//		URL test = new URL("http://www.booking.com/hotel/in/the-leela-palace-kempinski-new-delhi.en-gb.html?checkin=2014-06-14;checkout=2014-06-15;");

		//URL test = new URL("http://www.booking.com/hotel/in/the-leela-palace-kempinski-new-delhi.en-gb.html?checkin=2014-05-05;checkout=2014-05-07;");
		//ExtractData.getData(test);

//		ExtractData.getPrices(test);
		
		/*URL test1= new URL("http://www.booking.com/searchresults.html?src=index&nflt=&ss_raw=delhi&error_url=http%3A%2F%2Fwww.booking.com%2Findex.en-gb.html%3Fsid%3Debe7d62a1bd79af153bc686482db1ed3%3Bdcid%3D4%3B&dcid=4&lang=en-gb&sid=ebe7d62a1bd79af153bc686482db1ed3&si=ai%2Cco%2Cci%2Cre%2Cdi&ss=New+Delhi%2C+Delhi+NCR%2C+India&checkin_monthday=12&checkin_year_month=2014-6&checkout_monthday=13&checkout_year_month=2014-6&interval_of_time=any&flex_checkin_year_month=any&no_rooms=1&group_adults=2&group_children=0&dest_type=city&dest_id=-2106102&ac_pageview_id=153c4d7352b00060&ac_position=0&ac_suggestion_list_length=5");
		getMainLinks(test1);
		*/
		/*for(int j=0;j<mainLinks.size();j++)
		{
			URL link = mainLinks.get(j);
			ExtractData.getData(link);
			//mainUrls+=link.toString()+"\n";
			
		}*/
	}
}
