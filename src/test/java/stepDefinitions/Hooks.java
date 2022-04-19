package stepDefinitions;

import java.io.IOException;

import io.cucumber.core.backend.StepDefinition;
import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace") // Why?: If we are trying to run only @DeletePlace, it doesn't know place_id.
							// So, we need to write code for getting the place_id. Here, we are calling the
							// existing methods directly.
	public void beforeScenario() throws IOException {

		// Execute this code only when place_id is null
		// Write a code that will give you place_id

		stepDefinition sd = new stepDefinition();
		if (stepDefinition.place_id == null) { // As place_id is static
			// Calling the required functions:
			sd.add_place_payload_with("Azeez", "English-EN", "Asia");
			sd.user_calls_with_http_request("AddPlaceAPI", "POST");
			sd.verify_place_id_created_maps_to_using("Azeez", "getPlaceAPI"); // Not everything in the method os
																				// required.
			// Still, we are using this as we don't want to
			// write another method.
		}

	}

}