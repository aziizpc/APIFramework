Feature: Validating Place APIs

Scenario: Verify if place is being successfully added using AddPlaceAPI
	Given Add Place payload
	When User calls "AddPlaceAPI" with POST http request
	Then the API call is success with Status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
