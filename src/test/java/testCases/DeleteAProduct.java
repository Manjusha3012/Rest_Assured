package testCases;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DeleteAProduct {

	SoftAssert softAssertObj;
	HashMap<String, String> deletePayload;

	public DeleteAProduct() {
		softAssertObj = new SoftAssert();
	}

	public Map<String, String> updatePayloadMap() {
		deletePayload = new HashMap<String, String>();
		deletePayload.put("id", "3890");

		System.out.println("deletePayload:" + deletePayload.toString());
		return deletePayload;
	}

	@Test(priority = 1)
	public void deleteAProduct() {

		SoftAssert softAssert = new SoftAssert();//

		Response response = given()
				// .log().all()
				.baseUri("https://techfios.com/api-prod/api/product")
				.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
				.basic("demo@techfios.com", "abc123")
				.body(new File("src\\main\\java\\data\\deletePayload.json")).
//		.body(updatePayloadMap()).
				when()
				// .log().all()
				.delete("/delete.php").then()
				// .log().all()
				.extract().response();

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);

		softAssertObj.assertEquals(actualStatusCode, 200, "Status Code is not matching");

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		// Assert.assertEquals(actualHeader, ""application/json; charset=UTF-8");
		softAssertObj.assertEquals(actualHeader, "application/json; charset=UTF-8", "Headers are not matching");

		String actualResponsebody = response.getBody().asString();
		System.out.println("actualResponsebody:" + actualResponsebody);

		JsonPath jasonPathObj = new JsonPath(actualResponsebody);

		String actualProductmessage = jasonPathObj.get("message");
		System.out.println("actualProductmessage:" + actualProductmessage);
		softAssertObj.assertEquals(actualProductmessage, "Product was deleted.", "Product are not matching!");

		softAssertObj.assertAll();

	}

	@Test(priority = 2)
	public void readAProduct() {
		Response response = given()
				// .log().all()
				.baseUri("https://techfios.com/api-prod/api/product").header("Content-Type", "application/json").auth()
				.preemptive().basic("demo@techfios.com", "abc123").queryParam("id", "3890").when()
				// .log().all()
				.get("/read_one.php").then()
				// .log().all()
				.extract().response();

		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);

		softAssertObj.assertEquals(actualStatusCode, 404, "Status Code is not matching");

		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		// Assert.assertEquals(actualHeader, "application/json");
		softAssertObj.assertEquals(actualHeader, "application/json", "Header is not matching");

		String actualResponsebody = response.getBody().asString();
		System.out.println("actualResponsebody:" + actualResponsebody);

		JsonPath jasonPathObj = new JsonPath(actualResponsebody);

		String actualProductMessage = jasonPathObj.get("message");
		System.out.println("actualProductMessage:" + actualProductMessage);
		softAssertObj.assertEquals(actualProductMessage, "Product does not exist.", "Product message are not matching");

		softAssertObj.assertAll();

	}

}
