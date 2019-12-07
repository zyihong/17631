package crypto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.Object;
import java.util.Random;

public class PageReader {
	public static String getPageContent(String strUrl, String strPostRequest,int maxLength) {
			  StringBuffer buffer = new StringBuffer();
			  System.setProperty("sun.net.client.defaultConnectTimeout", "100000");
			  System.setProperty("sun.net.client.defaultReadTimeout", "100000");
			  try {
			   URL newUrl = new URL(strUrl);
			   HttpURLConnection hConnect = (HttpURLConnection) newUrl.openConnection();
			   BufferedReader rd = new BufferedReader(new InputStreamReader(hConnect.getInputStream()));
			   int ch;
			   for (int length = 0; (ch = rd.read()) > -1
			     && (maxLength <= 0 || length < maxLength); length++)
			    buffer.append((char) ch);
			   String s = buffer.toString();
			   s.replaceAll("//&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
			   rd.close();
			   hConnect.disconnect();
			   return buffer.toString().trim();
			  } catch (Exception e) {
			   //
				  e.printStackTrace();
			   return null;
			  }
	}

	public static void main(String[] args) throws IOException {
			  //String url = "https://api.blockchair.com/bitcoin/transactions?s=fee_usd(desc)&limit=100&offset=";
			  String url = "https://api.blockchair.com/ethereum/transactions?s=value_usd(desc)&limit=50&offset=";
			  //String url = "https://api.blockchair.com/dash/transactions?s=fee_usd(desc)&limit=100&offset=";
			  //String url = "https://api.blockchair.com/bitcoin-cash/transactions?s=fee_usd(desc)&limit=100&offset=";
			  int s = 75;
			  //File f=new File("D:/a_document/java/crypto/"+offset_s+"-"+offset_e+".txt");
			  //FileOutputStream fos1=new FileOutputStream(f);
			  //OutputStreamWriter dos1=new OutputStreamWriter(fos1);
			  
			  for(int i = s; i < s+25; i++) {
				  File f=new File("D:/a_document/java/crypto/ethereum/ethereum_value_high_"+i+".txt");
				  FileOutputStream fos1=new FileOutputStream(f);
				  OutputStreamWriter dos1=new OutputStreamWriter(fos1);
				  System.out.println(url+i*50);
				  dos1.write(getPageContent(url+i, "get", 100500)+"\n");
				  dos1.close();
			  }
		
		      //String url_s = "https://api.blockchair.com/bitcoin/transactions?q=block_id(";
		      //String url_s = "https://api.blockchair.com/ethereum/transactions?q=block_id(";      
		      /*String url_s = "https://api.blockchair.com/dash/transactions?q=block_id(";      
	          //String url_s = "https://api.blockchair.com/bitcoin-cash/transactions?q=block_id(";      
		      String url_e = "..),fee_usd(1..)&s=id(asc)&limit=100";
			  int s = 75;
			  for(int i = s; i < s+25; i++) {
				  String url = url_s+(int)(Math.random()*1170000)+url_e;
				  File f=new File("D:/a_document/java/crypto/dash/dash_random_block_"+i+".txt");
				  FileOutputStream fos1=new FileOutputStream(f);
				  OutputStreamWriter dos1=new OutputStreamWriter(fos1);
				  System.out.println(url);
				  dos1.write(getPageContent(url, "get", 100500)+"\n");
				  dos1.close();
			  }*/
			  
	}
}
