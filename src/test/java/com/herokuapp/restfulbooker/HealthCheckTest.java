package com.herokuapp.restfulbooker;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class HealthCheckTest {

	//A simple health check endpoint to confirm whether the API is up and running.
	@Test
	public void healthCheckTest() {
		given().
		when().
			get("https://restful-booker.herokuapp.com/ping").
		then().
			assertThat().
			statusCode(201);
	}
}