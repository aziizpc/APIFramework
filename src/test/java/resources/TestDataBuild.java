package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestDataBuild {	// POJO class init
	
	public AddPlace addPlacePayload(String name, String language, String address) {
		
		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setName(name);	// Note
		ap.setAddress(address); // Note
		ap.setPhone_number("(+91) 123 456 6789");
		ap.setWebsite("http://google.com");
		ap.setLanguage(language);	// Note
		
		List <String> typesList = new ArrayList<String>();	// Using ArrayList as we need to pass array of values
		typesList.add("shoe park");
		typesList.add("shop");		
		ap.setTypes(typesList);
		
		Location loc = new Location();
		loc.setLat(-43.383494);
		loc.setLng(43.427362);
		ap.setLocation(loc);
		
		return ap;
	}
	
	public String deletePlacePayload(String placeId) {
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}"; // JSON Escape of Delete Place Payload		
	}

}