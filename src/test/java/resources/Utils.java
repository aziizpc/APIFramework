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
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException {	// As request is common for all		
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));	// Note: Logging file
		
		req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl"))
			.addQueryParam("key", "qaclick123")
			.addFilter(RequestLoggingFilter.logRequestTo(log))	// NOTE: For logging Request
			.addFilter(ResponseLoggingFilter.logResponseTo(log))// NOTE: For logging Response
			.setContentType(ContentType.JSON)
			.build();		
		return req;		
	}
	
	public String getGlobalValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\global.properties");
		prop.load(fis);
		return prop.getProperty(key);	// .getProperty
	}

}