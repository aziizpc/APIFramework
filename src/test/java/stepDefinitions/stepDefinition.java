package stepDefinitions;

import java.io.IOException;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class stepDefinition extends Utils {	// Extending as we want to use all the methods of 'Utils'
	
	RequestSpecification testReq;
	ResponseSpecification res;
	Response response;
	static String place_id; // We want the control to take same value for place_id for the entire run [Across all 'Scenario']. So, static. Else, it will reset to null on each run.
	JsonPath js;
	
	TestDataBuild testData = new TestDataBuild();	// Global
	
//	@Given("Add Place payload")
//	public void add_place_payload() throws IOException {						
//		
//		
//		testReq = given()							// Sending Add Place payload
//					.spec(requestSpecification())	// Coming from the 'extends' class method - Utils
//					.body(testData.addPlacePayload());	
//	}
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		testReq = given()							// Sending Add Place payload
				.spec(requestSpecification())		// Coming from the 'extends' class method - Utils
				.body(testData.addPlacePayload(name, language, address));	
	}
	
	@When("User calls {string} with POST http request")
	public void user_calls_with_post_http_request(String string) {
		
		res = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
		
			response =
				testReq
				.when()
					.post("maps/api/place/add/json");
	}
	
	@When("User calls {string} with {string} http request")
	public void user_calls_with_http_request(String apiName, String action) {
		
		APIResources resourceAPI = APIResources.valueOf(apiName);	// NOTE
		System.out.println("API Name: " + resourceAPI.getResource());	// NOTE
		
		res = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		if (action.equalsIgnoreCase("POST")) {
			response =
					testReq	// (In place of given())
					.when()
						.post(resourceAPI.getResource());	// Note	    	
		}
		else if (action.equalsIgnoreCase("GET")) {
			response =
					testReq	// (In place of given())
					.when()
						.get(resourceAPI.getResource());	// Note
		}			
	}
	
	@Then("the API call is success with Status code {int}")
	public void the_api_call_is_success_with_status_code(int expectedStatusCode) {
		assertEquals(response.getStatusCode(), expectedStatusCode);    // getStatusCode() returns integer
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
		// String respString = response.asString();
		// js = new JsonPath(respString);	    
		// assertEquals(js.getString(key), expectedValue); 
	    assertEquals(getJsonPath(response, key), expectedValue);	// Created the method 'getJsonPath' in Utils and commented the above lines
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String apiName) throws IOException {
		
		place_id = getJsonPath(response, "place_id");	// Extracting place_id for using in GET action
		
		testReq = given()			// Setting new testReq (As this is global) to use with the next statement
				.spec(requestSpecification())
				.queryParam("place_id", place_id);
		
		user_calls_with_http_request(apiName, "GET");	// Calling one of the existing methods directly
														// apiName details would be fetched from enum
		// 'response' variable is now loaded with GET API Response
		// Therefore, we can write:
		String nameFromGETResponse = getJsonPath(response, "name");
		
		assertEquals(nameFromGETResponse, expectedName);
		
	}
	
	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
		testReq = 
				given()
				.spec(requestSpecification())
				.body(testData.deletePlacePayload(place_id));		
	}

}