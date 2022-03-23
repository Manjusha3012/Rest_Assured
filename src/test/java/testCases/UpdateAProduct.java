package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpdateAProduct {
       SoftAssert softAssertObj;
       HashMap<String,String> updatePayload;
       
    public UpdateAProduct() {
    	softAssertObj= new SoftAssert();
    	
	
}
//	{
//    "name" : "Shital's Fairy Amazing Pillow 2.0",
//    "price" : "199",
//    "description" : "The best pillow for amazing programmers.",
//    "category_id" : 2
//}
 //   HashMap<String,String> createPayload = new HashMap<String,String>();
    
   

    public Map<String,String> updatePayloadMap() {
    	updatePayload = new HashMap<String,String>();
    	updatePayload.put("id", "3908");
    	updatePayload.put("name", "Shital's Fairy Amazing Pillow 3.0");
    	updatePayload.put("price", "299");
    	updatePayload.put("description", "The best pillow for amazing programmers.");
    	updatePayload.put("category_id", "2");
        System.out.println("updatePayload:" + updatePayload.toString());
    	return updatePayload;
}
	@Test
	public void updateAProduct() {
		
		SoftAssert softAssert = new SoftAssert();//
		


		Response response=
		given()
		//.log().all()
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type","application/json; charset=UTF-8")
		.auth().preemptive().basic("demo@techfios.com", "abc123")
//		.body(new File("src\\main\\java\\data\\createPayload.json")).
		.body(updatePayloadMap()).
		when()
		//.log().all()
		.put("update.php").
		then()
		//.log().all()
		.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);
		//Assert.assertEquals(actualStatusCode, 201);//hard Assert means if this assert fails than the programe wont move ahead
		softAssertObj.assertEquals(actualStatusCode, 200, "Status Code is not matching");
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		//Assert.assertEquals(actualHeader, ""application/json; charset=UTF-8");
		softAssertObj.assertEquals(actualHeader, "application/json; charset=UTF-8", "Headers are not matching");
		
		String actualResponsebody = response.getBody().asString();
		System.out.println("actualResponsebody:" + actualResponsebody);
		
		JsonPath jasonPathObj = new JsonPath(actualResponsebody);
		
		String actualProductmessage = jasonPathObj.get("message");
		System.out.println("actualProductmessage:" + actualProductmessage);
		softAssertObj.assertEquals(actualProductmessage, "Product was updated.", "Product are not matching!");
	
		softAssertObj.assertAll();
	
			
		}
}



