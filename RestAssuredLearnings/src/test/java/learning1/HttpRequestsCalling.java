package learning1;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

/*
 *  given()	- Content Type, Headers, Cookies, Authentication, Add Parameters etc ..
 *  		- its an optional
 *  when() 	- get, post, put, delete etc ..
 * 	then() 	- validate status code, extract response, extract headers, cookies and response body etc ..
 */

public class HttpRequestsCalling {
	
	int userId;
	
	@Test(priority = 1)
	void getUsers() {
		given()
		.when()
		.get("https://reqres.in/api/users?page=2")
		
		.then()
		.statusCode(200)
		.body("page", equalTo(2))
		.log().all();
	}
	
	@Test(priority=2)
	void createUser() {
		HashMap requestBody = new HashMap();
		requestBody.put("name", "Sravani");
		requestBody.put("job", "Automation Engineer");
		
		
		userId = given()
			.contentType("application/json")
			.body(requestBody)
		
		.when()
			.post("https://reqres.in/api/users")
			.jsonPath().getInt("id");
		
//		.then()
//			.statusCode(201)
//			.log().all();
	}

	@Test(priority = 3, dependsOnMethods = { "createUser" })
	void updateUser() {
		
		HashMap requestBody = new HashMap();
		requestBody.put("name", "Sravani Budati");
		requestBody.put("job", "QA Automation Engineer");
		
		given()
			.contentType("application/json")
			.body(requestBody)
		
		.when()
			.put("https://reqres.in/api/users/" + userId)
		
		.then()
			.statusCode(200)
			.log().all();
	}
	
	@Test(priority = 4, dependsOnMethods = { "createUser"})
	void deleteUser() {
		
		given()
		
		.when()
			.delete("https://reqres.in/api/users/" + userId)
			
		.then()
			.statusCode(204)
			.log().all();
	}
	
	
	
}
