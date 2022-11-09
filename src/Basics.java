import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args) {
		
		//validate if Add_Place API is working fine or not
		//Rest assured works on three principles: Given, When, then. In every automation test
		//Given: All details
		//When: Submit the API (resource and HTTP method)
		//Then: validate the response
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response= given().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace())
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		//How to extract a specific value from the response JSON
		
		JsonPath json=new JsonPath(response); //for parsing JSON
		String placeID=json.getString("place_id");
		
		System.out.println(placeID);
		
		//updating the address using the placeID
		String updated=given().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\"70 winter walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(updated);
		
		String msg=json.getString("updated");
		System.out.println(msg);
		/*String expectedMSG = "Address Successfully Updated";
		Assert.assertEquals(msg, expectedMSG);*/

	}
System.out.println("Dummy changes");

}
