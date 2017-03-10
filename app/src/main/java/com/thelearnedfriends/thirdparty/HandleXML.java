package com.thelearnedfriends.thirdparty;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;


public class HandleXML
{

   private String displayMessage = "name";
   private String eventCode = "description";
   private String responseString = "coordinates";

   private String urlData = null;
   private XmlPullParserFactory xmlFactoryObject;
   public volatile boolean parsingComplete = true;
   
   public String result = null;
   StringBuilder sb = new StringBuilder();
   String line = null;

   private HttpURLConnection conn;
   
   public HandleXML(String data)
   {
      this.urlData = data;
   }
   
   public ArrayList fetchXML()
   {

          Dictionary dict = new Hashtable();
          ArrayList al = new ArrayList();

	      try
	      {

				xmlFactoryObject = XmlPullParserFactory.newInstance();
				XmlPullParser myparser = xmlFactoryObject.newPullParser();

				myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				myparser.setInput(new StringReader(urlData));
				//parseXMLAndStoreIt(myparser);

                int eventType = myparser.getEventType();
                int nameFlag = 0, descriptionFlag = 0, cordinatesFlag = 0;
                int firstname = 0, firstdesc = 0;


                while (eventType != XmlPullParser.END_DOCUMENT)
                {

                   if((eventType == XmlPullParser.START_TAG) && myparser.getName().equals("name"))
                   {
                      if (firstname == 0)
                      {
                         firstname++;
                         eventType = myparser.next();
                         continue;
                      }
                      nameFlag = 1;
                      eventType = myparser.next();
                      continue;
                   }

                   if((eventType == XmlPullParser.START_TAG) && myparser.getName().equals("description"))
                   {
                      if (firstdesc == 0)
                      {
                         firstdesc++;
                         eventType = myparser.next();
                         continue;
                      }
                      descriptionFlag = 1;
                      eventType = myparser.next();
                      continue;
                   }

                   if((eventType == XmlPullParser.START_TAG) && myparser.getName().equals("coordinates"))
                   {
                      cordinatesFlag = 1;
                      eventType = myparser.next();
                      continue;
                   }

                   if (nameFlag == 1)
                   {
                      String name = myparser.getText();
                      //System.out.println("name " + name);
                      dict.put("name", name);
                      nameFlag = 0;
                   }

                   if (descriptionFlag == 1)
                   {
                      String description = myparser.getText().replace("<div dir=\"ltr\">", " ");
                      description = description.replace("</div>", " ");
                      //System.out.println("description " + description);
                      dict.put("description", description);
                      descriptionFlag = 0;
                   }

                   if (cordinatesFlag == 1)
                   {
                      String cordinates = myparser.getText();
                      //System.out.println("cordinates " + cordinates);
                      dict.put("cordinates", cordinates);
                      cordinatesFlag = 0;

                      al.add(dict);
                      dict = new Hashtable();
                   }

                   eventType = myparser.next();

                }

	      }
	      catch(Exception ex)
	      {
	    	  parsingComplete = false;
	    	  ex.printStackTrace();
	      }
          finally
          {
             //System.out.println("Store length ::::: "+al.size());
             //System.out.println("Store array ::::: "+al);
          }

       return al;
   }


}