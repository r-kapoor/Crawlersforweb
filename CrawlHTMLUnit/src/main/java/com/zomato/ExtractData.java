package com.zomato;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import GlobalClasses.HtmlUnitWebClient;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class ExtractData extends HtmlUnitWebClient{
	
	private static String exceptionUrls = "";
	private static String exceptionMsg = "";
	private static String exceptionFile = "target/zomato/exception.txt";
	private static String exceptionmsgFile = "target/zomato/exceptionmsg.txt";

	@SuppressWarnings("resource")
	public static void getResturantData(zmtURL link) throws Exception
	{
		try{
		String address="unknown",locality="unknown",phoneNo="unknown",highlights="",openingHours="unknown",cuisines="unknown",statistic="unknown";
		ArrayList<URL> photoLink=new ArrayList<URL>();
		String rating = "unknown";
		int cost =-1;
		
		HtmlPage page=WebClient(link.url);
		
		DomElement addressArea 	= 	page.getFirstByXPath("//div[@class='grid_14 column omega']");
		DomElement ratingArea	= 	page.getFirstByXPath("//div[@class='res-rating pos-relative clearfix']");
		DomElement phoneArea 	= 	page.getFirstByXPath("//div[@id='phoneNoString']");
		DomElement infoArea 	= 	page.getFirstByXPath("//div[@class='ipadding info-tab']");
		DomElement photoArea 	= 	page.getFirstByXPath("//div[@class='res-photo-thumbnails']");

		//Get the address
		 if((addressArea!=null)&&(addressArea.hasChildNodes()))
		 {
			 DomElement addressE1=addressArea.getFirstElementChild();
			 if(addressE1!=null){
				DomElement addressE2 = addressE1.getNextElementSibling();
				if((addressE2!=null)&&(addressE2.hasChildNodes())){
					address = addressE2.asText().trim();
					DomElement localityE = addressE2.getFirstElementChild();
					if(localityE!=null)
					{
							locality=localityE.asText();						
					}
				}
			 }
		 }
		 
		//get the rating and value
		 if((ratingArea!=null)&&(ratingArea.hasChildNodes()))
		 {
			 rating = ratingArea.getFirstElementChild().asText().trim();
		 }
		 
		 //get the phone no
		 if((phoneArea!=null)&&(phoneArea.hasChildNodes()))
		 {
			 DomElement phoneE1 = phoneArea.getFirstElementChild();
			 if((phoneE1!=null)&&(phoneE1.hasChildNodes()))
			 {
				 DomElement phoneE2 = phoneE1.getFirstElementChild();
				 if((phoneE2!=null)&&(phoneE2.hasChildNodes())){
						 phoneNo = phoneE2.asText();
				 }
			 }
		 }
		 
		 //get other info
		 if((infoArea!=null)&&(infoArea.hasChildNodes())){
			 Iterable<DomElement> infoIterable = infoArea.getFirstElementChild().getChildElements(); 
			
			 //Iterate through all elements
			 Iterator<DomElement> infoIterator = infoIterable.iterator();
		 
			 while(infoIterator.hasNext())
			 {
				 DomElement infoE1=infoIterator.next();
				 if((infoE1!=null)&&(infoE1.hasChildNodes()))
				 {
					 DomElement infoE2=infoE1.getLastElementChild();
					 if((infoE2!=null)&&(infoE2.hasChildNodes()))
					 {	
						 DomElement infoE3= infoE2.getFirstElementChild();
						 
						 if((infoE3!=null)&&(infoE3.getTagName().contains("label")))
						 {
							 
							 String infoLable1=infoE3.asText().trim();
							 if(infoLable1.contains("Highlights"))
							 {
								 String HL[]=infoE2.asText().split("\n");
								 for(int i=1;i<HL.length;i++)
								 {
									 highlights+=HL[i];
								 }
							 }
							 
							 if(infoLable1.contains("Cost"))
							 {
								 Scanner in = new Scanner(infoE2.asText().trim()).useDelimiter("[^0-9]+");
								 cost = in.nextInt();
							 }
						 }
						 
						 if((infoE3!=null)&&(infoE3.hasChildNodes()))
						 {
							 DomElement infoE4 = infoE3.getFirstElementChild();
							 if((infoE4!=null)&&(infoE4.getTagName().contains("label")))
							 {
								 String infoLable2=infoE4.asText().trim();
								 if(infoLable2.contains("Opening hours"))
								 {
									 if(infoE3.getLastElementChild().getTagName().contains("span"))
									 openingHours =infoE3.getLastElementChild().asText();
								 }
							 }
							 
							 if((infoE4!=null)&&(infoE4.hasChildNodes()))
							 {
								 DomElement infoE5 = infoE4.getFirstElementChild();
								 if((infoE5!=null)&&(infoE5.getTagName().contains("label")))
								 {
									 String infoLable3=infoE5.asText().trim();
									 if(infoLable3.contains("Cuisines"))
									 {
										 cuisines =infoE5.getNextElementSibling().asText().trim();
									 }
								 }
							 }
						 }
					 }
				 }

			 }
		 }
				 //photo area
				 if((photoArea!=null)&&(photoArea.hasChildNodes()))
				 {
					 Iterator<DomElement> photoIterator=photoArea.getChildElements().iterator();
					 while(photoIterator.hasNext())
					 {
						 try{
						 DomElement photoE2=photoIterator.next();
						 if((photoE2!=null)&&(photoE2.hasChildNodes())&&(photoE2.getTagName().contains("a")))
						 {
							 DomElement photoE3=photoE2.getFirstElementChild();
							 if((photoE3!=null)&&(photoE3.getTagName().contains("img")))
							 {
								 photoLink.add(new URL(photoE3.getAttribute("src")));
							 }		 
						 }	   
						}catch(Exception e){
							System.out.println("Exception occured");
							System.out.println(e.getMessage());
						}
					}
				 }
		 
		 System.out.println("link "+ link.url);
		 System.out.println("Title "+link.title);
		 System.out.println("Country "+link.country);
		 System.out.println("City "+link.city);
		 System.out.println("address: "+address);
		 System.out.println("locality: "+locality);
		 System.out.println("rating: "+rating);
		 System.out.println("phoneNo "+phoneNo);
		 System.out.println("hightlights: "+highlights);
		 System.out.println("Cost "+cost);
		 System.out.println("openingHours "+openingHours);
		 System.out.println("cuisines "+cuisines);
		 for(int k=0;k<photoLink.size();k++)
		 {
			 System.out.println(photoLink.get(k));
		 }
		 
		}catch(Exception e){
		
			System.out.println("Exception Occured. Adding to exceptionUrls");
			System.out.println(e);
			System.out.println(e.getMessage());
			exceptionUrls+=link.url+"\n";
			exceptionMsg+=e+"\n";
		}
		
		FileOutputStream exception=new FileOutputStream(exceptionFile);
		FileOutputStream exceptionmsg=new FileOutputStream(exceptionmsgFile);
		PrintStream e=new PrintStream(exception);
		PrintStream e1=new PrintStream(exceptionmsg);
		e.println(exceptionUrls);
		e1.println(exceptionMsg);
		e.close();
		e1.close();	
	}
}