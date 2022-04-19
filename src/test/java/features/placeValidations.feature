Feature: Validating Place APIs

#Scenario: Verify if place is being successfully added using AddPlaceAPI
#	Given Add Place payload
#	When User calls "AddPlaceAPI" with "POST" http request
#	Then the API call is success with Status code 200
#	And "status" in response body is "OK"
#	And "scope" in response body is "APP"

@AddPlace	
Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
	Given Add Place payload with "<name>" "<language>" "<address>"
	When User calls "AddPlaceAPI" with "POST" http request
	Then the API call is success with Status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "getPlaceAPI"
Examples:
	|name		|language		|address					|
	|Aziiz House|English - EN	|23 Sylvia Road, Illinois	|
#	|Cloud House|Chinese - CZ	|67 Thayineri Road, Kannur	|
	

@DeletePlace
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace payload
	When User calls "deletePlaceAPI" with "POST" http request
	Then the API call is success with Status code 200
	And "response" in response body is "OK"
# The last step will fail as there is some issue with API