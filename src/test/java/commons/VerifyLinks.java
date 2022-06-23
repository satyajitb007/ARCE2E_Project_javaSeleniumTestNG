package commons;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class VerifyLinks {
//	private static Logger logger = Logger.getLogger("VerifyLinks");
	private static Logger logger = Logger.getLogger("com.navigation_TestCase.NavigationTestCase");
	@SuppressWarnings("finally")
	public static int verifyLinkActive(String linkUrl)
	{
		int response = 0;
		try 
		{
			URL url = new URL(linkUrl);
			HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
			httpURLConnect.setConnectTimeout(3000);
			httpURLConnect.setRequestMethod("HEAD");
			httpURLConnect.connect();
			response = httpURLConnect.getResponseCode();
			logger.info("Perfect "+httpURLConnect.getResponseCode()+" and message *--*"+httpURLConnect.getResponseMessage());
		} catch (Exception e) {
			logger.info(e.getMessage());
		} finally{
			return response;
		}
	} 




}