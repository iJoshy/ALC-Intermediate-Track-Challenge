
package com.thelearnedfriends.thelearnedfriends;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class BuildAndSendRequest extends AsyncTask<String, Void, String>
{

   private String newUrl = "http://41.190.16.170:8080/mobile-gateway/services/1.0/";
   private String osType = "ANDROID";
   private String originatingMsisdn = "";
   private String versionNo = "3.2";

   private HttpURLConnection conn;
   private JSONObject jsonobject;
   private String responseCode = "";
   private String eventCode = "";
   private String displayMessage = "";
   private int requester;
   private MaterialDialog dialog;
   private String restBody;
   private Context ctx;

   String serviceControllerCode = "";
   String prodMainPackage = "";
   String prodSubservice = "";
   String optCode = "";


   public BuildAndSendRequest(Context context)
   {
	   ctx = context;
   }

   public void mainbalance()
   {
	   serviceControllerCode = "QUERY_VOICE_PLAN";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 0;

	   execute(newUrl, restBody);

   }


   public void recharge(String code)
   {
	   serviceControllerCode = "RECHARGE_SUBSCRIBER_BY_VOUCHER";
	   prodMainPackage = "RECHARGE_SUBSCRIBER_BY_VOUCHER";
	   prodSubservice = "RECHARGE_SUBSCRIBER_BY_VOUCHER";
	   String voucherPIN = code;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"voucherPIN\":\""+voucherPIN+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 1;

	   execute(newUrl, restBody);

   }


   public void transfer(String code, String destNo, String amt)
   {
	   String destinationMsisdnOne = destNo;
	   serviceControllerCode = "SUBCRIBER_ACCOUNT_TRANSFER";
	   prodMainPackage = "SUBCRIBER_ACCOUNT_TRANSFER";
	   prodSubservice = "SUBCRIBER_ACCOUNT_TRANSFER";
	   String passKey = code;
	   String txnAmount = amt;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"passKey\":\""+passKey+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" +
			   "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 2;

	   execute(newUrl, restBody);

   }


   public void rechargeother(String code, String destNo)
   {
	   String destinationMsisdnOne = destNo;
	   serviceControllerCode = "RECHARGE_3RD_PARTY_BY_VOUCHER";
	   prodMainPackage = "RECHARGE_3RD_PARTY_BY_VOUCHER";
	   prodSubservice = "RECHARGE_3RD_PARTY_BY_VOUCHER";
	   String voucherPIN = code;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"voucherPIN\":\""+voucherPIN+"\",\n" + "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 3;

	   execute(newUrl, restBody);

   }


   public void databalance()
   {
	   serviceControllerCode = "QUERY_DATA_PLAN";
	   prodMainPackage = "QUERY_DATA_PLAN";
	   prodSubservice = "QUERY_DATA_PLAN";
	   optCode = "QUERY";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"optCode\":\""+optCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 4;

	   execute(newUrl, restBody);

   }


   public void databundles(String dataplancode, String amt, String dataplan)
   {
	   serviceControllerCode = "PURCHASE_EASY_BLAZE_PLAN";
	   prodMainPackage = "PURCHASE_EASY_BLAZE_PLAN";
	   prodSubservice = "PURCHASE_EASY_BLAZE_PLAN";
	   String serviceFlag = dataplancode;
	   String txnAmount = amt;
	   String dataValue = dataplan;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n  \"dataServiceFG\":\""+serviceFlag+"\",\n    \"dataValue\":\""+dataValue+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 5;

	   execute(newUrl, restBody);

   }


   public void optout()
   {
	   serviceControllerCode = "CANCEL_EASY_BLAZE_PLAN";
	   prodMainPackage = "CANCEL_EASY_BLAZE_PLAN";
	   prodSubservice = "CANCEL_EASY_BLAZE_PLAN";
	   String serviceFlag = "2";
	   String optCode = "CANCEL";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"optCode\":\""+optCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 6;

	   execute(newUrl, restBody);

   }


   public void younmeaddno(String addno)
   {
	   String destinationMsisdnOne = addno;
	   serviceControllerCode = "ADD_YOU_AND_ME";
	   String serviceFlag = "1";
	   String txnAmount = "0";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" +
			   "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 7;

	   execute(newUrl, restBody);

   }


   public void younmedeleteno(String deleteno)
   {
	   String destinationMsisdnOne = deleteno;
	   serviceControllerCode = "DELETE_YOU_AND_ME";
	   String serviceFlag = "2";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 8;

	   execute(newUrl, restBody);

   }


   public void younmequeryno()
   {
	   serviceControllerCode = "QUERY_YOU_AND_ME";
	   String serviceFlag = "8";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 9;

	   execute(newUrl, restBody);

   }


   public void younmereplaceno(String addno1, String addno2)
   {
	   serviceControllerCode = "REPLACE_YOU_AND_ME";
	   String destinationMsisdnOne = addno1;
	   String destinationMsisdnTwo = addno2;
	   String serviceFlag = "5";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\",\n" + "    \"destinationMsisdnTwo\":\""+destinationMsisdnTwo+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 10;

	   execute(newUrl, restBody);

   }


   public void cliqoptin()
   {
	   serviceControllerCode = "MIGRATE_EASY_BRAND";
	   prodMainPackage = "EASYCLIQ";
	   prodSubservice = "EASYCLIQ";
	   String txnAmount = "100";
	   String serviceFlag = "5";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 11;

	   execute(newUrl, restBody);

   }


   public void cliqoptout()
   {
	   serviceControllerCode = "CANCEL_CLIQ_4D_DAY";
	   prodMainPackage = "EASYCLIQ";
	   prodSubservice = "EASYCLIQ";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 12;

	   execute(newUrl, restBody);

   }

   public void cliqoptquery()
   {
	   serviceControllerCode = "QUERY_CLIQ_4D_DAY";
	   prodMainPackage = "EASYCLIQ";
	   prodSubservice = "EASYCLIQ";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 13;

	   execute(newUrl, restBody);

   }


   public void buyeasyflex(String flag, String amt)
   {
	   serviceControllerCode = "MIGRATE_TO_EASY_FLEX";
	   prodMainPackage = "EASYFLEX";
	   prodSubservice = "EASYFLEX";
	   String serviceFlag = flag;
	   String txnAmount = amt;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 14;

	   execute(newUrl, restBody);

   }


   public void canceleasyflex()
   {
	   serviceControllerCode = "CANCEL_ALL_ISN_SERVICES";
	   prodMainPackage = "EASYFLEX";
	   prodSubservice = "EASYFLEX";
	   String serviceFlag = "10";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 15;

	   execute(newUrl, restBody);

   }


   public void queryeasyflex()
   {
	   serviceControllerCode = "QUERY_EASY_FLEX";
	   prodMainPackage = "EASYFLEX";
	   prodSubservice = "EASYFLEX";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 16;

	   execute(newUrl, restBody);

   }


	public void  easylifeOptin()
	{
		serviceControllerCode = "EASYLIFE_SUBSCRIPTION";
		prodMainPackage = "EASYLIFE";
		prodSubservice = "EASYLIFE";

		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
				"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
				"\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
				"\",\n}";

		newUrl += "serviceControllerDelegate";
		requester = 18;

		execute(newUrl, restBody);

	}


   public void buyeasylife(String flag, String amt)
   {
	   serviceControllerCode = "MIGRATE_TO_EASY_LIFE";
	   prodMainPackage = "EASYLIFE";
	   prodSubservice = "EASYLIFE";
	   String serviceFlag = flag;
	   String txnAmount = amt;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 17;

	   execute(newUrl, restBody);

   }


   public void canceleasylife()
   {
	   serviceControllerCode = "CANCEL_ALL_ISN_SERVICES";
	   prodMainPackage = "EASYLIFE";
	   prodSubservice = "EASYLIFE";
	   String serviceFlag = "15";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 18;

	   execute(newUrl, restBody);

   }


   public void queryeasylife()
   {
	   serviceControllerCode = "QUERY_EASY_LIFE";
	   prodMainPackage = "EASYLIFE";
	   prodSubservice = "EASYLIFE";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 19;

	   execute(newUrl, restBody);

   }

   public void buyeasybiz(String flag, String code, String amt)
   {
	   serviceControllerCode = "INSTALL_EASY_BUSINESS";
	   prodMainPackage = "EASYBUSINESS";
	   prodSubservice = "EASYBUSINESS";
	   String serviceFlag = flag;
	   String txnAmount = amt;
	   String productCode = code;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" +
			   "    \"productCode\":\""+productCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 20;

	   execute(newUrl, restBody);

   }


   public void shownumber()
   {
	   serviceControllerCode = "QUERY_SUBSCRIBER_MOBILE_NO";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 21;

	   execute(newUrl, restBody);

   }


   public void checkpackage()
   {
	   serviceControllerCode = "QUERY_SUBSCRIBER_PACKAGE";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 22;

	   execute(newUrl, restBody);

   }


   public void younmemigrate()
   {
	   serviceControllerCode = "MIGRATE_TO_EASY_STARTER";
	   prodMainPackage = "EASYSTARTER";
	   prodSubservice = "EASYSTARTER";
	   String serviceFlag = "6";
	   String txnAmount = "100";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 23;

	   execute(newUrl, restBody);

   }


   public void cliqmigrate()
   {
	   serviceControllerCode = "MIGRATE_TO_EASY_CLIQ";
	   prodMainPackage = "EASYCLIQ";
	   prodSubservice = "EASYCLIQ";
	   String serviceFlag = "5";
	   String txnAmount = "100";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 24;

	   execute(newUrl, restBody);

   }


   public void datatransfer(String code, String destNo, String amt)
   {
	   String destinationMsisdnOne = destNo;
	   serviceControllerCode = "SUBSCRIBER_DATA_TXF";
	   prodMainPackage = "SUBSCRIBER_DATA_TXF";
	   prodSubservice = "SUBSCRIBER_DATA_TXF";
	   String passKey = code;
	   String txnAmount = amt;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"passKey\":\""+passKey+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" +
			   "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 26;

	   execute(newUrl, restBody);

   }

   public void datagifting(String dataplancode, String destNo)
   {
	   String destinationMsisdnOne = destNo;
	   serviceControllerCode = "SUBSCRIBER_GIFT_DATA";
	   prodMainPackage = "SUBSCRIBER_GIFT_DATA";
	   prodSubservice = "SUBSCRIBER_GIFT_DATA";
	   String serviceFlag = dataplancode;

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
			   "\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
			   "\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
			   "\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"destinationMsisdnOne\":\""+destinationMsisdnOne+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 27;

	   execute(newUrl, restBody);

   }


   public void displaynumber()
   {
	   serviceControllerCode = "QUERY_SUBSCRIBER_MOBILE_NO";

	   restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+"\"\n}";

	   newUrl += "serviceControllerDelegate";
	   requester = 28;

	   execute(newUrl, restBody);

   }


	public void validation()
	{
		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+"\",\n    \"osType\":\""+osType+"\"\n}";

		newUrl += "validateConfigParameters";
		requester = 29;

		execute(newUrl, restBody);

	}


	public void buyevolution(String flag, String amt)
	{
		serviceControllerCode = "MIGRATE_TO_EASY_FLEX";
		prodMainPackage = "EASYFLEX";
		prodSubservice = "EASYFLEX";
		String serviceFlag = flag;
		String txnAmount = amt;

		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
				"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
				"\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
				"\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\"\n}";

		newUrl += "serviceControllerDelegate";
		requester = 30;

		execute(newUrl, restBody);

	}


	public void cancelevolution()
	{
		serviceControllerCode = "CANCEL_ALL_ISN_SERVICES";
		prodMainPackage = "EASYFLEX";
		prodSubservice = "EASYFLEX";
		String serviceFlag = "10";

		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
				"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
				"\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
				"\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

		newUrl += "serviceControllerDelegate";
		requester = 31;

		execute(newUrl, restBody);

	}


	public void queryevolution()
	{
		serviceControllerCode = "QUERY_EASY_LIFE";
		prodMainPackage = "EASYLIFE";
		prodSubservice = "EASYLIFE";
		String serviceFlag = "1";

		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
				"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
				"\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+ prodSubservice+
				"\"\n" + "    \"serviceFlag\":\""+serviceFlag+"\"\n}";

		newUrl += "serviceControllerDelegate";
		requester = 32;

		execute(newUrl, restBody);

	}


	public void buyeasybexec(String flag, String code, String amt)
	{
		serviceControllerCode = "EASY_BIZ_EXECUTIVE_PLAN";
		prodMainPackage = "EASYBUSINESS";
		prodSubservice = "EASYBUSINESS";
		String serviceFlag = flag;
		String txnAmount = amt;
		String productCode = code;

		restBody = "{\n    \"versionNo\":\""+versionNo+"\",\n    \"originatingMsisdn\":\""+originatingMsisdn+
				"\",\n    \"osType\":\""+osType+"\",\n    \"serviceControllerCode\":\""+serviceControllerCode+
				"\",\n" +"    \"prodMainPackage\":\""+prodMainPackage+"\",\n" + "    \"prodSubservice\":\""+prodSubservice+
				"\",\n" + "    \"serviceFlag\":\""+serviceFlag+"\",\n" + "    \"txnAmount\":\""+txnAmount+"\",\n" +
				"    \"productCode\":\""+productCode+"\"\n}";

		newUrl += "serviceControllerDelegate";
		requester = 33;

		execute(newUrl, restBody);

	}



	@Override
	protected void onPreExecute()
	{
		// Showing progress dialog
		dialog =  new MaterialDialog.Builder(ctx)
				.title("Processing")
				.content("Please wait ...")
				.progress(true, 0)
				.progressIndeterminateStyle(true)
				.show();

		super.onPreExecute();
	}

	protected String doInBackground(String... params)
	{
		InputStream is = null;
		OutputStream os = null;
		String result = "";

		try
		{
			URL url = new URL(params[0]);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(30000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setUseCaches(false);
			conn.setDoInput(true);

			byte[] postDataBytes = params[1].toString().getBytes("UTF-8");

			System.out.println("url ::::: "+params[0].toString());
			System.out.println("body :::: "+params[1].toString());

			// Starts the query
			os = conn.getOutputStream();
			os.write(postDataBytes);
			os.flush();
			os.close();

			int status = conn.getResponseCode();

			if (status == 200)
			{
				is = conn.getInputStream();
				int len = conn.getContentLength();

				// Convert the InputStream into a string
				String contentAsString = readIt(is);
				//System.out.println("result ::: "+contentAsString);

				jsonobject = new JSONObject(contentAsString);
				responseCode = jsonobject.getString("validationTypeFG");
				eventCode = jsonobject.getString("eventCode");
				displayMessage = jsonobject.getString("displayMessage");

				return contentAsString;
			}

		}
		catch (Exception exception)
		{

			displayMessage = "sorry operation failed. please try again later.";
			exception.printStackTrace();
			return null;
		}
		finally
		{
			if(conn != null)
			{
				conn.disconnect();
			}
			newUrl = "";
		}

		return result;
	}

	protected void onPostExecute(String s)
	{

		dialog.dismiss();

		if (eventCode.equals("-25"))
			displayMessage = "This feature is only available with etisalat SIM card. Please turn off WiFi or insert an etisalat SIM card";


		if (displayMessage.equals("") || displayMessage == null)
			displayMessage = "Mobile Gateway Service is temporarily unavailable, please try again later, thank you.";


		new MaterialDialog.Builder(ctx)
				.iconRes(R.drawable.logo)
				.limitIconToDefaultSize()
				.title("etisalat")
				.content(displayMessage)
				.positiveText("ok")
				.callback(new MaterialDialog.ButtonCallback() {
					@Override
					public void onPositive(MaterialDialog dialog) {

					}

				})
				.show();

		super.onPostExecute(s);
	}


	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder out = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		String line;
		while ((line = reader.readLine()) != null)
		{
			out.append(line);
			out.append(newLine);
		}
		return out.toString();
	}


}
