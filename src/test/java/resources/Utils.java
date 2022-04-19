package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static RequestSpecification req;	// Made this static as we want to retain its value and use it for the next run so logging works for all examples
	
	public RequestSpecification requestSpecification() throws IOException {	// As request is common for all	
		
		if (req == null)	// As the older logs (In the same feature) are getting removed on each run 
		{		
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));	// Note: Logging file
		
			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
					.addQueryParam("key", "qaclick123")
					.addFilter(RequestLoggingFilter.logRequestTo(log))	// NOTE: For logging Request
					.addFilter(ResponseLoggingFilter.logResponseTo(log))// NOTE: For logging Response
					.setContentType(ContentType.JSON)
					.build();		
			return req;
		}
		return req;
	}
	
	public String getGlobalValue(String key) throws IOException {	// Need not be static
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);	// .getProperty
	}
	
	public String getJsonPath(Response response, String key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);		
		return js.get(key).toString();		
	}

}