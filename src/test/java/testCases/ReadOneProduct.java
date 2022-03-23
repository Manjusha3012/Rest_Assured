package testCases;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReadOneProduct {
	SoftAssert softAssertObj;
	
    public ReadOneProduct() {
    	softAssertObj= new SoftAssert();
	
}

	@Test
	public void readOneProduct() {
		
		SoftAssert softAssert = new SoftAssert();
		
//		given: all input details(base URL,Headers,Authorization,Payload/Body,QueryParameters)
//		when: submit api requests(Http method,Endpoint/Resource)
//		then: validate responce(Status code, Headers, responseTime, Payload/Body)

		
		
//		Read a product
//		http Method: GET
//		headers:
//		Content-Type= application/json
//		Authorization:
//		basicAuth: username,password
//		baseUri= https://techfios.com/api-prod/api/product/
//		EndPoint: /read_one.php
//		queryParams:
//		id=3720
//		Status Code= 200 OK

		Response response=
		given()
		//.log().all()
		.baseUri("https://techfios.com/api-prod/api/product")
		.header("Content-Type","application/json")
		.auth().preemptive().basic("demo@techfios.com", "abc123")
		.queryParam("id", "3908").
		when()
		//.log().all()
		.get("/read_one.php").
		then()
		//.log().all()
		.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);
		//Assert.assertEquals(actualStatusCode, 200);//hard Assert means if this assert fails than the programe wont move ahead
		softAssertObj.assertEquals(actualStatusCode, 200, "Status Code is not matching");
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		//Assert.assertEquals(actualHeader, "application/json");
		softAssertObj.assertEquals(actualHeader, "application/json", "Header is not matching");
		
		String actualResponsebody = response.getBody().asString();
		System.out.println("actualResponsebody:" + actualResponsebody);
		
		JsonPath jasonPathObj = new JsonPath(actualResponsebody);
		
		String actualProductId = jasonPathObj.get("id");
		System.out.println("actualProductId:" + actualProductId);
		softAssertObj.assertEquals(actualProductId, "3908", "Product Ids are not matching");
		
		
		String actualProductName = jasonPathObj.get("name");
		System.out.println("actualProductName:" + actualProductName);
		softAssertObj.assertEquals(actualProductName, "Shital's Fairy Amazing Pillow 3.", "Product names are not matching");
		
		String actualProductPrice = jasonPathObj.get("price");
		System.out.println("actualProductPrice:" + actualProductPrice);
		softAssertObj.assertEquals(actualProductPrice, "299", "Product prices are not matching!");
		
		softAssertObj.assertAll();
	
			
		}
	}



