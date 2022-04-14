package stepDefinitions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import pojo.AddPlace;
import pojo.Location;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
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
				.spec(requestSpecification())	// Coming from the 'extends' class method - Utils
				.body(testData.addPlacePayload(name, language, address));	
	}
	
	@When("User calls {string} with POST http request")
	public void user_calls_with_post_http_request(String string) {		

		res = new ResponseSpecBuilder().expectStatusCode(200)
			.expectContentType(ContentType.JSON).build();
		
			response =
				testReq
				.when()
					.post("maps/api/place/add/json")
				.then()
					.log().all()
					.assertThat()
						.spec(res)
					.extract().response();		
	}
	
	@Then("the API call is success with Status code {int}")
	public void the_api_call_is_success_with_status_code(int expectedStatusCode) {
		assertEquals(response.getStatusCode(), expectedStatusCode);    // getStatusCode() returns integer
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String value) {
	    String respString = response.asString();
	    JsonPath js = new JsonPath(respString);
	    assertEquals(js.getString(key), value); 
	}

}