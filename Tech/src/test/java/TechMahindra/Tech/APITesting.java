package TechMahindra.Tech;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APITesting {
	//Triggered the Get end point and printout the Response as string.
  @Test
  public void testCreateNewDeck() {
	  // Specify the base URL to the RESTful web service
	  RestAssured.baseURI = "https://deckofcardsapi.com";
	  
	  // Get the RequestSpecification of the request that you want to sent
	  // to the server. The server is specified by the BaseURI that we have
	  // specified in the above step.
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  // Make a GET request call directly by using RequestSpecification.get() method.
	  // Make sure you specify the resource name.
	  Response response = httpRequest.get("/api/deck/new");
	  
	  // Response.asString method will directly return the content of the body
	  // as String.
	  System.out.println("Response Body is =>  " + response.asString());
  }
  
  //Verified the Status code. If the status code is 200, print out the response body.
  @Test
  public void testCreateNewDeckWithShuffle() {
	  // Specify the base URL to the RESTful web service
	  RestAssured.baseURI = "https://deckofcardsapi.com";
	  
	  // Get the RequestSpecification of the request that you want to sent
	  // to the server. The server is specified by the BaseURI that we have
	  // specified in the above step.
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  // Make a GET request call directly by using RequestSpecification.get() method.
	  // Make sure you specify the resource name.
	  Response response = httpRequest.get("/api/deck/new/shuffle");
	  
	  //response code verification
	  int responseCode = response.getStatusCode();
	  if (responseCode == 200)
	  {
		  // Response.asString method will directly return the content of the body
		  // as String.
		  System.out.println("Response Body is =>  " + response.asString());
	  }
  }
  
//Passing the Parameters to Get call and verify the status Code
 //Using the Assert Statement
  @Test
  public void testAddingJockersInNewDeck() {
	  // Specify the base URL to the RESTful web service
	  RestAssured.baseURI = "https://deckofcardsapi.com";
	  
	  // Get the RequestSpecification of the request that you want to sent
	  // to the server. The server is specified by the BaseURI that we have
	  // specified in the above step.
	  RequestSpecification httpRequest = RestAssured.given();
	  
	  // Make a GET request call directly by using RequestSpecification.get() method.
	  // Make sure you specify the resource name.
	  Response response = httpRequest.queryParam("jokers_enabled", true).get("/api/deck/new");
	  
	  
	  //response code verification
	  int responseCode = response.getStatusCode();
	  if (responseCode == 200)
	  {
		  // Response as JSON  will directly return the content of the body
		  
		  String deck_id = response.jsonPath().getString("deck_id");
		  Assert.assertNotEquals(deck_id, null);
	  }
  }
  
  
  /**
	 * Testing for draw a card from new deck without shuffle
	 */
  //response of the first endpoint is input parameter for second endpoint.
 //Using the Assert Statement
  @Test
  public void testDrawAOneCardFromDeck() {
	  // First run the new deck endpoint then draw card from this response.
	  RestAssured.baseURI = "https://deckofcardsapi.com";
	  RequestSpecification httpRequest = RestAssured.given();
	  Response response = httpRequest.get("/api/deck/new");
	  String deckId = response.jsonPath().getString("deck_id");
	  Assert.assertNotNull(deckId);

	  Response responseWithDraw = httpRequest.queryParam("deck_id", deckId).get("/api/deck/{deck_id}/draw/");
	  //response code verification
	  int responseCode = responseWithDraw.getStatusCode();
	  if (responseCode == 200)
	  {
		  // Response as JSON  will directly return the content of the body
		  List<String> cards = responseWithDraw.jsonPath().getList("cards");
		  Assert.assertEquals(cards.size(), "51");		  
	  }
  }
  
  
}
