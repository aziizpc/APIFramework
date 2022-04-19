package resources;

// enum is a special class is java which has collection of constants or methods
public enum APIResources {
	
	AddPlaceAPI ("maps/api/place/add/json"),
	getPlaceAPI ("maps/api/place/get/json"),
	deletePlaceAPI ("maps/api/place/delete/json");
	
	private String resource;
	
	APIResources(String resource) {	// Mandatory [Every 'method' accepts one argument]
		this.resource = resource;		
	}
	
	public String getResource() {
		return resource;
	}

}