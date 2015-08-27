import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

public class Connect {
	public AmazonS3 client;
	private static AWSCredentials creds = null;
	private static String bucket = null;
	private static ClientConfiguration login = null;
	// private BufferedReader ldap=null;
	private Properties ldap = null;
	private InputStream inp = null;
	private boolean proxy = false;
	private String username = null;
	private String password = null;
	private String host=null;
	private int port;

	Connect() throws FileNotFoundException {
		try {
			creds = new ProfileCredentialsProvider("default").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Credentials not available");
		}

		try {
			ldap = new Properties();
			inp = getClass().getClassLoader().getResourceAsStream(
					"proxy.properties");
			ldap.load(inp);
			proxy=Boolean.valueOf(ldap.getProperty("useProxy"));
			if (proxy)
			{
				username=ldap.getProperty("username");
				password=ldap.getProperty("password");
				host=ldap.getProperty("host");
				port=Integer.valueOf(ldap.getProperty("port"));
				login = new ClientConfiguration();
				login.setProxyHost(host);
				login.setProxyPort(port);
				login.setProxyUsername(username);
				login.setProxyPassword(password);
				client = new AmazonS3Client(creds, login);
				Region usWest2 = Region.getRegion(Regions.US_WEST_2);
				client.setRegion(usWest2);
			}
			else
			{
				client = new AmazonS3Client(creds);
				Region usWest2 = Region.getRegion(Regions.US_WEST_2);
				client.setRegion(usWest2);
			}
			
		} catch (Exception e) {
			throw new FileNotFoundException("Login Credentials not available");
		}

	}
}
